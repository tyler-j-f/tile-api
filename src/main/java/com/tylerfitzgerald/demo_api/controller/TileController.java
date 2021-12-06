package com.tylerfitzgerald.demo_api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacade;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.token.TokenRetriever;
import com.tylerfitzgerald.demo_api.erc721.token.initializers.TokenInitializeException;
import com.tylerfitzgerald.demo_api.erc721.token.initializers.TokenInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api/tiles"})
public class TileController extends BaseController {

  @Autowired private TokenRetriever tokenRetriever;

  @Autowired private TokenInitializer tokenInitializer;

  @GetMapping("create/{tokenId}")
  public String createTileNFT(@PathVariable Long tokenId)
      throws JsonProcessingException, TokenInitializeException {
    TokenFacadeDTO nft = tokenInitializer.initialize(tokenId);
    if (nft == null) {
      return "Could not create tokenId: " + tokenId;
    }
    return new ObjectMapper()
        .writeValueAsString(new TokenFacade().setTokenFacadeDTO(nft).buildTokenDataDTO());
  }

  @GetMapping("get/{tokenId}")
  public String getTileJSON(@PathVariable Long tokenId) throws JsonProcessingException {
    TokenFacadeDTO nft = tokenRetriever.get(tokenId);
    if (nft == null) {
      return "Could not find tokenId: " + tokenId;
    }
    return new ObjectMapper()
        .writeValueAsString(new TokenFacade().setTokenFacadeDTO(nft).buildTokenDataDTO());
  }
}
