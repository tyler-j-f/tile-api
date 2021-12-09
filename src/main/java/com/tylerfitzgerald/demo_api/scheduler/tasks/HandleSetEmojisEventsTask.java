package com.tylerfitzgerald.demo_api.scheduler.tasks;

import com.tylerfitzgerald.demo_api.erc721.ethEvents.EthEventException;
import com.tylerfitzgerald.demo_api.erc721.ethEvents.events.SetEmojisEvent;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.token.TokenRetriever;
import com.tylerfitzgerald.demo_api.erc721.token.traits.WeightlessTraitTypeConstants;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.traitPickers.EmojiTraitPicker;
import com.tylerfitzgerald.demo_api.image.ImageResourcesLoader;
import com.tylerfitzgerald.demo_api.scheduler.TaskSchedulerException;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightlessTraitDTO;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightlessTraitRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

public class HandleSetEmojisEventsTask extends AbstractEthEventsRetrieverTask {
  private static final int NUMBER_OF_SUB_TILES = 4;
  private static final int EMOJI_SET_SPECIFIER_INDEX = 0;

  @Autowired private TokenRetriever tokenRetriever;
  @Autowired private ImageResourcesLoader imageResourcesLoader;
  @Autowired private EmojiTraitPicker emojiTraitPicker;
  @Autowired private WeightlessTraitRepository weightlessTraitRepository;

  @Override
  public void execute() throws TaskSchedulerException {
    try {
      getSetEmojisEventsAndUpdateTraitValues();
    } catch (EthEventException | IOException e) {
      throw new TaskSchedulerException(e.getMessage(), e.getCause());
    }
  }

  public void getSetEmojisEventsAndUpdateTraitValues() throws EthEventException, IOException {
    List<SetEmojisEvent> events =
        (List<SetEmojisEvent>)
            getEthEvents(
                SetEmojisEvent.class.getCanonicalName(),
                eventsConfig.getNftContractAddress(),
                eventsConfig.getSetEmojisEventHashSignature(),
                bigIntegerFactory.build(eventsConfig.getSchedulerNumberOfBlocksToLookBack()));
    if (events.size() == 0) {
      System.out.println("HandleSetEmojisEventsTask: Found no tasks.");
      return;
    }
    updateTraitValuesForEthEvents(removeDuplicateEthEventsForToken.remove(events));
    return;
  }

  private List<String> getTileEmojiIndexFromEvent(SetEmojisEvent event) throws EthEventException {
    List<String> tilesEmojiValuesList = new ArrayList<>();
    String eventEmojisValue = strip0xFromHexString(event.getEmojis());
    int emojiSetIndex = getEmojiSetIndex(eventEmojisValue);
    for (int x = 0; x < NUMBER_OF_SUB_TILES; x++) {
      String tileEmojiValue = getTileEmojiValue(eventEmojisValue, x, emojiSetIndex);
      tilesEmojiValuesList.add(tileEmojiValue);
    }
    return tilesEmojiValuesList;
  }

  private int getEmojiSetIndex(String eventEmojisValue) {
    return bigIntegerFactory.buildHex(eventEmojisValue.substring(28, 32), 16).intValue();
  }

  private String getTileEmojiValue(String eventEmojisValue, int tileIndex, int emojiSetIndex)
      throws EthEventException {
    if (emojiSetIndex != EMOJI_SET_SPECIFIER_INDEX) {
      throw new EthEventException(
          "Emoji set other than 0 was specified. Support does not exist for additional emoji sets at this time.");
    }
    return String.valueOf(
        bigIntegerFactory.buildHex(
            eventEmojisValue.substring(getBeginIndex(tileIndex), getEndIndex(tileIndex)), 16));
  }

  private int getBeginIndex(int tileIndex) {
    return (tileIndex * 7);
  }

  private int getEndIndex(int tileIndex) {
    return getBeginIndex(tileIndex) + 7;
  }

  private String getTraitValueToUpdate(Resource[] resorces, String tileEmojiIndex) {
    return emojiTraitPicker.stripExtension(
        getResourceFromResourcesList(resorces, tileEmojiIndex).getFilename());
  }

  private Resource getResourceFromResourcesList(Resource[] resorces, String tileEmojiIndex) {
    return resorces[Integer.valueOf(tileEmojiIndex)];
  }

  private void updateTraitValuesForEthEvent(SetEmojisEvent event, TokenFacadeDTO nft)
      throws EthEventException, IOException {
    int tileIndex = 0;
    List<WeightlessTraitDTO> traits = nft.getWeightlessTraits();
    List<WeightlessTraitDTO> traitsToUpdate = new ArrayList<>();
    WeightlessTraitDTO updateTrait;
    Resource[] resorces = imageResourcesLoader.getResources();
    for (String tileEmojiIndex : getTileEmojiIndexFromEvent(event)) {
      String valueToUpdate = getTraitValueToUpdate(resorces, tileEmojiIndex);
      switch (tileIndex) {
        case 0:
          updateTrait =
              updateTraitValue(
                  traits, valueToUpdate, Long.valueOf(WeightlessTraitTypeConstants.TILE_1_EMOJI));
          if (updateTrait != null) {
            traitsToUpdate.add(updateTrait);
          }
          break;
        case 1:
          updateTrait =
              updateTraitValue(
                  traits, valueToUpdate, Long.valueOf(WeightlessTraitTypeConstants.TILE_2_EMOJI));
          if (updateTrait != null) {
            traitsToUpdate.add(updateTrait);
          }
          break;
        case 2:
          updateTrait =
              updateTraitValue(
                  traits, valueToUpdate, Long.valueOf(WeightlessTraitTypeConstants.TILE_3_EMOJI));
          if (updateTrait != null) {
            traitsToUpdate.add(updateTrait);
          }
          break;
        case 3:
          updateTrait =
              updateTraitValue(
                  traits, valueToUpdate, Long.valueOf(WeightlessTraitTypeConstants.TILE_4_EMOJI));
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
        weightlessTraitsListFinder.findFirstByTraitTypeId(traits, traitTypeId);
    System.out.println("Pre updated trait. Trait: " + trait);
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

  private void updateTraitValuesForEthEvents(List<SetEmojisEvent> events)
      throws EthEventException, IOException {
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
