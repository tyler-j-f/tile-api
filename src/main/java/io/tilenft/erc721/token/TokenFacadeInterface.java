package io.tilenft.erc721.token;

import io.tilenft.erc721.TokenMetadataDTO;
import io.tilenft.erc721.token.initializers.TokenInitializeException;

public interface TokenFacadeInterface {
  TokenFacade initializeToken(Long tokenId, Long seedForTraits) throws TokenInitializeException;

  TokenFacade loadToken(Long tokenId) throws TokenInitializeException;

  TokenFacade setTokenFacadeDTO(TokenFacadeDTO nftFacadeDTO);

  TokenMetadataDTO buildTokenMetadataDTO();
}
