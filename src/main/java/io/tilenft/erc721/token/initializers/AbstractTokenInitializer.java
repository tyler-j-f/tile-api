package io.tilenft.erc721.token.initializers;

import io.tilenft.config.external.TokenConfig;
import io.tilenft.erc721.token.TokenFacadeDTO;
import io.tilenft.erc721.token.traits.creators.weighted.WeightedTraitsCreator;
import io.tilenft.erc721.token.traits.creators.weightless.AbstractWeightlessTraitsCreator;
import io.tilenft.etc.lsitFinders.WeightedTraitTypesListFinder;
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
    return tokenRepository.create(
        TokenDTO.builder()
            .tokenId(tokenId)
            .saleId(1L)
            .name(tokenConfig.getBase_name() + " " + tokenId.toString())
            .description(tokenConfig.getDescription())
            .externalUrl(tokenConfig.getBase_external_url() + tokenId)
            .imageUrl(tokenConfig.getBase_image_url() + tokenId)
            .build());
  }

  protected List<WeightedTraitTypeDTO> filterOutWeightedTraitTypesToIgnore(
      List<WeightedTraitTypeDTO> traitTypes, int[] traitTypesToIgnore) {
    return weightedTraitTypesListFinder.findByIgnoringTraitTypeIdList(
        traitTypes, traitTypesToIgnore);
  }
}
