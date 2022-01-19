package io.tilenft.eth.token.initializers;

import io.tilenft.config.external.TokenConfig;
import io.tilenft.etc.lists.finders.WeightedTraitTypesListFinder;
import io.tilenft.eth.token.TokenFacadeDTO;
import io.tilenft.eth.token.traits.creators.weighted.WeightedTraitsCreator;
import io.tilenft.eth.token.traits.creators.weightless.AbstractWeightlessTraitsCreator;
import io.tilenft.sql.dtos.TokenDTO;
import io.tilenft.sql.dtos.WeightedTraitDTO;
import io.tilenft.sql.dtos.WeightedTraitTypeDTO;
import io.tilenft.sql.dtos.WeightedTraitTypeWeightDTO;
import io.tilenft.sql.dtos.WeightlessTraitTypeDTO;
import io.tilenft.sql.repositories.TokenRepository;
import io.tilenft.sql.repositories.WeightedTraitTypeRepository;
import io.tilenft.sql.repositories.WeightedTraitTypeWeightRepository;
import io.tilenft.sql.repositories.WeightlessTraitTypeRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractTokenInitializer implements TokenInitializerInterface {
  @Autowired private TokenRepository tokenRepository;
  @Autowired private TokenConfig tokenConfig;
  @Autowired private WeightedTraitTypesListFinder weightedTraitTypesListFinder;
  @Autowired protected WeightedTraitTypeRepository weightedTraitTypeRepository;
  @Autowired protected WeightedTraitTypeWeightRepository weightedTraitTypeWeightRepository;
  @Autowired protected WeightlessTraitTypeRepository weightlessTraitTypeRepository;
  protected AbstractWeightlessTraitsCreator weightlessTraitsCreator;
  protected WeightedTraitsCreator weightedTraitsCreator;
  protected TokenDTO tokenDTO;
  protected List<WeightedTraitTypeWeightDTO> weightedTraitTypeWeights = new ArrayList<>();
  protected List<WeightedTraitDTO> weightedTraits = new ArrayList<>();
  protected List<WeightlessTraitTypeDTO> weightlessTraitTypes = new ArrayList<>();
  protected List<WeightedTraitTypeDTO> weightedTraitTypes = new ArrayList<>();

  public AbstractTokenInitializer(
      AbstractWeightlessTraitsCreator weightlessTraitsCreator,
      WeightedTraitsCreator weightedTraitsCreator) {
    this.weightlessTraitsCreator = weightlessTraitsCreator;
    this.weightedTraitsCreator = weightedTraitsCreator;
  }

  public TokenFacadeDTO buildTokenFacadeDTO() {
    return TokenFacadeDTO.builder()
        .tokenDTO(tokenDTO)
        .weightedTraits(weightedTraits)
        .weightedTraitTypes(weightedTraitTypes)
        .weightedTraitTypeWeights(weightedTraitTypeWeights)
        .weightlessTraits(weightlessTraitsCreator.getCreatedWeightlessTraits())
        .weightlessTraitTypes(weightlessTraitsCreator.getWeightlessTraitTypes())
        .build();
  }

  public TokenDTO createToken(Long tokenId) {
    return createToken(tokenId, false);
  }

  public TokenDTO createToken(Long tokenId, boolean isDryRun) {
    TokenDTO.TokenDTOBuilder tokenDTOBuilder =
        TokenDTO.builder()
            .tokenId(tokenId)
            .saleId(1L)
            .name(tokenConfig.getBase_name() + " " + tokenId.toString())
            .description(tokenConfig.getDescription())
            .externalUrl(tokenConfig.getBase_external_url() + tokenId)
            .imageUrl(tokenConfig.getBase_image_url() + tokenId);
    TokenDTO tokenDTO;
    if (isDryRun) {
      tokenDTO = tokenDTOBuilder.id(tokenId).build();
    } else {
      tokenDTO = tokenDTOBuilder.build();
    }
    return isDryRun ? tokenDTO : tokenRepository.create(tokenDTO);
  }

  protected List<WeightedTraitTypeDTO> filterOutWeightedTraitTypesToIgnore(
      List<WeightedTraitTypeDTO> traitTypes, int[] traitTypesToIgnore) {
    return weightedTraitTypesListFinder.findByIgnoringTraitTypeIdList(
        traitTypes, traitTypesToIgnore);
  }
}
