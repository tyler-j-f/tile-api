package io.tilenft.erc721.token;

import io.tilenft.sql.dtos.TokenDTO;
import io.tilenft.sql.dtos.WeightedTraitDTO;
import io.tilenft.sql.dtos.WeightedTraitTypeDTO;
import io.tilenft.sql.dtos.WeightedTraitTypeWeightDTO;
import io.tilenft.sql.dtos.WeightlessTraitDTO;
import io.tilenft.sql.dtos.WeightlessTraitTypeDTO;
import io.tilenft.sql.repositories.TokenRepository;
import io.tilenft.sql.repositories.WeightedTraitRepository;
import io.tilenft.sql.repositories.WeightedTraitTypeRepository;
import io.tilenft.sql.repositories.WeightedTraitTypeWeightRepository;
import io.tilenft.sql.repositories.WeightlessTraitRepository;
import io.tilenft.sql.repositories.WeightlessTraitTypeRepository;
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
