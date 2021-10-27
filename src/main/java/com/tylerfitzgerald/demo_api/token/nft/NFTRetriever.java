package com.tylerfitzgerald.demo_api.token.nft;

import com.tylerfitzgerald.demo_api.config.TraitsConfig;
import com.tylerfitzgerald.demo_api.token.TokenDTO;
import com.tylerfitzgerald.demo_api.token.TokenRepository;
import com.tylerfitzgerald.demo_api.token.traitTypeWeights.TraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.token.traitTypeWeights.TraitTypeWeightRepository;
import com.tylerfitzgerald.demo_api.token.traitTypes.TraitTypeDTO;
import com.tylerfitzgerald.demo_api.token.traitTypes.TraitTypeRepository;
import com.tylerfitzgerald.demo_api.token.traits.TraitDTO;
import com.tylerfitzgerald.demo_api.token.traits.TraitRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class NFTRetriever {

  private static final String NFT_NAME = "Tile";
  private static final String NFT_DESCRIPTION =
      "Tile NFT. Buy a tile and customize it yourself!!! Each tile will be generated with unique traits that decide how rare your Tile is.";
  private static final String NFT_EXTERNAL_URL = "http://tilenft.io/api/nft";
  private static final String NFT_IMG_URL_BASE = "http://tilenft.io/api/img/";

  @Autowired private TokenRepository tokenRepository;
  @Autowired private TraitRepository traitRepository;
  @Autowired private TraitTypeRepository traitTypeRepository;
  @Autowired private TraitTypeWeightRepository traitTypeWeightRepository;
  @Autowired private TraitsConfig traitsConfig;

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
