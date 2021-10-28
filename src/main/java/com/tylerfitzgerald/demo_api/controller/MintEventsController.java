package com.tylerfitzgerald.demo_api.controller;

import com.tylerfitzgerald.demo_api.config.EnvConfig;
import com.tylerfitzgerald.demo_api.erc721.token.TokenDataDTO;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacade;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.token.TokenInitializer;
import com.tylerfitzgerald.demo_api.erc721.token.TokenRetriever;
import com.tylerfitzgerald.demo_api.events.MintEvent;
import com.tylerfitzgerald.demo_api.events.MintEventRetriever;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = {"/api/events/mint"})
public class MintEventsController extends BaseController {

  @Autowired private MintEventRetriever mintEventRetriever;

  @Autowired private TokenRetriever tokenRetriever;

  @Autowired private TokenInitializer tokenInitializer;

  @Autowired private EnvConfig appConfig;

  @GetMapping(value = {"getAll/{numberOfBlocksAgo}", "getAll"})
  public String getMintEvents(@PathVariable(required = false) String numberOfBlocksAgo)
      throws ExecutionException, InterruptedException {
    if (numberOfBlocksAgo == null) {
      numberOfBlocksAgo = appConfig.getSchedulerNumberOfBlocksToLookBack();
    }
    List<MintEvent> events = getMintEvents(new BigInteger(numberOfBlocksAgo));
    String output;
    if (events.size() == 0) {
      output = "No events found";
      System.out.println(output);
      return output;
    }
    output = events.toString();
    System.out.println(output);
    return output;
  }

  @GetMapping(value = {"getAllAndCreateTokens/{numberOfBlocksAgo}", "getAllAndCreateTokens"})
  public String getMintEventsAndCreateTokens(
      @PathVariable(required = false) String numberOfBlocksAgo)
      throws ExecutionException, InterruptedException {
    if (numberOfBlocksAgo == null) {
      numberOfBlocksAgo = appConfig.getSchedulerNumberOfBlocksToLookBack();
    }
    List<MintEvent> events = getMintEvents(new BigInteger(numberOfBlocksAgo));
    return addTokensToDB(events).toString();
  }

  private List<MintEvent> getMintEvents(BigInteger numberOfBlocksAgo)
      throws ExecutionException, InterruptedException {
    List<MintEvent> events = mintEventRetriever.getMintEvents(numberOfBlocksAgo);
    if (events.size() == 0) {
      System.out.println("No events found");
      return new ArrayList<>();
    }
    return events;
  }

  private List<TokenDataDTO> addTokensToDB(List<MintEvent> events) {
    List<TokenDataDTO> tokens = new ArrayList<>();
    for (MintEvent event : events) {
      TokenDataDTO token = addTokenToDB(event);
      if (token == null) {
        System.out.println("Error adding mint event to token DB. TokenId: " + event.getTokenId());
        continue;
      }
      tokens.add(token);
    }
    return tokens;
  }

  private TokenDataDTO addTokenToDB(MintEvent event) {
    TokenFacadeDTO token = tokenInitializer.initialize(Long.valueOf(event.getTokenId()));
    if (token == null) {
      return null;
    }
    return new TokenFacade(token).buildTokenDataDTO();
  }
}
