package com.tylerfitzgerald.demo_api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tylerfitzgerald.demo_api.config.SalesConfig;
import com.tylerfitzgerald.demo_api.erc721.sales.SaleDTO;
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
@RequestMapping(value = {"/api/sales"})
public class SaleController extends BaseController {

  @Autowired private SalesConfig salesConfig;

  @GetMapping("get/{id}")
  public String getSaleJSON(@PathVariable String id) throws JsonProcessingException {
    return new ObjectMapper().writeValueAsString(salesConfig);
  }
}
