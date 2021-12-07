package com.tylerfitzgerald.demo_api.erc721.ethEvents;

import com.tylerfitzgerald.demo_api.erc721.ethEvents.events.AbstractSingleTokenEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RemoveDuplicateEthEventsForToken<T extends AbstractSingleTokenEvent> {

  public static final String ZERO_X = "0x";

  public List<T> remove(List<T> events) {
    List<T> sortedEventsList = new ArrayList<>();
    // Reverse the events so that most recent events are first.
    Collections.reverse(events);
    for (T event : events) {
      if (!doesEventsListAlreadyHaveTokenId(
          sortedEventsList, Long.valueOf(strip0xFromHexString(event.getTokenId())))) {
        sortedEventsList.add(event);
      }
    }
    return sortedEventsList;
  }

  private boolean doesEventsListAlreadyHaveTokenId(List<T> events, Long tokenId) {
    return findNumberOfEventsWithTokenId(events, tokenId) > 0;
  }

  private int findNumberOfEventsWithTokenId(List<T> events, Long tokenId) {
    return (int)
        events.stream()
            .filter(event -> Long.valueOf(strip0xFromHexString(event.getTokenId())).equals(tokenId))
            .count();
  }

  protected String strip0xFromHexString(String hexString) {
    return hexString.split(ZERO_X)[1];
  }
}
