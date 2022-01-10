// As long as ETH/Solidity doesn't explode.... this should never happen. The ETH Contract code
// should only allow one Merge solidity event for two owned tokens... then they will be burned and
// another token will be minted.
package io.tilenft.scheduler.tasks;

import io.tilenft.eth.events.EthEventException;
import io.tilenft.eth.events.RemoveDuplicateMergeEthEvents;
import io.tilenft.eth.events.implementations.MergeEvent;
import io.tilenft.eth.token.TokenFacadeDTO;
import io.tilenft.eth.token.TokenRetriever;
import io.tilenft.eth.token.initializers.MergeTokenInitializer;
import io.tilenft.eth.token.initializers.TokenInitializeException;
import io.tilenft.eth.token.traits.weighted.WeightedTraitTypeConstants;
import io.tilenft.eth.token.traits.weighted.WeightedTraitTypeWeightConstants;
import io.tilenft.eth.token.traits.weightless.pickers.WeightlessTraitPickerException;
import io.tilenft.scheduler.TaskSchedulerException;
import io.tilenft.sql.dtos.WeightedTraitDTO;
import io.tilenft.sql.repositories.WeightedTraitRepository;
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
      Long newTokenId = hexValueToDecimal.getLongFromHexString(event.getNewTokenId());
      Long burnedToken1Id = hexValueToDecimal.getLongFromHexString(event.getBurnedToken1Id());
      Long burnedToken2Id = hexValueToDecimal.getLongFromHexString(event.getBurnedToken2Id());
      System.out.println(
          "\nFound merge event for new token. newTokenId: "
              + newTokenId
              + ", burnedToken1Id: "
              + burnedToken1Id
              + ", burnedToken2Id: "
              + burnedToken2Id);
      if (tokenRetriever.get(newTokenId) != null) {
        System.out.println("Token for merge event was already created. tokenId: " + newTokenId);
        continue;
      }
      TokenFacadeDTO burnedNft1 = tokenRetriever.get(burnedToken1Id);
      TokenFacadeDTO burnedNft2 = tokenRetriever.get(burnedToken2Id);
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
    Long tokenId = hexValueToDecimal.getLongFromHexString(event.getNewTokenId());
    mergeTokenInitializer.initialize(
        tokenId,
        burnedNft1,
        burnedNft2,
        hexValueToDecimal.getLongFromHexString(event.getTransactionHash()));
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
            .traitTypeWeightId((long) WeightedTraitTypeWeightConstants.IS_BURNT_TOKEN_EQUALS_TRUE)
            .build());
    System.out.println(
        "Added burn trait for burnt token. tokenId: "
            + nft.getTokenDTO().getTokenId()
            + ", newTraitId: "
            + traitId);
  }
}
