package com.tylerfitzgerald.demo_api.erc721.token;

import com.tylerfitzgerald.demo_api.sql.tblToken.TokenDTO;
import com.tylerfitzgerald.demo_api.sql.tblToken.TokenRepository;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.WeightedTraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.WeightedTraitTypeWeightRepository;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypes.WeightedTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypes.WeightedTraitTypeRepository;
import com.tylerfitzgerald.demo_api.sql.tblTraits.WeightedTraitDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraits.WeightedTraitRepository;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraitTypes.WeightlessTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraitTypes.WeightlessTraitTypeRepository;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits.WeightlessTraitDTO;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits.WeightlessTraitRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class TokenRetriever {

  @Autowired private TokenRepository tokenRepository;
  @Autowired private WeightedTraitRepository weightedTraitRepository;
  @Autowired private WeightedTraitTypeRepository weightedTraitTypeRepository;
  @Autowired private WeightedTraitTypeWeightRepository weightedTraitTypeWeightRepository;
  @Autowired private WeightlessTraitRepository weightlessTraitRepository;
  @Autowired private WeightlessTraitTypeRepository weightlessTraitTypeRepository;

  private List<WeightedTraitTypeDTO> availableTraitTypes = new ArrayList<>();
  private List<WeightedTraitTypeWeightDTO> availableTraitTypeWeights = new ArrayList<>();
  private List<WeightedTraitDTO> weightedTraits = new ArrayList<>();
  private List<WeightlessTraitDTO> weightlessTraits = new ArrayList<>();
  private List<WeightlessTraitTypeDTO> weightlessTraitTypes = new ArrayList<>();
  private TokenDTO tokenDTO;

  public TokenFacadeDTO get(Long tokenId) {
    tokenDTO = getToken(tokenId);
    if (tokenDTO == null) {
      System.out.println("NFTRetriever failed to retrieve the token with tokenId: " + tokenId);
      return null;
    }
    availableTraitTypes = weightedTraitTypeRepository.read();
    availableTraitTypeWeights = weightedTraitTypeWeightRepository.read();
    weightlessTraitTypes = weightlessTraitTypeRepository.read();
    weightedTraits = getWeightedTraits(tokenId);
    weightlessTraits = getWeightlessTraits(tokenId);
    return buildNFTFacade();
  }

  private TokenFacadeDTO buildNFTFacade() {
    return TokenFacadeDTO.builder()
        .tokenDTO(tokenDTO)
        .weightedTraits(weightedTraits)
        .weightedTraitTypes(availableTraitTypes)
        .weightedTraitTypeWeights(availableTraitTypeWeights)
        .weightlessTraitTypes(weightlessTraitTypes)
        .weightlessTraits(weightlessTraits)
        .build();
  }

  private TokenDTO getToken(Long tokenId) {
    return tokenRepository.readById(tokenId);
  }

  private List<WeightedTraitDTO> getWeightedTraits(Long tokenId) {
    return weightedTraitRepository.readByTokenId(tokenId);
  }

  private List<WeightlessTraitDTO> getWeightlessTraits(Long tokenId) {
    return weightlessTraitRepository.readByTokenId(tokenId);
  }
}
