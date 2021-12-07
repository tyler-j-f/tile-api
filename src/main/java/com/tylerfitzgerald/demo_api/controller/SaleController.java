package com.tylerfitzgerald.demo_api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tylerfitzgerald.demo_api.config.external.SalesConfig;
import com.tylerfitzgerald.demo_api.erc721.metadata.SaleMetadataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api/sales"})
public class SaleController extends BaseController {

  @Autowired private SalesConfig salesConfig;

  @GetMapping("get/{id}")
  public String getSaleJSON(@PathVariable String id) throws JsonProcessingException {
    return new ObjectMapper()
        .writeValueAsString(
            SaleMetadataDTO.builder()
                .attributes(salesConfig.getAttributes())
                .description(salesConfig.getDescription())
                .external_url(salesConfig.getExternal_url())
                .image(salesConfig.getImage())
                .name(salesConfig.getName())
                .build());
  }
}
