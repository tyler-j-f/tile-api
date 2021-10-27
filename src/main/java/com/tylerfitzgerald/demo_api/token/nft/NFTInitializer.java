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
    // System.out.println("initialize debug:\ntraits: " + traits.toString());
    return NFTFacadeDTO.builder()
        .tokenDTO(tokenDTO)
        .traits(traits)
        .traitTypes(traitTypes)
        .traitTypeWeights(traitTypeWeights)
        .build();
  }

  private TraitDTO createTrait(TraitTypeDTO type) {
    Long traitTypeId = type.getTraitTypeId();
    //    System.out.println(
    //        "DEBUG:\ntypeId: "
    //            + type.getTraitTypeId()
    //            + "\ntraitTypes: "
    //            + traitTypes.toString()
    //            + "\ntraitTypeWeights: "
    //            + traitTypeWeights.toString());
    List<TraitTypeWeightDTO> weights =
        traitTypeWeights.stream()
            .filter(typeWeight -> typeWeight.getTraitTypeId().equals(traitTypeId))
            .collect(Collectors.toList());
    System.out.println(
        "createTrait debug:\ntraitTypeId: "
            + traitTypeId.toString()
            + "\nweights: "
            + weights.toString()
            + "\n");
    Long traitId = Long.valueOf(traitRepository.read().size()) + 1l;
    //    return traitRepository.create(
    //        TraitDTO
    //            .builder()
    //            .traitId(traitId)
    //            .traitTypeId(traitTypeId)
    //            .build()
    //    );
    return TraitDTO.builder().traitId(traitId).traitTypeId(traitTypeId).build();
  }
}
