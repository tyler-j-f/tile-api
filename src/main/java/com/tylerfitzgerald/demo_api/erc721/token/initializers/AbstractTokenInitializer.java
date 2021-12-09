package com.tylerfitzgerald.demo_api.erc721.token.initializers;

import com.tylerfitzgerald.demo_api.config.external.TokenConfig;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.AbstractWeightlessTraitsCreator;
import com.tylerfitzgerald.demo_api.etc.listFinders.WeightedTraitTypeWeightsFinder;
import com.tylerfitzgerald.demo_api.etc.listFinders.WeightedTraitTypesFinder;
import com.tylerfitzgerald.demo_api.etc.listFinders.WeightlessTraitTypesFinder;
import com.tylerfitzgerald.demo_api.sql.dtos.TokenDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightlessTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.repositories.TokenRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightedTraitRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightedTraitTypeRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightedTraitTypeWeightRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightlessTraitTypeRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class AbstractTokenInitializer implements TokenInitializerInterface {
  protected TokenRepository tokenRepository;
  protected TokenConfig tokenConfig;
  protected WeightedTraitRepository weightedTraitRepository;
  protected WeightedTraitTypesFinder weightedTraitTypesFinder;
  protected WeightedTraitTypeRepository weightedTraitTypeRepository;
  protected WeightedTraitTypeWeightRepository weightedTraitTypeWeightRepository;
  protected WeightedTraitTypeWeightsFinder weightedTraitTypeWeightsFinder;
  protected WeightlessTraitTypesFinder weightlessTraitTypesFinder;
  protected WeightlessTraitTypeRepository weightlessTraitTypeRepository;
  protected TokenDTO tokenDTO;
  protected Long seedForTraits;
  protected List<WeightedTraitTypeWeightDTO> weightedTraitTypeWeights = new ArrayList<>();
  protected List<WeightedTraitDTO> weightedTraits = new ArrayList<>();
  protected List<WeightlessTraitTypeDTO> weightlessTraitTypes = new ArrayList<>();
  protected List<WeightedTraitTypeDTO> weightedTraitTypes = new ArrayList<>();
  protected AbstractWeightlessTraitsCreator weightlessTraitsCreator;

  public AbstractTokenInitializer(
      TokenRepository tokenRepository,
      TokenConfig tokenConfig,
      WeightedTraitRepository weightedTraitRepository,
      WeightedTraitTypesFinder weightedTraitTypesFinder,
      WeightlessTraitTypesFinder weightlessTraitTypesFinder,
      WeightedTraitTypeRepository weightedTraitTypeRepository,
      WeightedTraitTypeWeightRepository weightedTraitTypeWeightRepository,
      WeightedTraitTypeWeightsFinder weightedTraitTypeWeightsFinder,
      WeightlessTraitTypeRepository weightlessTraitTypeRepository,
      AbstractWeightlessTraitsCreator weightlessTraitsCreator) {
    this.tokenRepository = tokenRepository;
    this.tokenConfig = tokenConfig;
    this.weightedTraitRepository = weightedTraitRepository;
    this.weightedTraitTypesFinder = weightedTraitTypesFinder;
    this.weightlessTraitTypesFinder = weightlessTraitTypesFinder;
    this.weightedTraitTypeRepository = weightedTraitTypeRepository;
    this.weightedTraitTypeWeightRepository = weightedTraitTypeWeightRepository;
    this.weightedTraitTypeWeightsFinder = weightedTraitTypeWeightsFinder;
    this.weightlessTraitTypeRepository = weightlessTraitTypeRepository;
    this.weightlessTraitsCreator = weightlessTraitsCreator;
  }

  public TokenFacadeDTO buildTokenFacadeDTO() {
    return TokenFacadeDTO.builder()
        .tokenDTO(tokenDTO)
        .weightedTraits(weightedTraits)
        .weightedTraitTypes(weightedTraitTypes)
        .weightedTraitTypeWeights(weightedTraitTypeWeights)
        .weightlessTraits(weightlessTraitsCreator.getCreatedWeightlessTraits())
        .weightlessTraitTypes(weightlessTraitsCreator.getWeightlessTraitTypes())
        .build();
  }

  public TokenDTO createToken(Long tokenId) {
    return tokenRepository.create(
        TokenDTO.builder()
            .tokenId(tokenId)
            .saleId(1L)
            .name(tokenConfig.getBase_name() + " " + tokenId.toString())
            .description(tokenConfig.getDescription())
            .externalUrl(tokenConfig.getBase_external_url() + tokenId)
            .imageUrl(tokenConfig.getBase_image_url() + tokenId)
            .build());
  }

  protected WeightedTraitTypeWeightDTO getRandomTraitTypeWeightFromList(
      List<WeightedTraitTypeWeightDTO> traitTypeWeights, Long randomNumberSeed) {
    int randomNumber = new Random(randomNumberSeed).nextInt(1, 100);
    Long count = 0L;
    for (WeightedTraitTypeWeightDTO traitTypeWeight : traitTypeWeights) {
      count = count + traitTypeWeight.getLikelihood();
      if (count >= randomNumber) {
        return traitTypeWeight;
      }
    }
    /**
     * We should never return null. If we get here: DB values for trait type weights (for a
     * particular trait type) are misconfigured.
     */
    return null;
  }

  protected List<WeightedTraitDTO> createWeightedTraits() {
    List<WeightedTraitDTO> traits = new ArrayList<>();
    for (WeightedTraitTypeDTO type : weightedTraitTypes) {
      WeightedTraitDTO trait = createWeightedTrait(type);
      if (trait != null) {
        traits.add(trait);
      }
    }
    return traits;
  }

  protected WeightedTraitDTO createWeightedTrait(WeightedTraitTypeDTO type) {
    Long traitTypeId = type.getTraitTypeId();
    List<WeightedTraitTypeWeightDTO> weights = getTraitTypeWeightsForTraitTypeId(traitTypeId);
    WeightedTraitTypeWeightDTO traitTypeWeight =
        getRandomTraitTypeWeightFromList(weights, getSeedForTrait(seedForTraits, type));
    Long traitId = weightedTraitRepository.getCount() + 1L;
    return weightedTraitRepository.create(
        WeightedTraitDTO.builder()
            .id(null)
            .traitId(traitId)
            .tokenId(tokenDTO.getTokenId())
            .traitTypeId(traitTypeId)
            .traitTypeWeightId(traitTypeWeight.getTraitTypeWeightId())
            .build());
  }

  protected List<WeightedTraitTypeWeightDTO> getTraitTypeWeightsForTraitTypeId(Long traitTypeId) {
    return weightedTraitTypeWeightsFinder.findByTraitTypeId(weightedTraitTypeWeights, traitTypeId);
  }

  protected List<WeightedTraitTypeDTO> filterOutWeightedTraitTypesToIgnore(
      List<WeightedTraitTypeDTO> traitTypes, int[] traitTypesToIgnore) {
    return weightedTraitTypesFinder.findByIgnoringTraitTypeIdList(traitTypes, traitTypesToIgnore);
  }

  protected Long getSeedForTrait(Long seedForTraits, WeightedTraitTypeDTO weightedTraitTypeDTO) {
    return (long)
        SeedForTrait.builder()
            .seedForTraits(seedForTraits)
            .weightedTraitTypeDTO(weightedTraitTypeDTO)
            .build()
            .hashCode();
  }
}
