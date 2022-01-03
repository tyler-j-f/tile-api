package io.tilenft.scheduler.tasks;

import io.tilenft.config.external.EventsConfig;
import io.tilenft.etc.BigIntegerFactory;
import io.tilenft.etc.HexStringPrefixStripper;
import io.tilenft.etc.HexValueToDecimal;
import io.tilenft.etc.lists.finders.WeightlessTraitsListFinder;
import io.tilenft.eth.events.EthEventException;
import io.tilenft.eth.events.EthEventsRetriever;
import io.tilenft.eth.events.RemoveDuplicateEthEventsForToken;
import io.tilenft.eth.events.implementations.SetMetadataEvent;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractMetadataSetEventsRetrieverTask implements TaskInterface {

  @Autowired protected HexValueToDecimal hexValueToDecimal;
  @Autowired protected EthEventsRetriever ethEventsRetriever;
  @Autowired protected RemoveDuplicateEthEventsForToken removeDuplicateEthEventsForToken;
  @Autowired protected EventsConfig eventsConfig;
  @Autowired protected BigIntegerFactory bigIntegerFactory;
  @Autowired protected WeightlessTraitsListFinder weightlessTraitsListFinder;
  @Autowired protected HexStringPrefixStripper hexStringPrefixStripper;

  protected List<SetMetadataEvent> getEthEvents(
      String className,
      String ethContractAddress,
      String ethEventHashSignature,
      BigInteger numberOfBlocksAgo,
      int metadataToSetIndex)
      throws EthEventException {
    List<SetMetadataEvent> events =
        ethEventsRetriever.getEvents(
            className, ethContractAddress, ethEventHashSignature, numberOfBlocksAgo);
    if (events.size() == 0) {
      System.out.println(
          "\nNo events found for className: "
              + className
              + "\nethContractAddress: "
              + ethContractAddress
              + "\nethEventHashSignature: "
              + ethEventHashSignature
              + "\nnumberOfBlocksAgo: "
              + numberOfBlocksAgo
              + "\n");
      return new ArrayList<>();
    }
    return events.stream()
        .filter(
            event ->
                hexValueToDecimal.getLongFromHexString(event.getMetadataToSetIndex())
                    == (long) metadataToSetIndex)
        .collect(Collectors.toList());
  }
}
