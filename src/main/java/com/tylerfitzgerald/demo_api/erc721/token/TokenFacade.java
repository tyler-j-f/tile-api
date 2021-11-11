package com.tylerfitzgerald.demo_api.erc721.token;

import com.tylerfitzgerald.demo_api.erc721.traits.DisplayTypeTrait;
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

  private ArrayList<Object> buildAttributes() {
    List<TraitDTO> tokenTraitDTOs = getTokenTraits();
    TraitTypeWeightDTO weight;
    TraitTypeDTO type;
    String traitType, traitValue, displayType;
    List<TraitTypeWeightDTO> traitWeightDTOs = getAvailableTraitTypeWeights();
    List<TraitTypeDTO> traitTypeDTOs = getAvailableTraitTypes();
    ArrayList<Object> traits = new ArrayList<>();
    for (TraitDTO traitDTO : tokenTraitDTOs) {
      weight = getTraitWeightForTraitDTO(traitWeightDTOs, traitDTO);
      type = getTraitTypeForTraitDTO(traitTypeDTOs, traitDTO);
      traitType = type.getTraitTypeName();
      traitValue = weight.getValue();
      displayType = weight.getDisplayTypeValue();
      if (displayType.equals("")) {
        traits.add(Trait.builder().trait_type(traitType).value(traitValue).build());
      } else {
        traits.add(
            DisplayTypeTrait.builder()
                .display_type(displayType)
                .trait_type(traitType)
                .value(traitValue)
                .build());
      }
    }
    return traits;
  }

  private TraitTypeWeightDTO getTraitWeightForTraitDTO(
      List<TraitTypeWeightDTO> traitWeightsDTOs, TraitDTO traitDTO) {
    return traitWeightsDTOs.stream()
        .filter(
            traitWeightsDTO ->
                traitWeightsDTO.getTraitTypeWeightId().equals(traitDTO.getTraitTypeWeightId()))
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
