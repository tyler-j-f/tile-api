// Handling mint events will ALWAYS mean that we mint a new token. IT WAS ALWAYS THE NEW TOKENS!!!
package com.tylerfitzgerald.demo_api.scheduler.tasks;

import com.tylerfitzgerald.demo_api.config.EventsConfig;
import com.tylerfitzgerald.demo_api.erc721.token.TokenDataDTO;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacade;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.token.TokenInitializeException;
import com.tylerfitzgerald.demo_api.erc721.token.TokenInitializer;
import com.tylerfitzgerald.demo_api.solidityEvents.AbstractEvent;
import com.tylerfitzgerald.demo_api.solidityEvents.EventRetriever;
import com.tylerfitzgerald.demo_api.sql.tblToken.TokenDTO;
import com.tylerfitzgerald.demo_api.sql.tblToken.TokenRepository;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class HandleSetColorsEventsTask implements TaskInterface {

  @Autowired private TokenRepository tokenRepository;

  @Autowired private TokenInitializer tokenInitializer;

  @Autowired private EventRetriever eventRetriever;

  @Autowired private EventsConfig eventsConfig;

  @Override
  public void execute() throws ExecutionException, InterruptedException, TokenInitializeException {
    getSetColorsEventsAndUpdateTraitValues();
  }

  public void getSetColorsEventsAndUpdateTraitValues()
      throws ExecutionException, InterruptedException, TokenInitializeException {
    List<AbstractEvent> events =
        getSetColorsEvents(new BigInteger(eventsConfig.getSchedulerNumberOfBlocksToLookBack()));
    System.out.println("DEBUG set colors: " + events);
    return;
  }

  private List<AbstractEvent> getSetColorsEvents(BigInteger numberOfBlocksAgo)
      throws ExecutionException, InterruptedException {
    List<?> events =
        eventRetriever.getEvents(
            eventsConfig.getNftFactoryContractAddress(),
            eventsConfig.getSetColorsEventHashSignature(),
            numberOfBlocksAgo);
    if (events.size() == 0) {
      System.out.println("No set colors events found");
      return new ArrayList<>();
    }
    return (List<AbstractEvent>) events;
  }

  private Long getLongFromHexString(String hexString) {
    return Long.parseLong(hexString.split("0x")[1], 16);
  }

  private Long getLongFromHexString(String hexString, int startIndex, int endIndex) {
    return Long.parseLong(hexString.split("0x")[1].substring(startIndex, endIndex), 16);
  }
}
