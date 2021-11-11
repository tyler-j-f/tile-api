package com.tylerfitzgerald.demo_api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tylerfitzgerald.demo_api.config.ContractConfig;
import com.tylerfitzgerald.demo_api.erc721.contract.ContractDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api/contract"})
public class ContractController extends BaseController {

  @Autowired private ContractConfig contractConfig;

  @GetMapping("get")
  public String getContractJSON() throws JsonProcessingException {
    return new ObjectMapper()
        .writeValueAsString(
            ContractDTO.builder()
                .name(contractConfig.getName())
                .description(contractConfig.getDescription())
                .image(contractConfig.getImage())
                .external_link(contractConfig.getExternal_link())
                .seller_fee_basis_points(contractConfig.getSeller_fee_basis_points())
                .fee_recipient(contractConfig.getFee_recipient())
                .build());
  }
}
