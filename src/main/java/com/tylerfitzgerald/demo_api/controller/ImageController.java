package com.tylerfitzgerald.demo_api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacade;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.token.TokenInitializer;
import com.tylerfitzgerald.demo_api.erc721.token.TokenRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api/tiles/image"})
public class ImageController extends BaseController {

  @Autowired private TokenRetriever tokenRetriever;

  @Autowired private TokenInitializer tokenInitializer;

  @GetMapping("create/{tokenId}")
  public String createTokenImage(@PathVariable Long tokenId) throws JsonProcessingException {
    return "createTokenImage";
  }

  @GetMapping("get/{tokenId}")
  public String getTokenImage(@PathVariable Long tokenId) throws JsonProcessingException {
    return "getTokenImage";
  }

  @GetMapping("contractImage/get/{contractImageId}")
  public String getContractImage(@PathVariable Long contractImageId)
      throws JsonProcessingException {
    return "getContractImage";
  }
}
