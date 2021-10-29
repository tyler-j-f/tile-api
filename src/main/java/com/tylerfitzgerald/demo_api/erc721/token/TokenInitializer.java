package com.tylerfitzgerald.demo_api.erc721.token;

import com.tylerfitzgerald.demo_api.config.TokenConfig;
import com.tylerfitzgerald.demo_api.config.TraitsConfig;
import com.tylerfitzgerald.demo_api.sql.tblToken.TokenDTO;
import com.tylerfitzgerald.demo_api.sql.tblToken.TokenRepository;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.TraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.TraitTypeWeightRepository;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypes.TraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypes.TraitTypeRepository;
import com.tylerfitzgerald.demo_api.sql.tblTraits.TraitDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraits.TraitRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

public class TokenInitializer {

  @Autowired private TokenRepository tokenRepository;
  @Autowired private TraitRepository traitRepository;
  @Autowired private TraitTypeRepository traitTypeRepository;
  @Autowired private TraitTypeWeightRepository traitTypeWeightRepository;
  @Autowired private TokenConfig tokenConfig;
  @Autowired private TraitsConfig traitsConfig;

  private List<TraitTypeDTO> availableTraitTypes = new ArrayList<>();
  private List<TraitTypeWeightDTO> availableTraitTypeWeights = new ArrayList<>();
  private List<TraitDTO> tokenTraits = new ArrayList<>();
  private TokenDTO tokenDTO;

  public TokenFacadeDTO initialize(Long tokenId) {
    availableTraitTypeWeights = traitTypeWeightRepository.read();
    availableTraitTypes = traitTypeRepository.read();
    tokenDTO = createToken(tokenId);
    if (tokenDTO == null) {
      System.out.println("NFTInitializer failed to initialize the token with tokenId: " + tokenId);
      return null;
    }
    tokenTraits = createTraits();
    return buildNFTFacade();
  }

  private TokenFacadeDTO buildNFTFacade() {
    return TokenFacadeDTO.builder()
        .tokenDTO(tokenDTO)
        .tokenTraits(tokenTraits)
        .availableTraitTypes(availableTraitTypes)
        .availableTraitTypeWeights(availableTraitTypeWeights)
        .build();
  }

  private TokenDTO createToken(Long tokenId) {
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

  private List<TraitDTO> createTraits() {
    List<TraitDTO> traits = new ArrayList<>();
    for (TraitTypeDTO type : traitsConfig.getTypes()) {
      TraitDTO trait = createTrait(type);
      if (trait != null) {
        traits.add(trait);
      }
    }
    return traits;
  }

  private TraitDTO createTrait(TraitTypeDTO type) {
    Long traitTypeId = type.getTraitTypeId();
    List<TraitTypeWeightDTO> weights = getTraitTypeWeightsForTraitTypeId(traitTypeId);
    TraitTypeWeightDTO traitTypeWeight = getRandomTraitTypeWeightFromList(weights);
    Long traitId = traitRepository.read().size() + 1L;
    return traitRepository.create(
        TraitDTO.builder()
            .id(null)
            .traitId(traitId)
            .tokenId(tokenDTO.getTokenId())
            .traitTypeId(traitTypeId)
            .traitTypeWeightId(traitTypeWeight.getTraitTypeWeightId())
            .build());
  }

  private List<TraitTypeWeightDTO> getTraitTypeWeightsForTraitTypeId(Long traitTypeId) {
    return availableTraitTypeWeights.stream()
        .filter(typeWeight -> typeWeight.getTraitTypeId().equals(traitTypeId))
        .collect(Collectors.toList());
  }

  private TraitTypeWeightDTO getRandomTraitTypeWeightFromList(
      List<TraitTypeWeightDTO> traitTypeWeights) {
    Long randomNumber = Long.valueOf(ThreadLocalRandom.current().nextInt(1, 100));
    Long count = 0L;
    for (TraitTypeWeightDTO traitTypeWeight : traitTypeWeights) {
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
}
