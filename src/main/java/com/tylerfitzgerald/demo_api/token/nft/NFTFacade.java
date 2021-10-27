package com.tylerfitzgerald.demo_api.token.nft;

import com.tylerfitzgerald.demo_api.nft.NFTData;
import com.tylerfitzgerald.demo_api.nft.traits.Trait;
import com.tylerfitzgerald.demo_api.token.TokenDTO;
import com.tylerfitzgerald.demo_api.token.traitTypeWeights.TraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.token.traitTypes.TraitTypeDTO;
import com.tylerfitzgerald.demo_api.token.traits.TraitDTO;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NFTFacade {

  private NFTFacadeDTO nftFacadeDTO;

  public TokenDTO getToken() {
    return nftFacadeDTO.getTokenDTO();
  }

  public List<TraitDTO> getTokenTraits() {
    return nftFacadeDTO.getTokenTraits();
  }

  public List<TraitTypeDTO> getAvailableTraitTypes() {
    return nftFacadeDTO.getAvailableTraitTypes();
  }

  public List<TraitTypeWeightDTO> getAvailableTraitTypeWeights() {
    return nftFacadeDTO.getAvailableTraitTypeWeights();
  }

  public NFTData buildNFTData() {
    TokenDTO tokenDTO = getToken();
    if (tokenDTO == null) {
      return null;
    }
    return NFTData.builder()
        .attributes(buildAttributes())
        .description(tokenDTO.getDescription())
        .external_url(tokenDTO.getExternalUrl())
        .image(tokenDTO.getImageUrl())
        .name(tokenDTO.getName())
        .build();
  }

  private ArrayList<Trait> buildAttributes() {
    ArrayList<Trait> traits = new ArrayList<>();
    List<TraitDTO> tokenTraitDTOs = getTokenTraits();
    TraitTypeWeightDTO weight;
    TraitTypeDTO type;
    String traitType, traitValue, displayType;
    List<TraitTypeWeightDTO> traitWeightDTOs = getAvailableTraitTypeWeights();
    List<TraitTypeDTO> traitTypeDTOs = getAvailableTraitTypes();
    for (TraitDTO traitDTO : tokenTraitDTOs) {
      Long traitId = traitDTO.getTraitTypeId();
      weight = getTraitWeightForTraitDTO(traitWeightDTOs, traitDTO);
      type = getTraitTypeForTraitDTO(traitTypeDTOs, traitDTO);
      traitType = type.getTraitTypeName();
      traitValue = weight.getValue();
      displayType = weight.getDisplayTypeValue();
      if (displayType == null) {
        traits.add(Trait.builder().trait_type(traitType).value(traitValue).build());
        return traits;
      }
      traits.add(Trait.builder().trait_type(traitType).value(traitValue).build());
    }
    return traits;
  }

  private TraitTypeWeightDTO getTraitWeightForTraitDTO(
      List<TraitTypeWeightDTO> traitWeightsDTOs, TraitDTO traitDTO) {
    return traitWeightsDTOs.stream()
        .filter(
            traitWeightsDTO -> traitWeightsDTO.getTraitTypeId().equals(traitDTO.getTraitTypeId()))
        .findFirst()
        .get();
  }

  private TraitTypeDTO getTraitTypeForTraitDTO(
      List<TraitTypeDTO> traitTypeDTOs, TraitDTO traitDTO) {
    return traitTypeDTOs.stream()
        .filter(traitTypeDTO -> traitTypeDTO.getTraitTypeId().equals(traitDTO.getTraitTypeId()))
        .findFirst()
        .get();
  }
}
