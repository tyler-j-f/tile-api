package io.tilenft.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.tilenft.config.external.ContractConfig;
import io.tilenft.config.external.EventsConfig;
import io.tilenft.eth.token.TokenFacade;
import io.tilenft.eth.token.initializers.TokenInitializeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api/tiles"})
public class TileController extends BaseController {

  @Autowired private TokenFacade tokenFacade;
  @Autowired private ContractConfig contractConfig;
  @Autowired private EventsConfig eventsConfig;

  @GetMapping("create/{tokenId}")
  public String createTileNFT(@PathVariable Long tokenId)
      throws JsonProcessingException, TokenInitializeException {
    return new ObjectMapper()
        .writeValueAsString(
            tokenFacade
                .initializeToken(tokenId, System.currentTimeMillis())
                .buildTokenMetadataDTO());
  }

  @GetMapping("get/{tokenId}")
  public String getTileJSON(@PathVariable Long tokenId)
      throws JsonProcessingException, TokenInitializeException {
    return new ObjectMapper()
        .writeValueAsString(tokenFacade.loadToken(tokenId).buildTokenMetadataDTO());
  }

  @GetMapping("blockExplorerUrl/get/{tokenId}")
  public String getBlockExplorerUrl(@PathVariable Long tokenId) throws JsonProcessingException {
    return new ObjectMapper().writeValueAsString(getBlockExplorerUrlFromId(tokenId));
  }

  private String getBlockExplorerUrlFromId(Long tokenId) {
    return contractConfig.getBlock_explorer_base_url()
        + eventsConfig.getNftContractAddress()
        + getBlockExplorerUrlTokenIdUrlParameter(tokenId);
  }

  private String getBlockExplorerUrlTokenIdUrlParameter(Long tokenId) {
    return "?a=" + tokenId.toString();
  }
}
