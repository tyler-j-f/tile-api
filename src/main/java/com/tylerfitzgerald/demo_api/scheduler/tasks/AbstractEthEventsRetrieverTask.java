package com.tylerfitzgerald.demo_api.scheduler.tasks;

import com.tylerfitzgerald.demo_api.config.EventsConfig;
import com.tylerfitzgerald.demo_api.etc.BigIntegerFactory;
import com.tylerfitzgerald.demo_api.ethEvents.EthEventsRetriever;
import com.tylerfitzgerald.demo_api.ethEvents.RemoveDuplicateEthEventsForToken;
import com.tylerfitzgerald.demo_api.ethEvents.EthEventException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractEthEventsRetrieverTask implements TaskInterface {

  public static final String ZERO_X = "0x";

  @Autowired protected EthEventsRetriever ethEventsRetriever;
  @Autowired protected RemoveDuplicateEthEventsForToken removeDuplicateEthEventsForToken;
  @Autowired protected EventsConfig eventsConfig;
  @Autowired protected BigIntegerFactory bigIntegerFactory;

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

  protected BigInteger geBigIntFromHexString(String hexString) {
    return new BigInteger(strip0xFromHexString(hexString), 16);
  }

  protected Long getLongFromHexString(String hexString) {
    return Long.parseUnsignedLong(strip0xFromHexString(hexString), 16);
  }

  protected Long getLongFromHexString(String hexString, int startIndex, int endIndex) {
    return Long.parseUnsignedLong(
        strip0xFromHexString(hexString)
            .substring(startIndex, endIndex)
            .replaceFirst("^0+(?!$)", ""),
        16);
  }

  protected String strip0xFromHexString(String hexString) {
    return hexString.split(ZERO_X)[1].replaceFirst("^0+(?!$)", "");
  }
}
