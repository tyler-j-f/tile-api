package com.tylerfitzgerald.demo_api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tylerfitzgerald.demo_api.erc721.token.TokenDataDTO;
import com.tylerfitzgerald.demo_api.config.EnvConfig;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypes.TraitTypeRepository;
import com.tylerfitzgerald.demo_api.erc721.traits.DisplayTypeTrait;
import com.tylerfitzgerald.demo_api.erc721.traits.Trait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping(value = {"/api"})
public class SaleController extends BaseController {

  @Autowired private EnvConfig appConfig;

  @Autowired private TraitTypeRepository traitTypeRepository;

  @GetMapping("sale/{id}")
  public String getSaleJSON(@PathVariable String id) throws JsonProcessingException {
    int totalSaleOptions = appConfig.getTileCount() * appConfig.getBitsPerTile();
    if (Integer.parseInt(id) > (totalSaleOptions - 1)) {
      // There is only one sale that exists in the factory contract.
      return null;
    }
    ObjectMapper objectMapper = new ObjectMapper();
    ArrayList<Object> traits =
        new ArrayList<Object>(Arrays.asList(new Trait("number_inside", "1")));
    TokenDataDTO tile =
        new TokenDataDTO(
            traits,
            "TESTING!!!! Friendly OpenSea Creature that enjoys long swims in the ocean.",
            "https://example.com/?token_id=0",
            "https://storage.googleapis.com/opensea-prod.appspot.com/creature/0.png",
            "Herbie Starbelly");
    return objectMapper.writeValueAsString(tile);
  }
}
