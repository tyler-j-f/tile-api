package io.tileNft.scheduler.tasks;

import io.tileNft.config.external.EventsConfig;
import io.tileNft.erc721.ethEvents.EthEventException;
import io.tileNft.erc721.ethEvents.EthEventsRetriever;
import io.tileNft.erc721.ethEvents.RemoveDuplicateEthEventsForToken;
import io.tileNft.etc.BigIntegerFactory;
import io.tileNft.etc.listFinders.WeightlessTraitsListFinder;
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
  @Autowired protected WeightlessTraitsListFinder weightlessTraitsListFinder;

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
