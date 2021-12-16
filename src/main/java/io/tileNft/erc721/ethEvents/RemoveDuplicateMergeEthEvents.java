package io.tileNft.erc721.ethEvents;

import io.tileNft.erc721.ethEvents.events.MergeEvent;
import java.util.ArrayList;
import java.util.List;

public class RemoveDuplicateMergeEthEvents {

  public static final String ZERO_X = "0x";

  public List<MergeEvent> remove(List<MergeEvent> events) {
    List<MergeEvent> sortedEventsList = new ArrayList<>();
    for (MergeEvent event : events) {
      if (!doesEventsListAlreadyHaveTokenIdPair(
          sortedEventsList,
          Long.valueOf(strip0xFromHexString(event.getBurnedToken1Id())),
          Long.valueOf(strip0xFromHexString(event.getBurnedToken2Id())))) {
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
                    Long.valueOf(strip0xFromHexString(event.getBurnedToken1Id()))
                            .equals(burnedTokenId1)
                        && Long.valueOf(strip0xFromHexString(event.getBurnedToken2Id()))
                            .equals(burnedTokenId2))
            .count();
  }

  protected String strip0xFromHexString(String hexString) {
    return hexString.split(ZERO_X)[1];
  }
}
