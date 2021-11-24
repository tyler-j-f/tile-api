package com.tylerfitzgerald.demo_api.scheduler.tasks;

import com.tylerfitzgerald.demo_api.config.EventsConfig;
import com.tylerfitzgerald.demo_api.solidityEvents.EthEventsRetriever;
import com.tylerfitzgerald.demo_api.solidityEvents.RemoveDuplicateEvents;
import com.tylerfitzgerald.demo_api.solidityEvents.SolidityEventException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractEthEventsRetrieverTask implements TaskInterface {

  public static final String ZERO_X = "0x";

  @Autowired protected EthEventsRetriever ethEventsRetriever;
  @Autowired protected RemoveDuplicateEvents removeDuplicateEvents;
  @Autowired protected EventsConfig eventsConfig;

  protected List<?> getEthEvents(
      String className,
      String ethContractAddress,
      String ethEventHashSignature,
      BigInteger numberOfBlocksAgo)
      throws SolidityEventException {
    List<?> events =
        (List<?>)
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

  protected Long getLongFromHexString(String hexString) {
    return Long.parseLong(hexString.split(ZERO_X)[1], 16);
  }

  protected Long getLongFromHexString(String hexString, int startIndex, int endIndex) {
    return Long.parseLong(hexString.split(ZERO_X)[1].substring(startIndex, endIndex), 16);
  }

  protected String strip0xFromHexString(String hexString) {
    return hexString.split(ZERO_X)[1];
  }
}
