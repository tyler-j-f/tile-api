package com.tylerfitzgerald.demo_api.erc721.token;

import com.tylerfitzgerald.demo_api.erc721.token.initializers.TokenInitializeException;
import com.tylerfitzgerald.demo_api.erc721.token.initializers.TokenInitializer;
import com.tylerfitzgerald.demo_api.erc721.traits.DisplayTypeTrait;
import com.tylerfitzgerald.demo_api.erc721.traits.Trait;
import com.tylerfitzgerald.demo_api.listUtils.finders.WeightedTraitTypeWeightsFinder;
import com.tylerfitzgerald.demo_api.listUtils.finders.WeightedTraitTypesFinder;
import com.tylerfitzgerald.demo_api.listUtils.finders.WeightlessTraitTypesFinder;
import com.tylerfitzgerald.demo_api.sql.tblToken.TokenDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.WeightedTraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypes.WeightedTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraits.WeightedTraitDTO;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraitTypes.WeightlessTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits.WeightlessTraitDTO;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenFacade implements TokenFacadeInterface {

  private TokenFacadeDTO tokenFacadeDTO;
  @Autowired private WeightedTraitTypesFinder weightedTraitTypesFinder;
  @Autowired private WeightedTraitTypeWeightsFinder weightedTraitTypeWeightsFinder;
  @Autowired private WeightlessTraitTypesFinder weightlessTraitTypesFinder;
  @Autowired private TokenInitializer tokenInitializer;
  @Autowired private TokenRetriever tokenRetriever;

  public TokenFacade initializeToken(Long tokenId, Long seedForTraits)
      throws TokenInitializeException {
    TokenFacadeDTO token = tokenInitializer.initialize(tokenId, seedForTraits);
    if (token == null) {
      String out =
          "tokenInitializer failed to initialize token. tokenId: "
              + tokenId
              + ", seedForTraits: "
              + seedForTraits;
      System.out.println(out);
      throw new TokenInitializeException(out);
    }
    setTokenFacadeDTO(token);
    return this;
  }

  public TokenMetadataDTO getTokenMetadataDTO(Long tokenId) throws TokenInitializeException {
    TokenFacadeDTO token = tokenRetriever.get(tokenId);
    if (token == null) {
      String out = "tokenRetriever failed to retrieve token. tokenId: " + tokenId;
      System.out.println(out);
      throw new TokenInitializeException(out);
    }
    return setTokenFacadeDTO(token).buildTokenDataDTO();
  }

  public TokenFacade setTokenFacadeDTO(TokenFacadeDTO nftFacadeDTO) {
    this.tokenFacadeDTO = nftFacadeDTO;
    return this;
  }

  public TokenMetadataDTO buildTokenDataDTO() {
    TokenDTO tokenDTO = tokenFacadeDTO.getTokenDTO();
    if (tokenDTO == null) {
      return null;
    }
    return TokenMetadataDTO.builder()
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
    WeightlessTraitTypeDTO weightlessTraitType =
        weightlessTraitTypesFinder.findFirstByWeightlessTraitTypeId(
            getWeightlessTraitTypes(), traitTypeId);
    if (weightlessTraitType == null) {
      return "ERROR";
    }
    return weightlessTraitType.getWeightlessTraitTypeName();
  }

  private ArrayList<Object> buildWeightedAttributes() {
    List<WeightedTraitDTO> tokenWeightedTraitDTOS = getWeightedTraits();
    WeightedTraitTypeWeightDTO weight;
    WeightedTraitTypeDTO type;
    String traitType, traitValue, displayType;
    List<WeightedTraitTypeWeightDTO> traitWeightDTOs = getWeightedTraitTypeWeights();
    List<WeightedTraitTypeDTO> weightedTraitTypeDTOS = getWeightedTraitTypes();
    ArrayList<Object> traits = new ArrayList<>();
    for (WeightedTraitDTO weightedTraitDTO : tokenWeightedTraitDTOS) {
      weight = getTraitWeightForTraitDTO(traitWeightDTOs, weightedTraitDTO);
      type = getTraitTypeForTraitDTO(weightedTraitTypeDTOS, weightedTraitDTO);
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

  private WeightedTraitTypeWeightDTO getTraitWeightForTraitDTO(
      List<WeightedTraitTypeWeightDTO> weightedTraitTypeWeightDTOs,
      WeightedTraitDTO weightedTraitDTO) {
    return weightedTraitTypeWeightsFinder.findFirstByTraitTypeId(
        weightedTraitTypeWeightDTOs, weightedTraitDTO.getTraitTypeWeightId());
  }

  private WeightedTraitTypeDTO getTraitTypeForTraitDTO(
      List<WeightedTraitTypeDTO> weightedTraitTypeDTOS, WeightedTraitDTO weightedTraitDTO) {
    return weightedTraitTypesFinder.findFirstByTraitTypeId(
        weightedTraitTypeDTOS, weightedTraitDTO.getTraitTypeId());
  }

  private List<WeightlessTraitDTO> getWeightlessTraits() {
    return tokenFacadeDTO.getWeightlessTraits();
  }

  private List<WeightlessTraitTypeDTO> getWeightlessTraitTypes() {
    return tokenFacadeDTO.getWeightlessTraitTypes();
  }

  private List<WeightedTraitDTO> getWeightedTraits() {
    return tokenFacadeDTO.getWeightedTraits();
  }

  private List<WeightedTraitTypeDTO> getWeightedTraitTypes() {
    return tokenFacadeDTO.getWeightedTraitTypes();
  }

  private List<WeightedTraitTypeWeightDTO> getWeightedTraitTypeWeights() {
    return tokenFacadeDTO.getWeightedTraitTypeWeights();
  }
}
