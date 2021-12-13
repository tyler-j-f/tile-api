// Handling mint events will ALWAYS mean that we mint a new token. IT WAS ALWAYS THE NEW TOKENS!!!
package com.tylerfitzgerald.demo_api.scheduler.tasks;

import com.tylerfitzgerald.demo_api.config.external.EventsConfig;
import com.tylerfitzgerald.demo_api.erc721.TokenMetadataDTO;
import com.tylerfitzgerald.demo_api.erc721.ethEvents.EthEventException;
import com.tylerfitzgerald.demo_api.erc721.ethEvents.events.MintEvent;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeFactory;
import com.tylerfitzgerald.demo_api.erc721.token.initializers.TokenInitializeException;
import com.tylerfitzgerald.demo_api.scheduler.TaskSchedulerException;
import com.tylerfitzgerald.demo_api.sql.dtos.TokenDTO;
import com.tylerfitzgerald.demo_api.sql.repositories.TokenRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class HandleMintEventsTask extends AbstractEthEventsRetrieverTask {

  @Autowired private TokenRepository tokenRepository;
  @Autowired private EventsConfig eventsConfig;
  @Autowired private TokenFacadeFactory tokenFacadeFactory;

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
    addTokensToDB(events);
    return;
  }

  private List<MintEvent> getMintEvents() throws EthEventException {
    List<MintEvent> events =
        (List<MintEvent>)
            getEthEvents(
                MintEvent.class.getCanonicalName(),
                eventsConfig.getNftFactoryContractAddress(),
                eventsConfig.getMintEventHashSignature(),
                bigIntegerFactory.build(eventsConfig.getSchedulerNumberOfBlocksToLookBack()));
    if (events.size() == 0) {
      System.out.println("No mint events found");
      return new ArrayList<>();
    }
    return events;
  }

  private List<TokenMetadataDTO> addTokensToDB(List<MintEvent> events)
      throws TokenInitializeException {
    List<TokenMetadataDTO> tokens = new ArrayList<>();
    Long tokenId, transactionHash;
    for (MintEvent event : events) {
      tokenId = getLongFromHexString(event.getTokenId());
      transactionHash = getLongFromHexString(event.getTransactionHash(), 0, 9);
      TokenDTO existingTokenDTO = tokenRepository.readById(tokenId);
      System.out.println("\nFound mint event for new token. newTokenId: " + tokenId);
      if (existingTokenDTO != null) {
        System.out.println(
            "Token for mint event was previously added to the DB. tokenId: " + tokenId);
        continue;
      }
      TokenMetadataDTO token = addTokenToDB(tokenId, transactionHash);
      if (token == null) {
        System.out.println("Error adding Token for mint event to token DB. TokenId: " + tokenId);
        continue;
      }
      tokens.add(token);
      System.out.println("Added Token for mint event to token DB. TokenId: " + tokenId);
    }
    return tokens;
  }

  private TokenMetadataDTO addTokenToDB(Long tokenId, Long transactionHash)
      throws TokenInitializeException {
    return tokenFacadeFactory
        .getObject()
        .initializeToken(tokenId, transactionHash)
        .buildTokenMetadataDTO();
  }
}
