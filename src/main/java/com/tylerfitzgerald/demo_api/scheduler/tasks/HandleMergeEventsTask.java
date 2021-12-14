// As long as ETH/Solidity doesn't explode.... this should never happen. The ETH Contract code
// should only allow one Merge solidity event for two owned tokens... then they will be burned and
// another token will be minted.
package com.tylerfitzgerald.demo_api.scheduler.tasks;

import com.tylerfitzgerald.demo_api.erc721.ethEvents.EthEventException;
import com.tylerfitzgerald.demo_api.erc721.ethEvents.RemoveDuplicateMergeEthEvents;
import com.tylerfitzgerald.demo_api.erc721.ethEvents.events.MergeEvent;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.token.TokenRetriever;
import com.tylerfitzgerald.demo_api.erc721.token.initializers.MergeTokenInitializer;
import com.tylerfitzgerald.demo_api.erc721.token.initializers.TokenInitializeException;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightedTraits.WeightedTraitTypeConstants;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightedTraits.WeightedTraitWeightConstants;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.traitPickers.WeightlessTraitPickerException;
import com.tylerfitzgerald.demo_api.scheduler.TaskSchedulerException;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitDTO;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightedTraitRepository;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class HandleMergeEventsTask extends AbstractEthEventsRetrieverTask {

  @Autowired private TokenRetriever tokenRetriever;
  @Autowired private RemoveDuplicateMergeEthEvents removeDuplicateMergeEthEvents;
  @Autowired private MergeTokenInitializer mergeTokenInitializer;
  @Autowired private WeightedTraitRepository weightedTraitRepository;

  @Override
  public void execute() throws TaskSchedulerException {
    try {
      getMergeEventsAndMintNewTokens();
    } catch (EthEventException
        | IOException
        | TokenInitializeException
        | WeightlessTraitPickerException e) {
      throw new TaskSchedulerException(e.getMessage(), e.getCause());
    }
  }

  public void getMergeEventsAndMintNewTokens()
      throws EthEventException, IOException, TokenInitializeException,
          WeightlessTraitPickerException {
    List<MergeEvent> events =
        (List<MergeEvent>)
            getEthEvents(
                MergeEvent.class.getCanonicalName(),
                eventsConfig.getNftContractAddress(),
                eventsConfig.getMergeEventHashSignature(),
                bigIntegerFactory.build(eventsConfig.getSchedulerNumberOfBlocksToLookBack()));
    if (events.size() == 0) {
      System.out.println("HandleMergeEventsTask: Found no tasks.");
      return;
    }
    addTokensAndNewTraitsForMergeEvents(removeDuplicateMergeEthEvents.remove(events));
    return;
  }

  private void addTokensAndNewTraitsForMergeEvents(List<MergeEvent> events)
      throws TokenInitializeException, WeightlessTraitPickerException {
    for (MergeEvent event : events) {
      Long newTokenId = Long.valueOf(strip0xFromHexString(event.getNewTokenId()));
      System.out.println("\nFound merge event for new token. newTokenId: " + newTokenId);
      if (tokenRetriever.get(newTokenId) != null) {
        System.out.println("Token for merge event was already created. tokenId: " + newTokenId);
        continue;
      }
      TokenFacadeDTO burnedNft1 =
          tokenRetriever.get(Long.valueOf(strip0xFromHexString(event.getBurnedToken1Id())));
      TokenFacadeDTO burnedNft2 =
          tokenRetriever.get(Long.valueOf(strip0xFromHexString(event.getBurnedToken2Id())));
      if (burnedNft1 == null || burnedNft2 == null) {
        System.out.println(
            "ERROR!!! One of the requested tokens to burn, during merging, is not able to be retrieved.");
        continue;
      }
      addNewTraitForBurnEvent(burnedNft1);
      addNewTraitForBurnEvent(burnedNft2);
      mintNewTokenForMerge(event, burnedNft1, burnedNft2);
    }
  }

  private void mintNewTokenForMerge(
      MergeEvent event, TokenFacadeDTO burnedNft1, TokenFacadeDTO burnedNft2)
      throws TokenInitializeException, WeightlessTraitPickerException {
    Long tokenId = getLongFromHexString(event.getNewTokenId());
    mergeTokenInitializer.initialize(
        tokenId, burnedNft1, burnedNft2, getLongFromHexString(event.getTransactionHash(), 0, 9));
    System.out.println("New token created from merge event. tokenId: " + tokenId);
  }

  private void addNewTraitForBurnEvent(TokenFacadeDTO nft) {
    Long traitId = weightedTraitRepository.getCount() + 1L;
    weightedTraitRepository.create(
        WeightedTraitDTO.builder()
            .id(null)
            .traitId(traitId)
            .tokenId(nft.getTokenDTO().getTokenId())
            .traitTypeId((long) WeightedTraitTypeConstants.IS_BURNT_TOKEN_EQUALS_TRUE)
            .traitTypeWeightId((long) WeightedTraitWeightConstants.IS_BURNT_TOKEN_EQUALS_TRUE)
            .build());
    System.out.println(
        "Added burn trait for burnt token. tokenId: "
            + nft.getTokenDTO().getTokenId()
            + ", newTraitId: "
            + traitId);
  }
}
