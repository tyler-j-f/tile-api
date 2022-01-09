package io.tilenft.eth.token.initializers;

import io.tilenft.etc.lists.finders.WeightedTraitTypeWeightsListFinder;
import io.tilenft.eth.token.TokenFacadeDTO;
import io.tilenft.eth.token.traits.creators.TraitsCreatorContext;
import io.tilenft.eth.token.traits.creators.weighted.WeightedTraitsCreator;
import io.tilenft.eth.token.traits.creators.weightless.MergeTokenWeightlessTraitsCreator;
import io.tilenft.eth.token.traits.weighted.WeightedTraitTypeConstants;
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
    weightedTraitTypes = weightedTraitTypeRepository.read();
    weightedTraitTypeWeights = weightedTraitTypeWeightRepository.read();
    weightlessTraitTypes = weightlessTraitTypeRepository.read();
    weightedTraitsCreator.createTraits(getContext(tokenId, seedForTraits, burnedNft1, burnedNft2));
    weightedTraits = weightedTraitsCreator.getCreatedWeightedTraits();
    weightlessTraitsCreator.createTraits(
        getContext(tokenId, seedForTraits, burnedNft1, burnedNft2));
    return buildTokenFacadeDTO();
  }

  private TraitsCreatorContext getContext(
      Long tokenId, Long seedForTraits, TokenFacadeDTO burnedNft1, TokenFacadeDTO burnedNft2) {
    filterAndUpdateWeightedTraitTypeWeights();
    return TraitsCreatorContext.builder()
        .tokenId(tokenId)
        .seedForTraits(seedForTraits)
        .weightlessTraitTypes(weightlessTraitTypes)
        .weightedTraits(weightedTraits)
        .weightedTraitTypes(
            filterOutWeightedTraitTypesToIgnore(weightedTraitTypes, WEIGHTED_TRAIT_TYPES_TO_IGNORE))
        .weightedTraitTypeWeights(weightedTraitTypeWeights)
        .burnedNft1(burnedNft1)
        .burnedNft2(burnedNft2)
        .build();
  }

  private List<WeightedTraitTypeWeightDTO> filterAndUpdateWeightedTraitTypeWeights() {
    System.out.println(
        "weightedTraitTypeWeights, before filtering and modifying: " + weightedTraitTypeWeights);
    Long[] traitTypeIds = new Long[4];
    traitTypeIds[0] = (long) WeightedTraitTypeConstants.TILE_1_MULTIPLIER;
    traitTypeIds[1] = (long) WeightedTraitTypeConstants.TILE_2_MULTIPLIER;
    traitTypeIds[2] = (long) WeightedTraitTypeConstants.TILE_3_MULTIPLIER;
    traitTypeIds[3] = (long) WeightedTraitTypeConstants.TILE_4_MULTIPLIER;
    int nextWeightedTraitTypeWeightId = weightedTraitTypeWeights.size() + 1;
    weightedTraitTypeWeightsListFinder.removeByTraitTypeIds(weightedTraitTypeWeights, traitTypeIds);
    System.out.println("weightedTraitTypeWeights, post removes: " + weightedTraitTypeWeights);
    createNewOneValueOneHundredPercentTraits(nextWeightedTraitTypeWeightId);
    System.out.println("weightedTraitTypeWeights, post adds: " + weightedTraitTypeWeights);
    return weightedTraitTypeWeights;
  }

  private void createNewOneValueOneHundredPercentTraits(int nextWeightedTraitTypeWeightId) {
    weightedTraitTypeWeights.add(
        createNewOneValueOneHundredPercent(
            (long) nextWeightedTraitTypeWeightId++,
            (long) WeightedTraitTypeConstants.TILE_1_MULTIPLIER));
    weightedTraitTypeWeights.add(
        createNewOneValueOneHundredPercent(
            (long) nextWeightedTraitTypeWeightId++,
            (long) WeightedTraitTypeConstants.TILE_2_MULTIPLIER));
    weightedTraitTypeWeights.add(
        createNewOneValueOneHundredPercent(
            (long) nextWeightedTraitTypeWeightId++,
            (long) WeightedTraitTypeConstants.TILE_3_MULTIPLIER));
    weightedTraitTypeWeights.add(
        createNewOneValueOneHundredPercent(
            (long) nextWeightedTraitTypeWeightId,
            (long) WeightedTraitTypeConstants.TILE_4_MULTIPLIER));
  }

  private WeightedTraitTypeWeightDTO createNewOneValueOneHundredPercent(
      Long traitTypeWeightId, Long traitTypeId) {
    return WeightedTraitTypeWeightDTO.builder()
        .id(traitTypeWeightId)
        .traitTypeWeightId(traitTypeWeightId)
        .traitTypeId(traitTypeId)
        .likelihood(100L)
        .value("1")
        .displayTypeValue("")
        .build();
  }

  private WeightedTraitTypeWeightDTO createNewWeightedTraitTypeWeight(
      Long id,
      Long traitTypeWeightId,
      Long traitTypeId,
      Long likelihood,
      String value,
      String displayTypeValue) {
    return WeightedTraitTypeWeightDTO.builder()
        .id(id)
        .traitTypeWeightId(traitTypeWeightId)
        .traitTypeId(traitTypeId)
        .likelihood(likelihood)
        .value(value)
        .displayTypeValue(displayTypeValue)
        .build();
  }
}
