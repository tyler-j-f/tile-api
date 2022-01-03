package io.tilenft.eth.events;

import io.tilenft.etc.HexValueToDecimal;
import io.tilenft.eth.events.implementations.AbstractSingleTokenEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class RemoveDuplicateEthEventsForToken<T extends AbstractSingleTokenEvent> {

  @Autowired private HexValueToDecimal hexValueToDecimal;

  public List<T> remove(List<T> events) {
    List<T> sortedEventsList = new ArrayList<>();
    // Reverse the events so that most recent events are first.
    Collections.reverse(events);
    for (T event : events) {
      if (!doesEventsListAlreadyHaveTokenId(
          sortedEventsList, hexValueToDecimal.getLongFromHexString(event.getTokenId()))) {
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
            .filter(
                event -> hexValueToDecimal.getLongFromHexString(event.getTokenId()).equals(tokenId))
            .count();
  }
}
