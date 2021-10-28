package com.tylerfitzgerald.demo_api.sql.nft;

import com.tylerfitzgerald.demo_api.config.TraitsConfig;
import com.tylerfitzgerald.demo_api.sql.TokenDTO;
import com.tylerfitzgerald.demo_api.sql.TokenRepository;
import com.tylerfitzgerald.demo_api.sql.nft.NFTFacadeDTO;
import com.tylerfitzgerald.demo_api.sql.TraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.TraitTypeWeightRepository;
import com.tylerfitzgerald.demo_api.sql.TraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.TraitTypeRepository;
import com.tylerfitzgerald.demo_api.sql.traits.TraitDTO;
import com.tylerfitzgerald.demo_api.sql.traits.TraitRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

public class NFTInitializer {

  private static final String NFT_NAME = "Tile";
  private static final String NFT_DESCRIPTION =
      "Tile NFT. Buy a tile and customize it yourself!!! Each tile will be generated with unique traits that decide how rare your Tile is.";
  private static final String NFT_EXTERNAL_URL = "http://tilenft.io/api/nft";
  private static final String NFT_IMG_URL_BASE = "http://tilenft.io/api/img/";

  @Autowired private TokenRepository tokenRepository;
  @Autowired private TraitRepository traitRepository;
  @Autowired private TraitTypeRepository traitTypeRepository;
  @Autowired private TraitTypeWeightRepository traitTypeWeightRepository;
  @Autowired private TraitsConfig traitsConfig;

  private List<TraitTypeDTO> availableTraitTypes = new ArrayList<>();
  private List<TraitTypeWeightDTO> availableTraitTypeWeights = new ArrayList<>();
  private List<TraitDTO> tokenTraits = new ArrayList<>();
  private TokenDTO tokenDTO;

  public NFTFacadeDTO initialize(Long tokenId) {
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

  private NFTFacadeDTO buildNFTFacade() {
    return NFTFacadeDTO.builder()
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
            .name(NFT_NAME)
            .description(NFT_DESCRIPTION)
            .externalUrl(NFT_EXTERNAL_URL)
            .imageUrl(NFT_IMG_URL_BASE + tokenId)
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
