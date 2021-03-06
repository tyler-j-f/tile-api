package io.tilenft.eth.token;

import io.tilenft.etc.lists.finders.WeightedTraitTypeWeightsListFinder;
import io.tilenft.etc.lists.finders.WeightedTraitTypesListFinder;
import io.tilenft.etc.lists.finders.WeightlessTraitTypesListFinder;
import io.tilenft.eth.metadata.TokenMetadataDTO;
import io.tilenft.eth.token.initializers.TokenInitializeException;
import io.tilenft.eth.token.initializers.TokenInitializer;
import io.tilenft.eth.token.traits.DisplayTypeTrait;
import io.tilenft.eth.token.traits.Trait;
import io.tilenft.sql.dtos.TokenDTO;
import io.tilenft.sql.dtos.WeightedTraitDTO;
import io.tilenft.sql.dtos.WeightedTraitTypeDTO;
import io.tilenft.sql.dtos.WeightedTraitTypeWeightDTO;
import io.tilenft.sql.dtos.WeightlessTraitDTO;
import io.tilenft.sql.dtos.WeightlessTraitTypeDTO;
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
  @Autowired private WeightedTraitTypesListFinder weightedTraitTypesListFinder;
  @Autowired private WeightedTraitTypeWeightsListFinder weightedTraitTypeWeightsListFinder;
  @Autowired private WeightlessTraitTypesListFinder weightlessTraitTypesListFinder;
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

  public TokenFacade loadToken(Long tokenId) throws TokenInitializeException {
    return loadToken(tokenRetriever.get(tokenId));
  }

  public TokenFacade loadToken(TokenFacadeDTO token) throws TokenInitializeException {
    if (token == null) {
      String out =
          "tokenRetriever failed to retrieve token. tokenId: " + token.getTokenDTO().getTokenId();
      System.out.println(out);
      throw new TokenInitializeException(out);
    }
    return setTokenFacadeDTO(token);
  }

  public TokenFacade setTokenFacadeDTO(TokenFacadeDTO nftFacadeDTO) {
    this.tokenFacadeDTO = nftFacadeDTO;
    return this;
  }

  public TokenMetadataDTO buildTokenMetadataDTO() {
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
        .discord_url(tokenDTO.getName())
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
        weightlessTraitTypesListFinder.findFirstByWeightlessTraitTypeId(
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
    return weightedTraitTypeWeightsListFinder.findFirstByTraitTypeWeightId(
        weightedTraitTypeWeightDTOs, weightedTraitDTO.getTraitTypeWeightId());
  }

  private WeightedTraitTypeDTO getTraitTypeForTraitDTO(
      List<WeightedTraitTypeDTO> weightedTraitTypeDTOS, WeightedTraitDTO weightedTraitDTO) {
    return weightedTraitTypesListFinder.findFirstByTraitTypeId(
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
