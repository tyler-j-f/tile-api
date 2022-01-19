package io.tilenft.scheduler.tasks;

import io.tilenft.config.external.EventsConfig;
import io.tilenft.etc.BigIntegerFactory;
import io.tilenft.etc.HexValueToDecimal;
import io.tilenft.etc.lists.finders.WeightlessTraitsListFinder;
import io.tilenft.eth.events.EthEventException;
import io.tilenft.eth.events.EthEventsRetriever;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractEthEventsRetrieverTask implements TaskInterface {

  @Autowired protected EthEventsRetriever ethEventsRetriever;
  @Autowired protected EventsConfig eventsConfig;
  @Autowired protected BigIntegerFactory bigIntegerFactory;
  @Autowired protected WeightlessTraitsListFinder weightlessTraitsListFinder;
  @Autowired protected HexValueToDecimal hexValueToDecimal;

  protected List<?> getEthEvents(
      String className,
      String ethContractAddress,
      String ethEventHashSignature,
      BigInteger numberOfBlocksAgo)
      throws EthEventException {
    List<?> events =
        (List<?>)
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
    return events;
  }
}
