package com.tylerfitzgerald.demo_api.scheduler.tasks;

import com.tylerfitzgerald.demo_api.config.EventsConfig;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.token.TokenRetriever;
import com.tylerfitzgerald.demo_api.erc721.traits.WeightlessTraitTypeConstants;
import com.tylerfitzgerald.demo_api.image.ImageException;
import com.tylerfitzgerald.demo_api.solidityEvents.EventRetriever;
import com.tylerfitzgerald.demo_api.solidityEvents.SetColorsEvent;
import com.tylerfitzgerald.demo_api.solidityEvents.SolidityEventException;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits.WeightlessTraitDTO;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class HandleSetColorsEventsTask implements TaskInterface {
  private static final int NUMBER_OF_SUB_TILES = 4;
  private static final int NUMBER_OF_PIXEL_VALUES = 3;

  @Autowired private EventRetriever eventRetriever;
  @Autowired private EventsConfig eventsConfig;
  @Autowired private TokenRetriever tokenRetriever;

  @Override
  public void execute() throws SolidityEventException {
    getSetColorsEventsAndUpdateTraitValues();
  }

  public void getSetColorsEventsAndUpdateTraitValues() throws SolidityEventException {
    List<SetColorsEvent> events =
        getSetColorsEvents(new BigInteger(eventsConfig.getSchedulerNumberOfBlocksToLookBack()));
    System.out.println("DEBUG set colors events: " + events);
    updateTraitValuesForEvents(events);
    return;
  }

  private List<List<String>> getTilesRGBValues(SetColorsEvent event) {
    List<List<String>> tilesValuesList = new ArrayList<>();
    for (int x = 0; x < NUMBER_OF_SUB_TILES; x++) {
      List<String> tileValuesList = getTileRGBValues(strip0xFromHexString(event.getColors()));
      tilesValuesList.add(tileValuesList);
    }
    return tilesValuesList;
  }

  private List<String> getTileRGBValues(String eventColorsValue) {
    List<String> tileValuesList = new ArrayList<>();
    for (int x = 0; x < NUMBER_OF_PIXEL_VALUES; x++) {
      tileValuesList.add(eventColorsValue.substring(getBeginIndex(x, 0), getEndIndex(x, 0)));
      tileValuesList.add(eventColorsValue.substring(getBeginIndex(x, 1), getEndIndex(x, 1)));
      tileValuesList.add(eventColorsValue.substring(getBeginIndex(x, 2), getEndIndex(x, 2)));
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
    WeightlessTraitDTO trait;
    for (List<String> tileRGBValues : getTilesRGBValues(event)) {
      switch (tileIndex) {
        case 0:
          trait =
              traits.stream()
                  .filter(
                      weightlessTraitDTO ->
                          weightlessTraitDTO
                              .getTraitTypeId()
                              .equals(Long.valueOf(WeightlessTraitTypeConstants.TILE_1_COLOR)))
                  .findFirst()
                  .get();
          System.out.println("DEBUG: ORIGINAL TRAIT: " + trait);
          trait.setValue(tileRGBValues.get(0) + tileRGBValues.get(1) + tileRGBValues.get(2));
          System.out.println("DEBUG: UPDATED TRAIT: " + trait);
          break;
        case 1:
          break;
        case 2:
          break;
        case 3:
          break;
        default:
          break;
      }
      tileIndex++;
    }
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

  private List<SetColorsEvent> getSetColorsEvents(BigInteger numberOfBlocksAgo)
      throws SolidityEventException {
    List<SetColorsEvent> events =
        (List<SetColorsEvent>)
            eventRetriever.getEvents(
                SetColorsEvent.class.getCanonicalName(),
                eventsConfig.getNftContractAddress(),
                eventsConfig.getSetColorsEventHashSignature(),
                numberOfBlocksAgo);
    if (events.size() == 0) {
      System.out.println("No set colors events found");
      return new ArrayList<>();
    }
    return events;
  }

  private String strip0xFromHexString(String hexString) {
    return hexString.split("0x")[1];
  }
}
