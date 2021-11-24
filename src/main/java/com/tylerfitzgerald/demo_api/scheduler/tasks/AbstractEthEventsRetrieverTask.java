package com.tylerfitzgerald.demo_api.scheduler.tasks;

import com.tylerfitzgerald.demo_api.solidityEvents.EthEventsRetriever;
import com.tylerfitzgerald.demo_api.solidityEvents.SetColorsEvent;
import com.tylerfitzgerald.demo_api.solidityEvents.SolidityEventException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractEthEventsRetrieverTask implements TaskInterface {

  @Autowired protected EthEventsRetriever ethEventsRetriever;

  protected List<SetColorsEvent> getEthEvents(
      String className,
      String ethContractAddress,
      String ethEventHashSignature,
      BigInteger numberOfBlocksAgo)
      throws SolidityEventException {
    List<SetColorsEvent> events =
        (List<SetColorsEvent>)
            ethEventsRetriever.getEvents(
                className, ethContractAddress, ethEventHashSignature, numberOfBlocksAgo);
    if (events.size() == 0) {
      System.out.println(
          "No  events found for classNAme: "
              + className
              + "\nethContractAddress: "
              + ethContractAddress
              + "\nethEventHashSignature: "
              + ethEventHashSignature
              + "\nnumberOfBlocksAgo: "
              + numberOfBlocksAgo);
      return new ArrayList<>();
    }
    return events;
  }
}
