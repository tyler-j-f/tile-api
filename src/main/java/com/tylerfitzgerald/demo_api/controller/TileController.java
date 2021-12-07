package com.tylerfitzgerald.demo_api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacade;
import com.tylerfitzgerald.demo_api.erc721.token.initializers.TokenInitializeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api/tiles"})
public class TileController extends BaseController {

  @Autowired private TokenFacade tokenFacade;

  @GetMapping("create/{tokenId}")
  public String createTileNFT(@PathVariable Long tokenId)
      throws JsonProcessingException, TokenInitializeException {
    return new ObjectMapper()
        .writeValueAsString(
            tokenFacade.initializeToken(tokenId, System.currentTimeMillis()).buildTokenDataDTO());
  }

  @GetMapping("get/{tokenId}")
  public String getTileJSON(@PathVariable Long tokenId)
      throws JsonProcessingException, TokenInitializeException {
    return new ObjectMapper().writeValueAsString(tokenFacade.getTokenMetadataDTO(tokenId));
  }
}
