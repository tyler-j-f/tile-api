package com.tylerfitzgerald.demo_api.erc721.token;

import com.tylerfitzgerald.demo_api.sql.tblToken.TokenDTO;
import com.tylerfitzgerald.demo_api.sql.tblToken.TokenRepository;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.TraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.TraitTypeWeightRepository;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypes.TraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypes.TraitTypeRepository;
import com.tylerfitzgerald.demo_api.sql.tblTraits.TraitDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraits.TraitRepository;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraitTypes.WeightlessTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraitTypes.WeightlessTraitTypeRepository;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits.WeightlessTraitDTO;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits.WeightlessTraitRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class TokenRetriever {

  @Autowired private TokenRepository tokenRepository;
  @Autowired private TraitRepository traitRepository;
  @Autowired private TraitTypeRepository traitTypeRepository;
  @Autowired private TraitTypeWeightRepository traitTypeWeightRepository;
  @Autowired private WeightlessTraitRepository weightlessTraitRepository;
  @Autowired private WeightlessTraitTypeRepository weightlessTraitTypeRepository;

  private List<TraitTypeDTO> availableTraitTypes = new ArrayList<>();
  private List<TraitTypeWeightDTO> availableTraitTypeWeights = new ArrayList<>();
  private List<TraitDTO> weightedTraits = new ArrayList<>();
  private List<WeightlessTraitDTO> weightlessTraits = new ArrayList<>();
  private List<WeightlessTraitTypeDTO> weightlessTraitTypes = new ArrayList<>();
  private TokenDTO tokenDTO;

  public TokenFacadeDTO get(Long tokenId) {
    tokenDTO = getToken(tokenId);
    if (tokenDTO == null) {
      System.out.println("NFTRetriever failed to retrieve the token with tokenId: " + tokenId);
      return null;
    }
    availableTraitTypes = traitTypeRepository.read();
    availableTraitTypeWeights = traitTypeWeightRepository.read();
    weightlessTraitTypes = weightlessTraitTypeRepository.read();
    weightedTraits = getWeightedTraits(tokenId);
    weightlessTraits = getWeightlessTraits(tokenId);
    return buildNFTFacade();
  }

  private TokenFacadeDTO buildNFTFacade() {
    return TokenFacadeDTO.builder()
        .tokenDTO(tokenDTO)
        .tokenTraits(weightedTraits)
        .availableTraitTypes(availableTraitTypes)
        .availableTraitTypeWeights(availableTraitTypeWeights)
        .weightlessTraitTypes(weightlessTraitTypes)
        .weightlessTraits(weightlessTraits)
        .build();
  }

  private TokenDTO getToken(Long tokenId) {
    return tokenRepository.readById(tokenId);
  }

  private List<TraitDTO> getWeightedTraits(Long tokenId) {
    return traitRepository.readByTokenId(tokenId);
  }

  private List<WeightlessTraitDTO> getWeightlessTraits(Long tokenId) {
    return weightlessTraitRepository.readByTokenId(tokenId);
  }
}
