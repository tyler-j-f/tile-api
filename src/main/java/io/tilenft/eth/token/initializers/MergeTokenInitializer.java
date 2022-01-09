package io.tilenft.eth.token.initializers;

import io.tilenft.etc.lists.finders.WeightedTraitTypeWeightsListFinder;
import io.tilenft.eth.token.TokenFacadeDTO;
import io.tilenft.eth.token.traits.creators.TraitsCreatorContext;
import io.tilenft.eth.token.traits.creators.weighted.WeightedTraitsCreator;
import io.tilenft.eth.token.traits.creators.weightless.MergeTokenWeightlessTraitsCreator;
import io.tilenft.eth.token.traits.weighted.WeightedTraitTypeConstants;
import io.tilenft.eth.token.traits.weighted.WeightedTraitTypeWeightConstants;
import io.tilenft.sql.dtos.WeightedTraitTypeWeightDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class MergeTokenInitializer extends AbstractTokenInitializer {

  @Autowired private WeightedTraitTypeWeightsListFinder weightedTraitTypeWeightsListFinder;

  public MergeTokenInitializer(
      MergeTokenWeightlessTraitsCreator weightlessTraitsCreator,
      WeightedTraitsCreator weightedTraitsCreator) {
    super(weightlessTraitsCreator, weightedTraitsCreator);
  }

  public static final int[] WEIGHTED_TRAIT_TYPES_TO_IGNORE = {
    WeightedTraitTypeConstants.TILE_1_RARITY,
    WeightedTraitTypeConstants.TILE_2_RARITY,
    WeightedTraitTypeConstants.TILE_3_RARITY,
    WeightedTraitTypeConstants.TILE_4_RARITY,
    WeightedTraitTypeConstants.IS_BURNT_TOKEN_EQUALS_TRUE
  };

  public TokenFacadeDTO initialize(
      Long tokenId, TokenFacadeDTO burnedNft1, TokenFacadeDTO burnedNft2, Long seedForTraits)
      throws TokenInitializeException {
    if (burnedNft1 == null) {
      System.out.println(
          "TokenInitializer failed to load burned token 1. burnedNft1: " + burnedNft1);
      return null;
    }
    if (burnedNft2 == null) {
      System.out.println(
          "TokenInitializer failed to load burned token 2. burnedNft2: " + burnedNft2);
      return null;
    }
    tokenDTO = createToken(tokenId);
    if (tokenDTO == null) {
      System.out.println(
          "TokenInitializer failed to initialize the token with tokenId: " + tokenId);
      return null;
    }
    weightedTraitTypes =
        filterOutWeightedTraitTypesToIgnore(
            weightedTraitTypeRepository.read(), WEIGHTED_TRAIT_TYPES_TO_IGNORE);
    weightedTraitTypeWeights =
        filterOutWeightedTraitTypeWeightsToIgnore(
            weightedTraitTypeWeightRepository.read(),
            WeightedTraitTypeWeightConstants.MERGE_MULTIPLIER_TRAITS_TO_IGNORE);
    weightlessTraitTypes = weightlessTraitTypeRepository.read();
    weightedTraitsCreator.createTraits(getContext(tokenId, seedForTraits, burnedNft1, burnedNft2));
    weightedTraits = weightedTraitsCreator.getCreatedWeightedTraits();
    weightlessTraitsCreator.createTraits(
        getContext(tokenId, seedForTraits, burnedNft1, burnedNft2));
    return buildTokenFacadeDTO();
  }

  private TraitsCreatorContext getContext(
      Long tokenId, Long seedForTraits, TokenFacadeDTO burnedNft1, TokenFacadeDTO burnedNft2) {
    return TraitsCreatorContext.builder()
        .tokenId(tokenId)
        .seedForTraits(seedForTraits)
        .weightlessTraitTypes(weightlessTraitTypes)
        .weightedTraits(weightedTraits)
        .weightedTraitTypes(weightedTraitTypes)
        .weightedTraitTypeWeights(weightedTraitTypeWeights)
        .burnedNft1(burnedNft1)
        .burnedNft2(burnedNft2)
        .build();
  }

  protected List<WeightedTraitTypeWeightDTO> filterOutWeightedTraitTypeWeightsToIgnore(
      List<WeightedTraitTypeWeightDTO> traitTypeWeightsList, int[] traitTypeWeightIdsToIgnore) {
    List<WeightedTraitTypeWeightDTO> output =
        weightedTraitTypeWeightsListFinder.findByIgnoringTraitTypeWeightIdList(
            traitTypeWeightsList, traitTypeWeightIdsToIgnore);
    System.out.println("filterOutWeightedTraitTypeWeightsToIgnore output: " + output);
    return output;
  }
}
