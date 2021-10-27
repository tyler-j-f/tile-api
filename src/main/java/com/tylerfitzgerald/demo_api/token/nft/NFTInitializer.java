package com.tylerfitzgerald.demo_api.token.nft;

import com.tylerfitzgerald.demo_api.config.TraitsConfig;
import com.tylerfitzgerald.demo_api.token.TokenDTO;
import com.tylerfitzgerald.demo_api.token.TokenRepository;
import com.tylerfitzgerald.demo_api.token.traitTypeWeights.TraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.token.traitTypeWeights.TraitTypeWeightRepository;
import com.tylerfitzgerald.demo_api.token.traitTypes.TraitTypeDTO;
import com.tylerfitzgerald.demo_api.token.traitTypes.TraitTypeRepository;
import com.tylerfitzgerald.demo_api.token.traits.TraitDTO;
import com.tylerfitzgerald.demo_api.token.traits.TraitRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;

public class NFTInitializer {

  @Autowired private TokenRepository tokenRepository;
  @Autowired private TraitRepository traitRepository;
  @Autowired private TraitTypeRepository traitTypeRepository;
  @Autowired private TraitTypeWeightRepository traitTypeWeightRepository;
  @Autowired private TraitsConfig traitsConfig;

  private TokenDTO tokenDTO = null;
  private List<TraitDTO> traits = new ArrayList<>();
  private List<TraitTypeDTO> traitTypes = new ArrayList<>();
  private List<TraitTypeWeightDTO> traitTypeWeights = new ArrayList<>();

  public NFTFacadeDTO initialize(Long tokenId) {
    traitTypeWeights = traitTypeWeightRepository.read();
    traitTypes = traitTypeRepository.read();
    for (TraitTypeDTO type : traitsConfig.getTypes()) {
      traits.add(createTrait(type));
    }
    return NFTFacadeDTO.builder()
        .tokenDTO(tokenDTO)
        .traits(traits)
        .traitTypes(traitTypes)
        .traitTypeWeights(traitTypeWeights)
        .build();
  }

  private TraitDTO createTrait(TraitTypeDTO type) {
    Long traitTypeId = type.getTraitTypeId();
    List<TraitTypeWeightDTO> weights = getTraitTypeWeightsForTraitTypeId(traitTypeId);
    TraitTypeWeightDTO traitTypeWeight = getRandomTraitTypeWeightFromList(weights);
    System.out.println(
        "DEBUG traitType: "
            + traitTypeId
            + "\nRandom traitTypeWeight: "
            + traitTypeWeight.toString());
    return TraitDTO.builder().traitId(1L).traitTypeId(traitTypeId).build();
  }

  private List<TraitTypeWeightDTO> getTraitTypeWeightsForTraitTypeId(Long traitTypeId) {
    return traitTypeWeights.stream()
        .filter(typeWeight -> typeWeight.getTraitTypeId().equals(traitTypeId))
        .collect(Collectors.toList());
  }

    private TraitTypeWeightDTO getRandomTraitTypeWeightFromList(
      List<TraitTypeWeightDTO> traitTypeWeights) {
    Long randomNumber = Long.valueOf(ThreadLocalRandom.current().nextInt(1, 100));
    Long count = 0L;
    for (TraitTypeWeightDTO traitTypeWeight : traitTypeWeights) {
      Long likelihood = traitTypeWeight.getLikelihood();
      count = count + likelihood;
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
