// Handling mint events will ALWAYS mean that we mint a new token. IT WAS ALWAYS THE NEW TOKENS!!!
package com.tylerfitzgerald.demo_api.scheduler.tasks;

import com.tylerfitzgerald.demo_api.config.EventsConfig;
import com.tylerfitzgerald.demo_api.erc721.token.TokenInitializeException;
import com.tylerfitzgerald.demo_api.solidityEvents.AbstractEvent;
import com.tylerfitzgerald.demo_api.solidityEvents.EventRetriever;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class HandleSetColorsEventsTask {}

//
//  @Autowired private EventRetriever eventRetriever;
//
//  @Autowired private EventsConfig eventsConfig;
//
//  @Override
//  public void execute() throws ExecutionException, InterruptedException, TokenInitializeException
// {
//    getSetColorsEventsAndUpdateTraitValues();
//  }
//
//  public void getSetColorsEventsAndUpdateTraitValues()
//      throws ExecutionException, InterruptedException, TokenInitializeException {
//    List<AbstractEvent> events =
//        getSetColorsEvents(new BigInteger(eventsConfig.getSchedulerNumberOfBlocksToLookBack()));
//    System.out.println("DEBUG set colors events: " + events);
//    if (events.get(0) != null) {
//      System.out.println("DEBUG the colors: " + events.get(0).getTokenId());
//    }
//    return;
//  }
//
//  private List<AbstractEvent> getSetColorsEvents(BigInteger numberOfBlocksAgo)
//      throws ExecutionException, InterruptedException {
//    List<?> events =
//        eventRetriever.getEvents(
//            eventsConfig.getNftContractAddress(),
//            eventsConfig.getSetColorsEventHashSignature(),
//            numberOfBlocksAgo);
//    if (events.size() == 0) {
//      System.out.println("No set colors events found");
//      return new ArrayList<>();
//    }
//    return (List<AbstractEvent>) events;
//  }
//
//  private Long getLongFromHexString(String hexString) {
//    return Long.parseLong(hexString.split("0x")[1], 16);
//  }
//
//  private Long getLongFromHexString(String hexString, int startIndex, int endIndex) {
//    return Long.parseLong(hexString.split("0x")[1].substring(startIndex, endIndex), 16);
//  }
// }
