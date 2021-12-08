package com.tylerfitzgerald.demo_api.erc721.token.initializers;

import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.AbstractWeightlessTraitsCreator;
import com.tylerfitzgerald.demo_api.sql.dtos.TokenDTO;

public interface TokenInitializerInterface {
  TokenDTO createToken(Long tokenId);

  TokenFacadeDTO buildTokenFacadeDTO(AbstractWeightlessTraitsCreator weightlessTraitsCreator);
}
