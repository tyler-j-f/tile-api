// Handling mint events will ALWAYS mean that we mint a new token. IT WAS ALWAYS THE NEW TOKENS!!!
package com.tylerfitzgerald.demo_api.scheduler.tasks;

import com.tylerfitzgerald.demo_api.config.EventsConfig;
import com.tylerfitzgerald.demo_api.erc721.token.TokenDataDTO;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacade;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.token.TokenInitializeException;
import com.tylerfitzgerald.demo_api.erc721.token.TokenInitializer;
import com.tylerfitzgerald.demo_api.scheduler.TaskSchedulerException;
import com.tylerfitzgerald.demo_api.ethEvents.events.MintEvent;
import com.tylerfitzgerald.demo_api.ethEvents.EthEventException;
import com.tylerfitzgerald.demo_api.sql.tblToken.TokenDTO;
import com.tylerfitzgerald.demo_api.sql.tblToken.TokenRepository;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class HandleMintEventsTask extends AbstractEthEventsRetrieverTask {

  @Autowired private TokenRepository tokenRepository;
  @Autowired private TokenInitializer tokenInitializer;
  @Autowired private EventsConfig eventsConfig;

  @Override
  public void execute() throws TaskSchedulerException {
    try {
      getMintEventsAndCreateTokens();
    } catch (EthEventException | TokenInitializeException e) {
      throw new TaskSchedulerException(e.getMessage(), e.getCause());
    }
  }

  public void getMintEventsAndCreateTokens() throws EthEventException, TokenInitializeException {
    List<MintEvent> events = getMintEvents();
    if (events.size() == 0) {
      System.out.println("HandleMintEventsTask: Found no tasks.");
      return;
    }
    addTokensToDB(events).toString();
    return;
  }

  private List<MintEvent> getMintEvents() throws EthEventException {
    List<MintEvent> events =
        (List<MintEvent>)
            getEthEvents(
                MintEvent.class.getCanonicalName(),
                eventsConfig.getNftContractAddress(),
                eventsConfig.getMintEventHashSignature(),
                bigIntegerFactory.build(eventsConfig.getSchedulerNumberOfBlocksToLookBack()));
    if (events.size() == 0) {
      System.out.println("No mint events found");
      return new ArrayList<>();
    }
    return events;
  }

  private List<TokenDataDTO> addTokensToDB(List<MintEvent> events) throws TokenInitializeException {
    List<TokenDataDTO> tokens = new ArrayList<>();
    Long tokenId, transactionHash;
    BigInteger fromAddress;
    for (MintEvent event : events) {
      System.out.println("DEBUG event: " + event);
      fromAddress = geBigIntFromHexString(event.getFromAddress());
      if (!fromAddress.equals(new BigInteger("0", 16))) {
        System.out.println("Transfer event, not from coinbase: " + event);
        continue;
      }
      tokenId = getLongFromHexString(event.getTokenId());
      transactionHash = getLongFromHexString(event.getTransactionHash(), 0, 9);
      TokenDTO existingTokenDTO = tokenRepository.readById(tokenId);
      if (existingTokenDTO != null) {
        System.out.println(
            "Token from mint event was already previously added to the token DB. \ntokenId: "
                + tokenId
                + "\nexistingTokenDTO: "
                + existingTokenDTO.toString());
        continue;
      }
      TokenDataDTO token = addTokenToDB(tokenId, transactionHash);
      if (token == null) {
        System.out.println("Error adding token from mint event to token DB. TokenId: " + tokenId);
        continue;
      }
      tokens.add(token);
      System.out.println("Added token from mint event to token DB. TokenId: " + tokenId);
    }
    return tokens;
  }

  private TokenDataDTO addTokenToDB(Long tokenId, Long transactionHash)
      throws TokenInitializeException {
    TokenFacadeDTO token = tokenInitializer.initialize(tokenId, transactionHash);
    if (token == null) {
      return null;
    }
    return new TokenFacade(token).buildTokenDataDTO();
  }
}
