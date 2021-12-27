package io.tilenft.eth.events;

import io.tilenft.etc.HexStringPrefixStripper;
import io.tilenft.eth.events.implementations.AbstractSingleTokenEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class RemoveDuplicateEthEventsForToken<T extends AbstractSingleTokenEvent> {

  @Autowired private HexStringPrefixStripper hexStringPrefixStripper;

  public static final String ZERO_X = "0x";

  public List<T> remove(List<T> events) {
    List<T> sortedEventsList = new ArrayList<>();
    // Reverse the events so that most recent events are first.
    Collections.reverse(events);
    for (T event : events) {
      if (!doesEventsListAlreadyHaveTokenId(
          sortedEventsList,
          Long.valueOf(hexStringPrefixStripper.strip0xFromHexString(event.getTokenId())))) {
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
                event ->
                    Long.valueOf(hexStringPrefixStripper.strip0xFromHexString(event.getTokenId()))
                        .equals(tokenId))
            .count();
  }
}
