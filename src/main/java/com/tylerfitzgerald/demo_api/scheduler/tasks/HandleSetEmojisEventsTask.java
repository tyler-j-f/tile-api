package com.tylerfitzgerald.demo_api.scheduler.tasks;

import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.token.TokenRetriever;
import com.tylerfitzgerald.demo_api.erc721.traits.WeightlessTraitTypeConstants;
import com.tylerfitzgerald.demo_api.etc.BigIntegerFactory;
import com.tylerfitzgerald.demo_api.ethEvents.EthEventException;
import com.tylerfitzgerald.demo_api.ethEvents.events.SetEmojisEvent;
import com.tylerfitzgerald.demo_api.scheduler.TaskSchedulerException;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits.WeightlessTraitDTO;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits.WeightlessTraitRepository;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class HandleSetEmojisEventsTask extends AbstractEthEventsRetrieverTask {
  private static final int NUMBER_OF_SUB_TILES = 4;
  private static final int EMOJI_SET_SPECIFIER_INDEX = 0;

  @Autowired private TokenRetriever tokenRetriever;
  @Autowired private WeightlessTraitRepository weightlessTraitRepository;

  @Override
  public void execute() throws TaskSchedulerException {
    try {
      getSetEmojisEventsAndUpdateTraitValues();
    } catch (EthEventException e) {
      throw new TaskSchedulerException(e.getMessage(), e.getCause());
    }
  }

  public void getSetEmojisEventsAndUpdateTraitValues() throws EthEventException {
    List<SetEmojisEvent> events =
        (List<SetEmojisEvent>)
            getEthEvents(
                SetEmojisEvent.class.getCanonicalName(),
                eventsConfig.getNftContractAddress(),
                eventsConfig.getSetEmojisEventHashSignature(),
                bigIntegerFactory.build(eventsConfig.getSchedulerNumberOfBlocksToLookBack()));
    updateTraitValuesForEthEvents(removeDuplicateEthEvents.remove(events));
    return;
  }

  private List<String> getTilesEmojiValues(SetEmojisEvent event) throws EthEventException {
    List<String> tilesEmojiValuesList = new ArrayList<>();
    String eventEmojisValue = strip0xFromHexString(event.getEmojis());
    int emojiSetIndex = getEmojiSetIndex(eventEmojisValue);
    System.out.println("DEBUG emojiSetIndex: " + emojiSetIndex);
    for (int x = 0; x < NUMBER_OF_SUB_TILES; x++) {
      String tileEmojiValue = getTileEmojiValue(eventEmojisValue, x, emojiSetIndex);
      System.out.println("DEBUG value " + x + ": " + tileEmojiValue);
      tilesEmojiValuesList.add(tileEmojiValue);
    }
    return tilesEmojiValuesList;
  }

  private int getEmojiSetIndex(String eventEmojisValue) {
    return bigIntegerFactory.build(eventEmojisValue.substring(28, 32), 16).intValue();
  }

  private String getTileEmojiValue(String eventEmojisValue, int tileIndex, int emojiSetIndex)
      throws EthEventException {
    if (emojiSetIndex != EMOJI_SET_SPECIFIER_INDEX) {
      throw new EthEventException(
          "Emoji set other than 0 was specified. Support does not exist for additional emoji sets at this time.");
    }
    return String.valueOf(
        bigIntegerFactory.build(
            eventEmojisValue.substring(getBeginIndex(tileIndex), getEndIndex(tileIndex)), 16));
  }

  private int getBeginIndex(int tileIndex) {
    return (tileIndex * 7);
  }

  private int getEndIndex(int tileIndex) {
    return getBeginIndex(tileIndex) + 7;
  }

  private void updateTraitValuesForEthEvent(SetEmojisEvent event, TokenFacadeDTO nft)
      throws EthEventException {
    int tileIndex = 0;
    List<WeightlessTraitDTO> traits = nft.getWeightlessTraits();
    List<WeightlessTraitDTO> traitsToUpdate = new ArrayList<>();
    WeightlessTraitDTO updateTrait;
    for (String tileEmojiValue : getTilesEmojiValues(event)) {
      switch (tileIndex) {
        case 0:
          updateTrait =
              updateTraitValue(
                  traits, tileEmojiValue, Long.valueOf(WeightlessTraitTypeConstants.TILE_1_EMOJI));
          if (updateTrait != null) {
            traitsToUpdate.add(updateTrait);
          }
          break;
        case 1:
          updateTrait =
              updateTraitValue(
                  traits, tileEmojiValue, Long.valueOf(WeightlessTraitTypeConstants.TILE_2_EMOJI));
          if (updateTrait != null) {
            traitsToUpdate.add(updateTrait);
          }
          break;
        case 2:
          updateTrait =
              updateTraitValue(
                  traits, tileEmojiValue, Long.valueOf(WeightlessTraitTypeConstants.TILE_3_EMOJI));
          if (updateTrait != null) {
            traitsToUpdate.add(updateTrait);
          }
          break;
        case 3:
          updateTrait =
              updateTraitValue(
                  traits, tileEmojiValue, Long.valueOf(WeightlessTraitTypeConstants.TILE_4_EMOJI));
          if (updateTrait != null) {
            traitsToUpdate.add(updateTrait);
          }
          break;
        default:
          break;
      }
      tileIndex++;
    }
    for (WeightlessTraitDTO traitToUpdate : traitsToUpdate) {
      weightlessTraitRepository.update(traitToUpdate);
      System.out.println("Updated tile emoji. Trait: " + traitToUpdate);
    }
  }

  private WeightlessTraitDTO updateTraitValue(
      List<WeightlessTraitDTO> traits, String tileEmojiValue, Long traitTypeId) {
    WeightlessTraitDTO trait =
        traits.stream()
            .filter(weightlessTraitDTO -> weightlessTraitDTO.getTraitTypeId().equals(traitTypeId))
            .findFirst()
            .get();
    if (tileEmojiValue.equals(trait.getValue())) {
      System.out.println(
          "Will not update emoji value trait for tile # "
              + trait.getTokenId()
              + " . Trait Values: "
              + trait);
      return null;
    }
    trait.setValue(tileEmojiValue);
    return trait;
  }

  private void updateTraitValuesForEthEvents(List<SetEmojisEvent> events) throws EthEventException {
    for (SetEmojisEvent event : events) {
      TokenFacadeDTO nft =
          tokenRetriever.get(Long.valueOf(strip0xFromHexString(event.getTokenId())));
      if (nft == null) {
        System.out.println("ERROR!!! This token does not exist");
        continue;
      }
      updateTraitValuesForEthEvent(event, nft);
    }
  }
}
