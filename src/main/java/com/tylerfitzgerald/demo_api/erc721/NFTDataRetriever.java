package com.tylerfitzgerald.demo_api.erc721;

import com.tylerfitzgerald.demo_api.sql.tokens.TokenDTO;
import com.tylerfitzgerald.demo_api.sql.tokens.TokenRepository;
import com.tylerfitzgerald.demo_api.sql.nft.NFTFacadeDTO;
import com.tylerfitzgerald.demo_api.sql.traitTypeWeights.TraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.traitTypeWeights.TraitTypeWeightRepository;
import com.tylerfitzgerald.demo_api.sql.traitTypes.TraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.traitTypes.TraitTypeRepository;
import com.tylerfitzgerald.demo_api.sql.traits.TraitDTO;
import com.tylerfitzgerald.demo_api.sql.traits.TraitRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class NFTDataRetriever {

  @Autowired private TokenRepository tokenRepository;
  @Autowired private TraitRepository traitRepository;
  @Autowired private TraitTypeRepository traitTypeRepository;
  @Autowired private TraitTypeWeightRepository traitTypeWeightRepository;

  private List<TraitTypeDTO> availableTraitTypes = new ArrayList<>();
  private List<TraitTypeWeightDTO> availableTraitTypeWeights = new ArrayList<>();
  private List<TraitDTO> tokenTraits = new ArrayList<>();
  private TokenDTO tokenDTO;

  public NFTFacadeDTO get(Long tokenId) {
    availableTraitTypeWeights = traitTypeWeightRepository.read();
    availableTraitTypes = traitTypeRepository.read();
    tokenDTO = getToken(tokenId);
    if (tokenDTO == null) {
      System.out.println("NFTRetriever failed to retrieve the token with tokenId: " + tokenId);
      return null;
    }
    tokenTraits = getTraits(tokenId);
    return buildNFTFacade();
  }

  private NFTFacadeDTO buildNFTFacade() {
    return NFTFacadeDTO.builder()
        .tokenDTO(tokenDTO)
        .tokenTraits(tokenTraits)
        .availableTraitTypes(availableTraitTypes)
        .availableTraitTypeWeights(availableTraitTypeWeights)
        .build();
  }

  private TokenDTO getToken(Long tokenId) {
    return tokenRepository.readById(tokenId);
  }

  private List<TraitDTO> getTraits(Long tokenId) {
    return traitRepository.readByTokenId(tokenId);
  }
}
