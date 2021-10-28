package com.tylerfitzgerald.demo_api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tylerfitzgerald.demo_api.token.nft.NFTFacade;
import com.tylerfitzgerald.demo_api.token.nft.NFTFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.NFTRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api/tiles"})
public class TileController extends BaseController {

  @Autowired private NFTRetriever nftRetriever;

  @GetMapping("get/{tokenId}")
  public String getTileJSON(@PathVariable Long tokenId) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    NFTFacadeDTO nft = nftRetriever.get(tokenId);
    if (nft == null) {
      System.out.println("\nDEBUG: nftInitializer->get failed. tokenId: " + tokenId.toString());
      return "Could not find tokenId: " + tokenId;
    }
    return objectMapper.writeValueAsString(new NFTFacade(nft).buildNFTData());
  }
}
