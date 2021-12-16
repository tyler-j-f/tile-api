package io.tilenft.erc721.token.initializers;

import io.tilenft.erc721.token.TokenFacadeDTO;
import io.tilenft.sql.dtos.TokenDTO;

public interface TokenInitializerInterface {
  TokenDTO createToken(Long tokenId);

  TokenFacadeDTO buildTokenFacadeDTO();
}
