package io.tilenft.eth.token.initializers;

import io.tilenft.eth.token.TokenFacadeDTO;
import io.tilenft.sql.dtos.TokenDTO;

public interface TokenInitializerInterface {
  TokenDTO createToken(Long tokenId);

  TokenFacadeDTO buildTokenFacadeDTO();
}
