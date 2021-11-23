package com.tylerfitzgerald.demo_api.scheduler.tasks;

import com.tylerfitzgerald.demo_api.config.EventsConfig;
import com.tylerfitzgerald.demo_api.erc721.token.TokenInitializeException;
import com.tylerfitzgerald.demo_api.solidityEvents.AbstractEvent;
import com.tylerfitzgerald.demo_api.solidityEvents.EventRetriever;
import com.tylerfitzgerald.demo_api.solidityEvents.SetColorsEvent;
import com.tylerfitzgerald.demo_api.solidityEvents.SolidityEventException;
import com.tylerfitzgerald.demo_api.sql.tblTraits.TraitsTable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class HandleSetColorsEventsTask implements TaskInterface {
  private static final int NUMBER_OF_SUB_TILES = 4;
  private static final int NUMBER_OF_PIXEL_VALUES = 3;

  @Autowired private EventRetriever eventRetriever;

  @Autowired private EventsConfig eventsConfig;

  @Override
  public void execute() throws SolidityEventException {
    getSetColorsEventsAndUpdateTraitValues();
  }

  public void getSetColorsEventsAndUpdateTraitValues() throws SolidityEventException {
    List<SetColorsEvent> events =
        getSetColorsEvents(new BigInteger(eventsConfig.getSchedulerNumberOfBlocksToLookBack()));
    System.out.println("DEBUG set colors events: " + events);
    updateTraitValues(events);
    return;
  }

  private List<List<String>> getTilesRGBValues(SetColorsEvent event) {
    List<List<String>> tilesValuesList = new ArrayList<>();
    for (int x = 0; x < NUMBER_OF_SUB_TILES; x++) {
      List<String> tileValuesList = getTileRGBValues(event.getColors());
      tilesValuesList.add(tileValuesList);
    }
    return tilesValuesList;
  }

  private List<String> getTileRGBValues(String eventColorsValue) {
    List<String> tileValuesList = new ArrayList<>();
    int length = eventColorsValue.length();
    for (int x = 0; x < NUMBER_OF_PIXEL_VALUES; x++) {
      tileValuesList.add(
          eventColorsValue.substring(length - getBeginIndex(x, 0), length - getEndIndex(x, 0)));
      tileValuesList.add(
          eventColorsValue.substring(length - getBeginIndex(x, 1), length - getEndIndex(x, 1)));
      tileValuesList.add(
          eventColorsValue.substring(length - getBeginIndex(x, 2), length - getEndIndex(x, 2)));
    }
    return tileValuesList;
  }

  private int getBeginIndex(int tileIndex, int pixelIndex) {
    return (tileIndex * 9) + (pixelIndex * 3);
  }

  private int getEndIndex(int tileIndex, int pixelIndex) {
    return getBeginIndex(tileIndex, pixelIndex) + 3;
  }

  private void updateTraitValue(SetColorsEvent event) {
    System.out.println("DEBUG one event RGB values: " + getTilesRGBValues(event));
  }

  private void updateTraitValues(List<SetColorsEvent> events) {
    for (SetColorsEvent event : events) {
      updateTraitValue(event);
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

  private Long getLongFromHexString(String hexString) {
    return Long.parseLong(hexString.split("0x")[1], 16);
  }

  private Long getLongFromHexString(String hexString, int startIndex, int endIndex) {
    return Long.parseLong(hexString.split("0x")[1].substring(startIndex, endIndex), 16);
  }
}
