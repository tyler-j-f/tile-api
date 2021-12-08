package com.tylerfitzgerald.demo_api.erc721.token.initializers;

import com.tylerfitzgerald.demo_api.config.external.TokenConfig;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.WeightlessTraitException;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.traitPickers.OverallRarityTraitPicker;
import com.tylerfitzgerald.demo_api.etc.listFinders.WeightedTraitTypeWeightsFinder;
import com.tylerfitzgerald.demo_api.etc.listFinders.WeightedTraitTypesFinder;
import com.tylerfitzgerald.demo_api.etc.listFinders.WeightlessTraitTypesFinder;
import com.tylerfitzgerald.demo_api.sql.dtos.TokenDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightlessTraitDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightlessTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.repositories.TokenRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightedTraitRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightedTraitTypeRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightedTraitTypeWeightRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightlessTraitRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightlessTraitTypeRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractTokenInitializer implements TokenInitializerInterface {
  @Autowired protected TokenRepository tokenRepository;
  @Autowired protected TokenConfig tokenConfig;
  @Autowired protected WeightedTraitRepository weightedTraitRepository;
  @Autowired protected WeightedTraitTypesFinder weightedTraitTypesFinder;
  @Autowired protected WeightlessTraitTypesFinder weightlessTraitTypesFinder;
  @Autowired protected WeightedTraitTypeRepository weightedTraitTypeRepository;
  @Autowired protected WeightedTraitTypeWeightRepository weightedTraitTypeWeightRepository;
  @Autowired protected WeightlessTraitRepository weightlessTraitRepository;
  @Autowired protected WeightlessTraitTypeRepository weightlessTraitTypeRepository;
  @Autowired protected OverallRarityTraitPicker overallRarityTraitPicker;
  @Autowired protected WeightedTraitTypeWeightsFinder weightedTraitTypeWeightsFinder;
  protected TokenDTO tokenDTO;
  protected Long seedForTraits;
  protected List<WeightedTraitTypeWeightDTO> weightedTraitTypeWeights = new ArrayList<>();
  protected List<WeightedTraitDTO> weightedTraits = new ArrayList<>();
  protected List<WeightedTraitTypeDTO> weightedTraitTypes = new ArrayList<>();
  protected List<WeightlessTraitDTO> weightlessTraits = new ArrayList<>();
  protected List<WeightlessTraitTypeDTO> weightlessTraitTypes = new ArrayList<>();

  public TokenFacadeDTO buildTokenFacadeDTO() {
    return TokenFacadeDTO.builder()
        .tokenDTO(tokenDTO)
        .weightedTraits(weightedTraits)
        .weightedTraitTypes(weightedTraitTypes)
        .weightedTraitTypeWeights(weightedTraitTypeWeights)
        .weightlessTraits(weightlessTraits)
        .weightlessTraitTypes(weightlessTraitTypes)
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

  protected String getWeightlessTraitDisplayTypeValue() {
    return "";
  }

  protected List<WeightedTraitDTO> createWeightedTraits() {
    List<WeightedTraitDTO> traits = new ArrayList<>();
    for (WeightedTraitTypeDTO type : weightedTraitTypes) {
      // Increment the seed so that we use a unique random value for each trait
      WeightedTraitDTO trait = createWeightedTrait(type);
      if (trait != null) {
        traits.add(trait);
      }
    }
    return traits;
  }

  protected WeightedTraitDTO createWeightedTrait(WeightedTraitTypeDTO type) {
    seedForTraits++;
    Long traitTypeId = type.getTraitTypeId();
    List<WeightedTraitTypeWeightDTO> weights = getTraitTypeWeightsForTraitTypeId(traitTypeId);
    WeightedTraitTypeWeightDTO traitTypeWeight =
        getRandomTraitTypeWeightFromList(weights, seedForTraits);
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

  protected List<WeightlessTraitDTO> createWeightlessTraits() throws TokenInitializeException {
    WeightlessTraitDTO weightlessTraitDTO;
    for (WeightlessTraitTypeDTO weightlessTraitType : weightlessTraitTypes) {
      // Increment the seed so that we use a unique random value for each trait
      try {
        weightlessTraitDTO = createWeightlessTrait(weightlessTraitType);
      } catch (WeightlessTraitException e) {
        throw new TokenInitializeException(e.getMessage(), e.getCause());
      }
      if (weightlessTraitDTO != null) {
        weightlessTraits.add(weightlessTraitDTO);
      }
    }
    return weightlessTraits;
  }

  protected List<WeightedTraitTypeDTO> filterOutWeightedTraitTypesToIgnore(
      List<WeightedTraitTypeDTO> traitTypes, int[] traitTypesToIgnore) {
    return weightedTraitTypesFinder.findByIgnoringTraitTypeIdList(traitTypes, traitTypesToIgnore);
  }

  protected List<WeightlessTraitTypeDTO> filterOutWeightlessTraitTypesToIgnore(
      List<WeightlessTraitTypeDTO> weightlessTraitTypes, int[] traitTypesToIgnore) {
    return weightlessTraitTypesFinder.findByIgnoringTraitTypeIdList(
        weightlessTraitTypes, traitTypesToIgnore);
  }

  protected WeightlessTraitDTO createWeightlessTrait(WeightlessTraitTypeDTO weightlessTraitType)
      throws TokenInitializeException, WeightlessTraitException {
    seedForTraits++;
    Long weightTraitId = weightlessTraitRepository.getCount() + 1L;
    String traitValue = getWeightlessTraitValue(weightlessTraitType);
    if (traitValue == null || traitValue.equals("")) {
      return null;
    }
    return weightlessTraitRepository.create(
        WeightlessTraitDTO.builder()
            .id(null)
            .traitId(weightTraitId)
            .tokenId(tokenDTO.getTokenId())
            .traitTypeId(weightlessTraitType.getWeightlessTraitTypeId())
            .value(traitValue)
            .displayTypeValue(getWeightlessTraitDisplayTypeValue())
            .build());
  }

  protected abstract String getWeightlessTraitValue(WeightlessTraitTypeDTO weightlessTraitType)
      throws WeightlessTraitException, TokenInitializeException;
}
