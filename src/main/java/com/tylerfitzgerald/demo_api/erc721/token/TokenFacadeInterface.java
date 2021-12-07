package com.tylerfitzgerald.demo_api.erc721.token;

import com.tylerfitzgerald.demo_api.erc721.token.initializers.TokenInitializeException;

public interface TokenFacadeInterface {
  TokenFacade initializeToken(Long tokenId, Long seedForTraits) throws TokenInitializeException;

  TokenMetadataDTO getTokenMetadataDTO(Long tokenId) throws TokenInitializeException;

  TokenFacade setTokenFacadeDTO(TokenFacadeDTO nftFacadeDTO);

  TokenMetadataDTO buildTokenDataDTO();
}
