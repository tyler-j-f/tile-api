package com.tylerfitzgerald.demo_api.scheduler.tasks;

import com.tylerfitzgerald.demo_api.config.EnvConfig;
import com.tylerfitzgerald.demo_api.erc721.token.TokenDataDTO;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacade;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.token.TokenInitializer;
import com.tylerfitzgerald.demo_api.events.MintEvent;
import com.tylerfitzgerald.demo_api.events.MintEventRetriever;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class HandleMintEventsAndCreateDBTokensTask implements TaskInterface {

  @Autowired private TokenInitializer tokenInitializer;

  @Autowired private MintEventRetriever mintEventRetriever;

  @Autowired private EnvConfig appConfig;

  @Override
  public void execute() throws ExecutionException, InterruptedException {
    getMintEventsAndCreateTokens();
  }

  public String getMintEventsAndCreateTokens() throws ExecutionException, InterruptedException {
    List<MintEvent> events =
        getMintEvents(new BigInteger(appConfig.getSchedulerNumberOfBlocksToLookBack()));
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
    Long tokenId;
    for (MintEvent event : events) {
      tokenId = getLongFromHexString(event.getTokenId());
      TokenDataDTO token = addTokenToDB(tokenId);
      if (token == null) {
        System.out.println("Error adding mint event to token DB. TokenId: " + tokenId);
        continue;
      }
      tokens.add(token);
    }
    return tokens;
  }

  private TokenDataDTO addTokenToDB(Long tokenId) {
    TokenFacadeDTO token = tokenInitializer.initialize(tokenId);
    if (token == null) {
      return null;
    }
    return new TokenFacade(token).buildTokenDataDTO();
  }

  private Long getLongFromHexString(String hexString) {
    return Long.parseLong(hexString.split("0x")[1], 16);
  }
}
