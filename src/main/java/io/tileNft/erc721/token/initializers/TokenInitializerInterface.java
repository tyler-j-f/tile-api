package io.tileNft.erc721.token.initializers;

import io.tileNft.erc721.token.TokenFacadeDTO;
import io.tileNft.sql.dtos.TokenDTO;

public interface TokenInitializerInterface {
  TokenDTO createToken(Long tokenId);

  TokenFacadeDTO buildTokenFacadeDTO();
}
