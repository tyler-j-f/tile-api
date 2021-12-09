package com.tylerfitzgerald.demo_api.erc721.token.initializers;

import com.tylerfitzgerald.demo_api.config.external.TokenConfig;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.weighted.WeightedTraitsCreator;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.weightless.AbstractWeightlessTraitsCreator;
import com.tylerfitzgerald.demo_api.etc.listFinders.WeightedTraitTypeWeightsFinder;
import com.tylerfitzgerald.demo_api.etc.listFinders.WeightedTraitTypesFinder;
import com.tylerfitzgerald.demo_api.etc.listFinders.WeightlessTraitTypesFinder;
import com.tylerfitzgerald.demo_api.sql.dtos.TokenDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightlessTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.repositories.TokenRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightedTraitRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightedTraitTypeRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightedTraitTypeWeightRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightlessTraitTypeRepository;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTokenInitializer implements TokenInitializerInterface {
  protected TokenRepository tokenRepository;
  protected TokenConfig tokenConfig;
  protected WeightedTraitRepository weightedTraitRepository;
  protected WeightedTraitTypesFinder weightedTraitTypesFinder;
  protected WeightedTraitTypeRepository weightedTraitTypeRepository;
  protected WeightedTraitTypeWeightRepository weightedTraitTypeWeightRepository;
  protected WeightedTraitTypeWeightsFinder weightedTraitTypeWeightsFinder;
  protected WeightlessTraitTypesFinder weightlessTraitTypesFinder;
  protected WeightlessTraitTypeRepository weightlessTraitTypeRepository;
  protected TokenDTO tokenDTO;
  protected Long seedForTraits;
  protected List<WeightedTraitTypeWeightDTO> weightedTraitTypeWeights = new ArrayList<>();
  protected List<WeightedTraitDTO> weightedTraits = new ArrayList<>();
  protected List<WeightlessTraitTypeDTO> weightlessTraitTypes = new ArrayList<>();
  protected List<WeightedTraitTypeDTO> weightedTraitTypes = new ArrayList<>();
  protected AbstractWeightlessTraitsCreator weightlessTraitsCreator;
  protected WeightedTraitsCreator weightedTraitsCreator;

  public AbstractTokenInitializer(
      TokenRepository tokenRepository,
      TokenConfig tokenConfig,
      WeightedTraitRepository weightedTraitRepository,
      WeightedTraitTypesFinder weightedTraitTypesFinder,
      WeightlessTraitTypesFinder weightlessTraitTypesFinder,
      WeightedTraitTypeRepository weightedTraitTypeRepository,
      WeightedTraitTypeWeightRepository weightedTraitTypeWeightRepository,
      WeightedTraitTypeWeightsFinder weightedTraitTypeWeightsFinder,
      WeightlessTraitTypeRepository weightlessTraitTypeRepository,
      AbstractWeightlessTraitsCreator weightlessTraitsCreator,
      WeightedTraitsCreator weightedTraitsCreator) {
    this.tokenRepository = tokenRepository;
    this.tokenConfig = tokenConfig;
    this.weightedTraitRepository = weightedTraitRepository;
    this.weightedTraitTypesFinder = weightedTraitTypesFinder;
    this.weightlessTraitTypesFinder = weightlessTraitTypesFinder;
    this.weightedTraitTypeRepository = weightedTraitTypeRepository;
    this.weightedTraitTypeWeightRepository = weightedTraitTypeWeightRepository;
    this.weightedTraitTypeWeightsFinder = weightedTraitTypeWeightsFinder;
    this.weightlessTraitTypeRepository = weightlessTraitTypeRepository;
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
    return weightedTraitTypesFinder.findByIgnoringTraitTypeIdList(traitTypes, traitTypesToIgnore);
  }
}
