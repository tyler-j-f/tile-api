package com.tylerfitzgerald.demo_api.erc721.token;

import com.tylerfitzgerald.demo_api.erc721.traits.Trait;
import com.tylerfitzgerald.demo_api.sql.tblToken.TokenDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.TraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypes.TraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraits.TraitDTO;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenFacade {

  private TokenFacadeDTO nftFacadeDTO;

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

  public TokenDataDTO buildTokenDataDTO() {
    TokenDTO tokenDTO = getToken();
    if (tokenDTO == null) {
      return null;
    }
    return TokenDataDTO.builder()
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