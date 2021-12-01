package com.tylerfitzgerald.demo_api.erc721.token;

import com.tylerfitzgerald.demo_api.erc721.traits.DisplayTypeTrait;
import com.tylerfitzgerald.demo_api.erc721.traits.Trait;
import com.tylerfitzgerald.demo_api.sql.tblToken.TokenDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.TraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypes.TraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraits.TraitDTO;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraitTypes.WeightlessTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits.WeightlessTraitDTO;
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

  public List<WeightlessTraitDTO> getWeightlessTraits() {
    return nftFacadeDTO.getWeightlessTraits();
  }

  public List<WeightlessTraitTypeDTO> getWeightlessTraitTypes() {
    return nftFacadeDTO.getWeightlessTraitTypes();
  }

  public List<TraitDTO> getWeightedTraits() {
    return nftFacadeDTO.getWeightedTraits();
  }

  public List<TraitTypeDTO> getWeightedTraitTypes() {
    return nftFacadeDTO.getWeightedTraitTypes();
  }

  public List<TraitTypeWeightDTO> getWeightedTraitTypeWeights() {
    return nftFacadeDTO.getWeightedTraitTypeWeights();
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
    ArrayList<Object> attributes = buildWeightedAttributes();
    attributes.addAll(buildWeightlessAttributes());
    return attributes;
  }

  private ArrayList<Object> buildWeightlessAttributes() {
    ArrayList<Object> traits = new ArrayList<>();
    String traitType, traitValue, displayType;
    for (WeightlessTraitDTO weightlessTraitDTO : getWeightlessTraits()) {
      traitType = getTraitType(weightlessTraitDTO.getTraitTypeId());
      traitValue = weightlessTraitDTO.getValue();
      displayType = weightlessTraitDTO.getDisplayTypeValue();
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

  private String getTraitType(Long traitTypeId) {
    List<WeightlessTraitTypeDTO> weightlessTraitTypes = getWeightlessTraitTypes();
    WeightlessTraitTypeDTO weightlessTraitType =
        weightlessTraitTypes.stream()
            .filter(
                weightlessTraitTypeDTO ->
                    weightlessTraitTypeDTO.getWeightlessTraitTypeId().equals(traitTypeId))
            .findFirst()
            .get();
    if (weightlessTraitType == null) {
      return "ERROR";
    }
    return weightlessTraitType.getWeightlessTraitTypeName();
  }

  private ArrayList<Object> buildWeightedAttributes() {
    List<TraitDTO> tokenTraitDTOs = getWeightedTraits();
    TraitTypeWeightDTO weight;
    TraitTypeDTO type;
    String traitType, traitValue, displayType;
    List<TraitTypeWeightDTO> traitWeightDTOs = getWeightedTraitTypeWeights();
    List<TraitTypeDTO> traitTypeDTOs = getWeightedTraitTypes();
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
