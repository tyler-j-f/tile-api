package io.tilenft.eth.events;

import io.tilenft.etc.HexValueToDecimal;
import io.tilenft.eth.events.implementations.MergeEvent;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class RemoveDuplicateMergeEthEvents {

  @Autowired private HexValueToDecimal hexValueToDecimal;

  public List<MergeEvent> remove(List<MergeEvent> events) {
    List<MergeEvent> sortedEventsList = new ArrayList<>();
    for (MergeEvent event : events) {
      if (!doesEventsListAlreadyHaveTokenIdPair(
          sortedEventsList,
          Long.valueOf(hexValueToDecimal.getLongFromHexString(event.getBurnedToken1Id())),
          Long.valueOf(hexValueToDecimal.getLongFromHexString(event.getBurnedToken2Id())))) {
        sortedEventsList.add(event);
      }
    }
    return sortedEventsList;
  }

  private boolean doesEventsListAlreadyHaveTokenIdPair(
      List<MergeEvent> events, Long burnedTokenId1, Long burnedTokenId2) {
    return findNumberOfEventsWithTokenIdPair(events, burnedTokenId1, burnedTokenId2) > 0;
  }

  private int findNumberOfEventsWithTokenIdPair(
      List<MergeEvent> events, Long burnedTokenId1, Long burnedTokenId2) {
    return (int)
        events.stream()
            .filter(
                event ->
                    Long.valueOf(hexValueToDecimal.getLongFromHexString(event.getBurnedToken1Id()))
                            .equals(burnedTokenId1)
                        && Long.valueOf(
                                hexValueToDecimal.getLongFromHexString(event.getBurnedToken2Id()))
                            .equals(burnedTokenId2))
            .count();
  }
}
