package com.tylerfitzgerald.demo_api.scheduler.tasks;

import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.token.TokenRetriever;
import com.tylerfitzgerald.demo_api.erc721.traits.WeightlessTraitTypeConstants;
import com.tylerfitzgerald.demo_api.scheduler.TaskSchedulerException;
import com.tylerfitzgerald.demo_api.ethEvents.events.SetColorsEvent;
import com.tylerfitzgerald.demo_api.ethEvents.EthEventException;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits.WeightlessTraitDTO;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits.WeightlessTraitRepository;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class HandleSetColorsEventsTask extends AbstractEthEventsRetrieverTask {
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
    List<SetColorsEvent> events =
        (List<SetColorsEvent>)
            getEthEvents(
                SetColorsEvent.class.getCanonicalName(),
                eventsConfig.getNftContractAddress(),
                eventsConfig.getSetColorsEventHashSignature(),
                new BigInteger(eventsConfig.getSchedulerNumberOfBlocksToLookBack()));
    updateTraitValuesForEvents(removeDuplicateEthEvents.remove(events));
    return;
  }

  private List<List<String>> getTilesRGBValues(SetColorsEvent event) {
    List<List<String>> tilesValuesList = new ArrayList<>();
    for (int x = 0; x < NUMBER_OF_SUB_TILES; x++) {
      List<String> tileValuesList = getTileRGBValues(strip0xFromHexString(event.getColors()), x);
      tilesValuesList.add(tileValuesList);
    }
    return tilesValuesList;
  }

  private List<String> getTileRGBValues(String eventColorsValue, int tileIndex) {
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

  private void updateTraitValuesForEvent(SetColorsEvent event, TokenFacadeDTO nft) {
    int tileIndex = 0;
    List<WeightlessTraitDTO> traits = nft.getWeightlessTraits();
    List<WeightlessTraitDTO> traitsToUpdate = new ArrayList<>();
    WeightlessTraitDTO updateTrait;
    System.out.println("DEBUG getTilesRGBValues: " + getTilesRGBValues(event));
    for (List<String> tileRGBValues : getTilesRGBValues(event)) {
      switch (tileIndex) {
        case 0:
          updateTrait =
              updateTraitValue(
                  traits, tileRGBValues, Long.valueOf(WeightlessTraitTypeConstants.TILE_1_COLOR));
          if (updateTrait != null) {
            traitsToUpdate.add(updateTrait);
          }
          break;
        case 1:
          updateTrait =
              updateTraitValue(
                  traits, tileRGBValues, Long.valueOf(WeightlessTraitTypeConstants.TILE_2_COLOR));
          if (updateTrait != null) {
            traitsToUpdate.add(updateTrait);
          }
          break;
        case 2:
          updateTrait =
              updateTraitValue(
                  traits, tileRGBValues, Long.valueOf(WeightlessTraitTypeConstants.TILE_3_COLOR));
          if (updateTrait != null) {
            traitsToUpdate.add(updateTrait);
          }
          break;
        case 3:
          updateTrait =
              updateTraitValue(
                  traits, tileRGBValues, Long.valueOf(WeightlessTraitTypeConstants.TILE_4_COLOR));
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
      System.out.println("Updated tile color. Trait: " + traitToUpdate);
    }
  }

  private WeightlessTraitDTO updateTraitValue(
      List<WeightlessTraitDTO> traits, List<String> tileRGBValues, Long traitTypeId) {
    WeightlessTraitDTO trait =
        traits.stream()
            .filter(weightlessTraitDTO -> weightlessTraitDTO.getTraitTypeId().equals(traitTypeId))
            .findFirst()
            .get();
    String rgbToSet = tileRGBValues.get(2) + tileRGBValues.get(1) + tileRGBValues.get(0);
    if (rgbToSet.equals(trait.getValue())) {
      System.out.println(
          "Will not update color value trait for tile # "
              + trait.getTokenId()
              + " . Trait Values: "
              + trait);
      return null;
    }
    trait.setValue(rgbToSet);
    return trait;
  }

  private void updateTraitValuesForEvents(List<SetColorsEvent> events) {
    for (SetColorsEvent event : events) {
      TokenFacadeDTO nft =
          tokenRetriever.get(Long.valueOf(strip0xFromHexString(event.getTokenId())));
      if (nft == null) {
        System.out.println("ERROR!!! This token does not exist");
        continue;
      }
      updateTraitValuesForEvent(event, nft);
    }
  }
}
