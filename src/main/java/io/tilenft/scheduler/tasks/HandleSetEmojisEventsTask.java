package io.tilenft.scheduler.tasks;

import io.tilenft.eth.events.EthEventException;
import io.tilenft.eth.events.implementations.SetMetadataEvent;
import io.tilenft.eth.token.TokenFacadeDTO;
import io.tilenft.eth.token.traits.weightless.WeightlessTraitTypeConstants;
import io.tilenft.eth.token.traits.weightless.pickers.EmojiTraitPicker;
import io.tilenft.image.ImageResourcesLoader;
import io.tilenft.scheduler.TaskSchedulerException;
import io.tilenft.sql.dtos.WeightlessTraitDTO;
import io.tilenft.sql.repositories.WeightlessTraitRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;

public class HandleSetEmojisEventsTask extends AbstractMetadataSetEventsRetrieverTask {
  private static final int NUMBER_OF_SUB_TILES = 4;
  private static final int EMOJI_SET_SPECIFIER_INDEX = 0;

  @Qualifier("emojiResourceLoader")
  @Autowired
  private ImageResourcesLoader emojiResourcesLoader;

  @Autowired private EmojiTraitPicker emojiTraitPicker;
  @Autowired private WeightlessTraitRepository weightlessTraitRepository;

  @Override
  public void execute() throws TaskSchedulerException {
    try {
      handleSetEmojisEvents();
    } catch (EthEventException | IOException e) {
      throw new TaskSchedulerException(e.getMessage(), e.getCause());
    }
  }

  public void handleSetEmojisEvents() throws EthEventException, IOException {
    List<SetMetadataEvent> events =
        getEthEvents(
            SetMetadataEvent.class.getCanonicalName(),
            eventsConfig.getNftContractAddress(),
            eventsConfig.getSetMetadataHashSignature(),
            bigIntegerFactory.build(eventsConfig.getSchedulerNumberOfBlocksToLookBack()),
            1);
    if (events.size() == 0) {
      System.out.println("HandleSetEmojisEventsTask: Found no tasks.");
      return;
    }
    updateTraitValuesForEthEvents(removeDuplicateEthEventsForToken.remove(events));
    return;
  }

  private List<String> getTileEmojiIndexFromEvent(SetMetadataEvent event) throws EthEventException {
    List<String> tilesEmojiValuesList = new ArrayList<>();
    String eventEmojisValue =
        hexStringPrefixStripper.strip0xFromHexString(event.getMetadataToSet());
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

  /**
   * @param eventEmojisValue
   * @param tileIndex
   * @param emojiSetIndex There is currently only an emoji set, from openmoji.org
   * @return
   * @throws EthEventException
   */
  private String getTileEmojiValue(String eventEmojisValue, int tileIndex, int emojiSetIndex)
      throws EthEventException {
    if (emojiSetIndex != EMOJI_SET_SPECIFIER_INDEX) {
      throw new EthEventException(
          "Emoji set other than 0 was specified. Support does not exist for additional emoji sets at this time.");
    }
    return eventEmojisValue.substring(getBeginIndex(tileIndex), getEndIndex(tileIndex));
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

  private boolean validateTraitValueToUpdate(String tileEmojiIndex, int lengthOfResourcesArray) {
    return Integer.parseInt(tileEmojiIndex) <= lengthOfResourcesArray;
  }

  private Resource getResourceFromResourcesList(Resource[] resorces, String tileEmojiIndex) {
    return resorces[Integer.parseInt(tileEmojiIndex)];
  }

  protected void updateTraitValuesForEthEvent(SetMetadataEvent event, TokenFacadeDTO nft)
      throws EthEventException, IOException {
    int tileIndex = 0;
    List<WeightlessTraitDTO> traits = nft.getWeightlessTraits();
    List<WeightlessTraitDTO> traitsToUpdate = new ArrayList<>();
    WeightlessTraitDTO updateTrait;
    Resource[] resources = emojiResourcesLoader.getResources();
    System.out.println(
        "debug emojiResourcesLoader loaded: "
            + Arrays.toString(Arrays.copyOfRange(resources, 0, 20)));
    int resourcesArrayLength = resources.length;
    for (String tileEmojiIndex : getTileEmojiIndexFromEvent(event)) {
      if (!validateTraitValueToUpdate(tileEmojiIndex, resourcesArrayLength)) {
        System.out.println(
            "Invalid emoji index specified. tileEmojiIndex: "
                + tileEmojiIndex
                + ", resourcesArrayLength: "
                + resourcesArrayLength);
        continue;
      }
      String valueToUpdate = getTraitValueToUpdate(resources, tileEmojiIndex);
      switch (tileIndex) {
        case 0:
          updateTrait =
              updateTraitValue(
                  traits, valueToUpdate, (long) WeightlessTraitTypeConstants.TILE_1_EMOJI);
          if (updateTrait != null) {
            traitsToUpdate.add(updateTrait);
          }
          break;
        case 1:
          updateTrait =
              updateTraitValue(
                  traits, valueToUpdate, (long) WeightlessTraitTypeConstants.TILE_2_EMOJI);
          if (updateTrait != null) {
            traitsToUpdate.add(updateTrait);
          }
          break;
        case 2:
          updateTrait =
              updateTraitValue(
                  traits, valueToUpdate, (long) WeightlessTraitTypeConstants.TILE_3_EMOJI);
          if (updateTrait != null) {
            traitsToUpdate.add(updateTrait);
          }
          break;
        case 3:
          updateTrait =
              updateTraitValue(
                  traits, valueToUpdate, (long) WeightlessTraitTypeConstants.TILE_4_EMOJI);
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
}
