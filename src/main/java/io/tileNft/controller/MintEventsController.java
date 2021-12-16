package io.tileNft.controller;

import io.tileNft.config.external.EventsConfig;
import io.tileNft.erc721.TokenMetadataDTO;
import io.tileNft.erc721.ethEvents.EthEventException;
import io.tileNft.erc721.ethEvents.EthEventsRetriever;
import io.tileNft.erc721.ethEvents.events.MintEvent;
import io.tileNft.erc721.token.TokenFacade;
import io.tileNft.erc721.token.initializers.TokenInitializeException;
import io.tileNft.erc721.token.initializers.TokenInitializer;
import io.tileNft.etc.BigIntegerFactory;
import io.tileNft.scheduler.tasks.AbstractEthEventsRetrieverTask;
import io.tileNft.sql.dtos.TokenDTO;
import io.tileNft.sql.repositories.TokenRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api/events/mint"})
public class MintEventsController extends BaseController {

  @Autowired private TokenRepository tokenRepository;
  @Autowired private BigIntegerFactory bigIntegerFactory;
  @Autowired private TokenInitializer tokenInitializer;
  @Autowired private EventsConfig eventsConfig;
  @Autowired private EthEventsRetriever ethEventsRetriever;
  @Autowired private TokenFacade tokenFacade;

  @GetMapping(value = {"getAll/{numberOfBlocksAgo}", "getAll"})
  public String getAll(@PathVariable(required = false) String numberOfBlocksAgo)
      throws EthEventException {
    if (numberOfBlocksAgo == null) {
      numberOfBlocksAgo = eventsConfig.getSchedulerNumberOfBlocksToLookBack();
    }
    List<MintEvent> events = getMintEvents(numberOfBlocksAgo);
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
  public String getAllAndCreateTokens(@PathVariable(required = false) String numberOfBlocksAgo)
      throws TokenInitializeException, EthEventException {
    if (numberOfBlocksAgo == null) {
      numberOfBlocksAgo = eventsConfig.getSchedulerNumberOfBlocksToLookBack();
    }
    List<MintEvent> events = getMintEvents(numberOfBlocksAgo);
    return addTokensToDB(events).toString();
  }

  private List<MintEvent> getMintEvents(String schedulerNumberOfBlocksToLookBack)
      throws EthEventException {
    List<MintEvent> events =
        ethEventsRetriever.getEvents(
            MintEvent.class.getCanonicalName(),
            eventsConfig.getNftFactoryContractAddress(),
            eventsConfig.getMintEventHashSignature(),
            bigIntegerFactory.build(schedulerNumberOfBlocksToLookBack));
    if (events.size() == 0) {
      System.out.println("No events found");
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
      transactionHash = getLongFromHexString(event.getTransactionHash());
      TokenDTO existingTokenDTO = tokenRepository.readById(tokenId);
      System.out.println("\nFound mint event for new token. newTokenId: " + tokenId);
      if (existingTokenDTO != null) {
        System.out.println(
            "Token for mint event was previously added to the DB. tokenId: " + tokenId);
        continue;
      }
      TokenMetadataDTO token = addTokenToDB(tokenId, transactionHash);
      if (token == null) {
        System.out.println(
            "\nError adding Token for mint event to token DB. TokenId: " + tokenId + "\n");
        continue;
      }
      tokens.add(token);
      System.out.println("\nAdded Token for mint event to token DB. TokenId: " + tokenId + "\n");
    }
    return tokens;
  }

  private TokenMetadataDTO addTokenToDB(Long tokenId, Long transactionHash)
      throws TokenInitializeException {
    return tokenFacade.initializeToken(tokenId, transactionHash).buildTokenMetadataDTO();
  }

  private Long getLongFromHexString(String hexString) {
    return Long.parseLong(hexString.split(AbstractEthEventsRetrieverTask.ZERO_X)[1], 16);
  }
}
