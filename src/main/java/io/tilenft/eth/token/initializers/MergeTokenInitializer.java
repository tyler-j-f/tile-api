package io.tilenft.eth.token.initializers;

import io.tilenft.eth.token.TokenFacadeDTO;
import io.tilenft.eth.token.traits.creators.TraitsCreatorContext;
import io.tilenft.eth.token.traits.creators.weighted.WeightedTraitsCreator;
import io.tilenft.eth.token.traits.creators.weightless.MergeTokenWeightlessTraitsCreator;
import io.tilenft.eth.token.traits.weighted.WeightedTraitTypeConstants;
import io.tilenft.eth.token.traits.weighted.WeightedTraitTypeWeightConstants;
import io.tilenft.sql.dtos.WeightedTraitTypeWeightDTO;
import java.util.List;
import java.util.stream.Collectors;

public class MergeTokenInitializer extends AbstractTokenInitializer {

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

  public static final Long[] MULTIPLIER_WEIGHTED_TRAIT_TYPES = {
    (long) WeightedTraitTypeConstants.TILE_1_MULTIPLIER,
    (long) WeightedTraitTypeConstants.TILE_2_MULTIPLIER,
    (long) WeightedTraitTypeConstants.TILE_3_MULTIPLIER,
    (long) WeightedTraitTypeConstants.TILE_4_MULTIPLIER
  };

  public static final Long[] MULTIPLIER_TRAIT_TYPE_WEIGHTS_WITH_VALUE_OF_ONE = {
    (long) WeightedTraitTypeWeightConstants.TILE_1_MULTIPLIER_VALUE_1,
    (long) WeightedTraitTypeWeightConstants.TILE_2_MULTIPLIER_VALUE_1,
    (long) WeightedTraitTypeWeightConstants.TILE_3_MULTIPLIER_VALUE_1,
    (long) WeightedTraitTypeWeightConstants.TILE_4_MULTIPLIER_VALUE_1
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
        filterAndModifyWeightedTraitTypeWeights(weightedTraitTypeWeightRepository.read());
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

  /**
   * We want to modify the available weighted trait type weights when for merging. All tile X
   * multiplier traits should resolve to 1. To accomplish this, remove all multiplier trait type
   * weights besides the weights that correspond to a value of 1. for the value of 1 multiplier
   * weights that remain, set to a likelihood of 100.
   *
   * @param traitTypeWeightsList
   * @return
   */
  protected List<WeightedTraitTypeWeightDTO> filterAndModifyWeightedTraitTypeWeights(
      List<WeightedTraitTypeWeightDTO> traitTypeWeightsList) {
    List<WeightedTraitTypeWeightDTO> output =
        traitTypeWeightsList.stream()
            .filter(
                traitTypeWeight -> {
                  for (Long multiplierTraitType : MULTIPLIER_WEIGHTED_TRAIT_TYPES) {
                    if (traitTypeWeight.getTraitTypeId().equals(multiplierTraitType)) {
                      for (Long valueOfOneMultiplierWeight :
                          MULTIPLIER_TRAIT_TYPE_WEIGHTS_WITH_VALUE_OF_ONE) {
                        if (traitTypeWeight
                            .getTraitTypeWeightId()
                            .equals(valueOfOneMultiplierWeight)) {
                          // We will keep one weight for each multiplier trait type.
                          // This trait type we keep has the value of 1.
                          // Let's set the likelihood to 100 since it is now the only trait type
                          // weight.
                          traitTypeWeight.setLikelihood(100L);
                          return true;
                        }
                      }
                      // traitTypeWeight was not found in
                      // MULTIPLIER_TRAIT_TYPE_WEIGHTS_WITH_VALUE_OF_ONE
                      return false;
                    }
                  }
                  return true;
                })
            .collect(Collectors.toList());
    return output;
  }
}
