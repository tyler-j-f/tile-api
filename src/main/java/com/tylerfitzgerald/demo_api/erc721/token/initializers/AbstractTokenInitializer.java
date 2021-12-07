package com.tylerfitzgerald.demo_api.erc721.token.initializers;

import com.tylerfitzgerald.demo_api.config.TokenConfig;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.traitPickers.OverallRarityTraitPicker;
import com.tylerfitzgerald.demo_api.listUtils.finders.WeightedTraitTypeWeightsFinder;
import com.tylerfitzgerald.demo_api.listUtils.finders.WeightedTraitTypesFinder;
import com.tylerfitzgerald.demo_api.sql.tblToken.TokenDTO;
import com.tylerfitzgerald.demo_api.sql.tblToken.TokenRepository;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.WeightedTraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.WeightedTraitTypeWeightRepository;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypes.WeightedTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypes.WeightedTraitTypeRepository;
import com.tylerfitzgerald.demo_api.sql.tblTraits.WeightedTraitDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraits.WeightedTraitRepository;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraitTypes.WeightlessTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraitTypes.WeightlessTraitTypeRepository;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits.WeightlessTraitDTO;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits.WeightlessTraitRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractTokenInitializer implements TokenInitializerInterface {

  @Autowired private TokenRepository tokenRepository;
  @Autowired private TokenConfig tokenConfig;
  @Autowired protected WeightedTraitRepository weightedTraitRepository;
  @Autowired protected WeightedTraitTypeRepository weightedTraitTypeRepository;
  @Autowired protected WeightedTraitTypeWeightRepository weightedTraitTypeWeightRepository;
  @Autowired protected WeightlessTraitRepository weightlessTraitRepository;
  @Autowired protected WeightlessTraitTypeRepository weightlessTraitTypeRepository;
  @Autowired protected OverallRarityTraitPicker rarityTraitPicker;
  @Autowired protected WeightedTraitTypeWeightsFinder weightedTraitTypeWeightsFinder;
  @Autowired protected WeightedTraitTypesFinder weightedTraitTypesFinder;
  protected List<WeightedTraitTypeWeightDTO> weightedTraitTypeWeights = new ArrayList<>();
  protected TokenDTO tokenDTO;
  protected List<WeightedTraitDTO> weightedTraits = new ArrayList<>();
  protected List<WeightedTraitTypeDTO> weightedTraitTypes = new ArrayList<>();
  protected List<WeightlessTraitDTO> weightlessTraits = new ArrayList<>();
  protected List<WeightlessTraitTypeDTO> weightlessTraitTypes = new ArrayList<>();

  public TokenFacadeDTO buildNFTFacade() {
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

  protected List<WeightedTraitDTO> createWeightedTraits(Long seedForTraits) {
    List<WeightedTraitDTO> traits = new ArrayList<>();
    for (WeightedTraitTypeDTO type : weightedTraitTypes) {
      // Increment the seed so that we use a unique random value for each trait
      WeightedTraitDTO trait = createWeightedTrait(type, seedForTraits++);
      if (trait != null) {
        traits.add(trait);
      }
    }
    return traits;
  }

  protected WeightedTraitDTO createWeightedTrait(WeightedTraitTypeDTO type, Long seedForTrait) {
    Long traitTypeId = type.getTraitTypeId();
    List<WeightedTraitTypeWeightDTO> weights = getTraitTypeWeightsForTraitTypeId(traitTypeId);
    WeightedTraitTypeWeightDTO traitTypeWeight =
        getRandomTraitTypeWeightFromList(weights, seedForTrait);
    Long traitId = weightedTraitRepository.read().size() + 1L;
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
}
