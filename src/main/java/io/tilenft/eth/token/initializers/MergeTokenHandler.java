package io.tilenft.eth.token.initializers;

import io.tilenft.config.external.EventsConfig;
import io.tilenft.etc.BigIntegerFactory;
import io.tilenft.etc.HexValueToDecimal;
import io.tilenft.etc.lists.finders.WeightlessTraitsListFinder;
import io.tilenft.eth.events.EthEventsRetriever;
import io.tilenft.eth.token.TokenFacadeDTO;
import org.springframework.beans.factory.annotation.Autowired;

public class MergeTokenHandler {

  @Autowired private MergeTokenInitializer mergeTokenInitializer;
  @Autowired protected EthEventsRetriever ethEventsRetriever;
  @Autowired protected EventsConfig eventsConfig;
  @Autowired protected BigIntegerFactory bigIntegerFactory;
  @Autowired protected WeightlessTraitsListFinder weightlessTraitsListFinder;
  @Autowired protected HexValueToDecimal hexValueToDecimal;

  public void mintNewTokenForMerge(
      Long newTokenId, TokenFacadeDTO burnedNft1, TokenFacadeDTO burnedNft2, Long seedForTraits)
      throws TokenInitializeException {
    mergeTokenInitializer.initialize(newTokenId, burnedNft1, burnedNft2, seedForTraits);
  }
}
