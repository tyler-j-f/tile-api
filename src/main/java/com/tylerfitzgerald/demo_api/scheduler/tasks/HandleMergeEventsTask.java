package com.tylerfitzgerald.demo_api.scheduler.tasks;

import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.token.TokenRetriever;
import com.tylerfitzgerald.demo_api.ethEvents.EthEventException;
import com.tylerfitzgerald.demo_api.ethEvents.RemoveDuplicateEthEventsForToken;
import com.tylerfitzgerald.demo_api.ethEvents.RemoveDuplicateMergeEthEvents;
import com.tylerfitzgerald.demo_api.ethEvents.events.MergeEvent;
import com.tylerfitzgerald.demo_api.scheduler.TaskSchedulerException;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits.WeightlessTraitDTO;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class HandleMergeEventsTask extends AbstractEthEventsRetrieverTask {

  @Autowired private TokenRetriever tokenRetriever;
  @Autowired protected RemoveDuplicateMergeEthEvents removeDuplicateMergeEthEvents;

  @Override
  public void execute() throws TaskSchedulerException {
    try {
      getMergeEventsAndMintNewTokens();
    } catch (EthEventException | IOException e) {
      throw new TaskSchedulerException(e.getMessage(), e.getCause());
    }
  }

  public void getMergeEventsAndMintNewTokens() throws EthEventException, IOException {
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
    System.out.println("HandleMergeEventsTask, found tasks: " + events);
    List<MergeEvent> eventsAfterRemove = removeDuplicateMergeEthEvents.remove(events);
    System.out.println("eventsAfterRemove: " + events);
    updateTraitValuesForBurnEvents(eventsAfterRemove);
    return;
  }

  // TODO move updateTraitValue to another class or to a common parent.
  private WeightlessTraitDTO updateTraitValue(
      List<WeightlessTraitDTO> traits, String tileEmojiValue, Long traitTypeId) {
    WeightlessTraitDTO trait =
        traits.stream()
            .filter(weightlessTraitDTO -> weightlessTraitDTO.getTraitTypeId().equals(traitTypeId))
            .findFirst()
            .get();
    System.out.println("Pre updated trait. Trait: " + trait);
    if (tileEmojiValue.equals(trait.getValue())) {
      System.out.println(
          "Will not update emoji value trait for tile # "
              + trait.getTokenId()
              + " . Trait Values: "
              + trait);
      return null;
    }
    trait.setValue(tileEmojiValue);
    return trait;
  }

  private void updateTraitValuesForBurnEvents(List<MergeEvent> events) throws EthEventException {
    for (MergeEvent event : events) {
      TokenFacadeDTO burnedNft1 =
          tokenRetriever.get(Long.valueOf(strip0xFromHexString(event.getBurnedToken1Id())));
      TokenFacadeDTO burnedNft2 =
          tokenRetriever.get(Long.valueOf(strip0xFromHexString(event.getBurnedToken2Id())));
      if (burnedNft1 == null || burnedNft2 == null) {
        System.out.println(
            "ERROR!!! One of the requested tokens to burn, during merging, is not able to be retrieved.");
        continue;
      }
      updateTokenTraitValuesForBurnEvent(burnedNft1);
      updateTokenTraitValuesForBurnEvent(burnedNft2);
      mintNewTokenForMerge(event, burnedNft1, burnedNft2);
    }
  }

  private void mintNewTokenForMerge(
      MergeEvent event, TokenFacadeDTO burnedNft1, TokenFacadeDTO burnedNft2)
      throws EthEventException {
    System.out.println("mintNewTokenForMerge called");
  }

  private void updateTokenTraitValuesForBurnEvent(TokenFacadeDTO nft) throws EthEventException {
    System.out.println("updateTraitValuesForBurnEvent called. \n event: " + nft);
  }
}
