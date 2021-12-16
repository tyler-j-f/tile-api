package io.tileNft.erc721.token;

import io.tileNft.sql.dtos.TokenDTO;
import io.tileNft.sql.dtos.WeightedTraitDTO;
import io.tileNft.sql.dtos.WeightedTraitTypeDTO;
import io.tileNft.sql.dtos.WeightedTraitTypeWeightDTO;
import io.tileNft.sql.dtos.WeightlessTraitDTO;
import io.tileNft.sql.dtos.WeightlessTraitTypeDTO;
import io.tileNft.sql.repositories.TokenRepository;
import io.tileNft.sql.repositories.WeightedTraitRepository;
import io.tileNft.sql.repositories.WeightedTraitTypeRepository;
import io.tileNft.sql.repositories.WeightedTraitTypeWeightRepository;
import io.tileNft.sql.repositories.WeightlessTraitRepository;
import io.tileNft.sql.repositories.WeightlessTraitTypeRepository;
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
