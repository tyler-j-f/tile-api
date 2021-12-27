package io.tilenft.scheduler.tasks;

import io.tilenft.eth.events.EthEventException;
import io.tilenft.eth.events.implementations.SetMetadataEvent;
import io.tilenft.eth.token.TokenFacadeDTO;
import io.tilenft.eth.token.TokenRetriever;
import io.tilenft.eth.token.traits.weightless.WeightlessTraitTypeConstants;
import io.tilenft.scheduler.TaskSchedulerException;
import io.tilenft.sql.dtos.WeightlessTraitDTO;
import io.tilenft.sql.repositories.WeightlessTraitRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class HandleSetColorsEventsTask extends AbstractMetadataSetEventsRetrieverTask {
  private static final int NUMBER_OF_SUB_TILES = 4;
  private static final int NUMBER_OF_PIXEL_VALUES = 3;

  @Autowired private TokenRetriever tokenRetriever;
  @Autowired private WeightlessTraitRepository weightlessTraitRepository;

  @Override
  public void execute() throws TaskSchedulerException {
    try {
      getSetColorsEventsAndUpdateTraitValues();
    } catch (EthEventException e) {
      throw new TaskSchedulerException(e.getMessage(), e.getCause());
    }
  }

  public void getSetColorsEventsAndUpdateTraitValues() throws EthEventException {
    List<SetMetadataEvent> events =
        super.getEthEvents(
            SetMetadataEvent.class.getCanonicalName(),
            eventsConfig.getNftContractAddress(),
            eventsConfig.getSetMetadataHashSignature(),
            bigIntegerFactory.build(eventsConfig.getSchedulerNumberOfBlocksToLookBack()),
            0);
    if (events.size() == 0) {
      System.out.println("HandleSetColorsEventsTask: Found no tasks.");
      return;
    }
    updateTraitValuesForEthEvents(removeDuplicateEthEventsForToken.remove(events));
    return;
  }

  private List<List<String>> getTilesRGBValues(SetMetadataEvent event) {
    List<List<String>> tilesValuesList = new ArrayList<>();
    for (int x = 0; x < NUMBER_OF_SUB_TILES; x++) {
      List<String> tileValuesList =
          getTileRGBValue(
              hexStringPrefixStripper.strip0xFromHexString(event.getMetadataToSet()), x);
      tilesValuesList.add(tileValuesList);
    }
    return tilesValuesList;
  }

  private List<String> getTileRGBValue(String eventColorsValue, int tileIndex) {
    List<String> tileValuesList = new ArrayList<>();
    for (int x = 0; x < NUMBER_OF_PIXEL_VALUES; x++) {
      tileValuesList.add(
          eventColorsValue.substring(getBeginIndex(tileIndex, x), getEndIndex(tileIndex, x)));
    }
    return tileValuesList;
  }

  private int getBeginIndex(int tileIndex, int pixelIndex) {
    return (tileIndex * 9) + (pixelIndex * 3);
  }

  private int getEndIndex(int tileIndex, int pixelIndex) {
    return getBeginIndex(tileIndex, pixelIndex) + 3;
  }

  private void updateTraitValuesForEthEvent(SetMetadataEvent event, TokenFacadeDTO nft) {
    int tileIndex = 0;
    List<WeightlessTraitDTO> traits = nft.getWeightlessTraits();
    List<WeightlessTraitDTO> traitsToUpdate = new ArrayList<>();
    WeightlessTraitDTO traitToUpdate;
    for (List<String> tileRGBValues : getTilesRGBValues(event)) {
      switch (tileIndex) {
        case 0:
          traitToUpdate =
              updateTraitValue(
                  traits, tileRGBValues, (long) WeightlessTraitTypeConstants.TILE_1_COLOR);
          if (traitToUpdate != null) {
            traitsToUpdate.add(traitToUpdate);
          }
          break;
        case 1:
          traitToUpdate =
              updateTraitValue(
                  traits, tileRGBValues, (long) WeightlessTraitTypeConstants.TILE_2_COLOR);
          if (traitToUpdate != null) {
            traitsToUpdate.add(traitToUpdate);
          }
          break;
        case 2:
          traitToUpdate =
              updateTraitValue(
                  traits, tileRGBValues, (long) WeightlessTraitTypeConstants.TILE_3_COLOR);
          if (traitToUpdate != null) {
            traitsToUpdate.add(traitToUpdate);
          }
          break;
        case 3:
          traitToUpdate =
              updateTraitValue(
                  traits, tileRGBValues, (long) WeightlessTraitTypeConstants.TILE_4_COLOR);
          if (traitToUpdate != null) {
            traitsToUpdate.add(traitToUpdate);
          }
          break;
        default:
          break;
      }
      tileIndex++;
    }
    for (WeightlessTraitDTO trait : traitsToUpdate) {
      weightlessTraitRepository.update(trait);
      System.out.println("Updated tile color. Trait: " + trait);
    }
  }

  private WeightlessTraitDTO updateTraitValue(
      List<WeightlessTraitDTO> traits, List<String> tileRGBValues, Long traitTypeId) {
    WeightlessTraitDTO trait =
        weightlessTraitsListFinder.findFirstByTraitTypeId(traits, traitTypeId);
    if (!validateOnePixelTripletValue(tileRGBValues.get(0))
        || !validateOnePixelTripletValue(tileRGBValues.get(1))
        || !validateOnePixelTripletValue(tileRGBValues.get(2))) {
      System.out.println(
          "HandleSetColorsEventsTask -> updateTraitValue Failure. Invalid RGB color values were most likely passed.");
      return null;
    }
    String rgbToSet = tileRGBValues.get(2) + tileRGBValues.get(1) + tileRGBValues.get(0);
    if (rgbToSet.equals(trait.getValue())) {
      System.out.println(
          "Trait value is already set. Will not update trait for tile #"
              + trait.getTokenId()
              + " . Trait Values: "
              + trait);
      return null;
    }
    trait.setValue(rgbToSet);
    return trait;
  }

  /**
   * Each RGB pixel is made of 3 sub-pixel triplets (One for R, one for G, one for B). We need to
   * validate that the value for all triplets can be converted into an integer, 0-255.
   *
   * @param tripletValue
   * @return
   */
  private boolean validateOnePixelTripletValue(String tripletValue) {
    try {
      return Integer.parseInt(tripletValue) < 256;
    } catch (Exception e) {
      System.out.println(
          "HandleSetColorsEventsTask -> validateOnePixelTripletValue Failure. Invalid RGB color tripletValue value was most likely passed. tripletValue: "
              + tripletValue);
      System.out.println(e);
      return false;
    }
  }

  private void updateTraitValuesForEthEvents(List<SetMetadataEvent> events) {
    for (SetMetadataEvent event : events) {
      TokenFacadeDTO nft =
          tokenRetriever.get(
              Long.valueOf(hexStringPrefixStripper.strip0xFromHexString(event.getTokenId())));
      if (nft == null) {
        System.out.println("ERROR!!! This token does not exist");
        continue;
      }
      updateTraitValuesForEthEvent(event, nft);
    }
  }
}
