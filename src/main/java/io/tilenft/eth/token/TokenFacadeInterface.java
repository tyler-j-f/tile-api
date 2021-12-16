package io.tilenft.eth.token;

import io.tilenft.eth.metadata.TokenMetadataDTO;
import io.tilenft.eth.token.initializers.TokenInitializeException;

public interface TokenFacadeInterface {
  TokenFacade initializeToken(Long tokenId, Long seedForTraits) throws TokenInitializeException;

  TokenFacade loadToken(Long tokenId) throws TokenInitializeException;

  TokenFacade setTokenFacadeDTO(TokenFacadeDTO nftFacadeDTO);

  TokenMetadataDTO buildTokenMetadataDTO();
}
