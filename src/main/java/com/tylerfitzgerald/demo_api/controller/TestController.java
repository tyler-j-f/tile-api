package com.tylerfitzgerald.demo_api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tylerfitzgerald.demo_api.erc721.NFTData;
import com.tylerfitzgerald.demo_api.config.EnvConfig;
import com.tylerfitzgerald.demo_api.sql.TraitTypeRepository;
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
public class TestController extends BaseController {

  @Autowired private EnvConfig appConfig;

  @Autowired private TraitTypeRepository traitTypeRepository;

  @GetMapping("creature/{id}")
  public String getCreatureJSON(@PathVariable String id) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    ArrayList<Trait> traits =
        new ArrayList<Trait>(
            Arrays.asList(
                new Trait("base", "jellyfish"),
                new Trait("eyes", "big"),
                new Trait("mouth", "happy"),
                new Trait("level", "5"),
                new Trait("stamina", "1.4"),
                new Trait("personality", "happy"),
                new DisplayTypeTrait("aqua_power", "10", "boost_number"),
                new DisplayTypeTrait("stamina_increase", "5", "boost_percentage"),
                new DisplayTypeTrait("generation", "1", "number")));
    NFTData creature =
        new NFTData(
            traits,
            "Friendly OpenSea Creature that enjoys long swims in the ocean.",
            "https://example.com/?token_id=0",
            "https://storage.googleapis.com/opensea-prod.appspot.com/creature/0.png",
            "Herbie Starbelly");
    return objectMapper.writeValueAsString(creature);
  }

  @GetMapping("sale/{id}")
  public String getSaleJSON(@PathVariable String id) throws JsonProcessingException {
    int totalSaleOptions = appConfig.getTileCount() * appConfig.getBitsPerTile();
    if (Integer.parseInt(id) > (totalSaleOptions - 1)) {
      // There is only one sale that exists in the factory contract.
      return null;
    }
    ObjectMapper objectMapper = new ObjectMapper();
    ArrayList<Trait> traits = new ArrayList<Trait>(Arrays.asList(new Trait("number_inside", "1")));
    NFTData tile =
        new NFTData(
            traits,
            "TESTING!!!! Friendly OpenSea Creature that enjoys long swims in the ocean.",
            "https://example.com/?token_id=0",
            "https://storage.googleapis.com/opensea-prod.appspot.com/creature/0.png",
            "Herbie Starbelly");
    return objectMapper.writeValueAsString(tile);
  }
}
