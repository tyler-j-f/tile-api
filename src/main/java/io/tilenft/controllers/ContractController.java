package io.tilenft.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.tilenft.config.external.ContractConfig;
import io.tilenft.config.external.EventsConfig;
import io.tilenft.eth.metadata.ContractMetadataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api/contract"})
public class ContractController extends BaseController {

  @Autowired private ContractConfig contractConfig;
  @Autowired private EventsConfig eventsConfig;

  @GetMapping("get")
  public String getContractJSON() throws JsonProcessingException {
    return new ObjectMapper()
        .writeValueAsString(
            ContractMetadataDTO.builder()
                .name(contractConfig.getName())
                .description(contractConfig.getDescription())
                .image(contractConfig.getImage())
                .external_link(contractConfig.getExternal_link())
                .seller_fee_basis_points(contractConfig.getSeller_fee_basis_points())
                .fee_recipient(contractConfig.getFee_recipient())
                .build());
  }

  @GetMapping("getContractAddress")
  public String getContractAddress() throws JsonProcessingException {
    return new ObjectMapper().writeValueAsString(eventsConfig.getNftContractAddress());
  }

  @GetMapping("getOwnerAddress/{tokenId}")
  public String getOwnerAddress(@PathVariable Long tokenId) throws JsonProcessingException {
    String address = "0x4fdF8DF271e1A65B119D858eeA2A7681da8F9c15";
    return new ObjectMapper().writeValueAsString(address);
  }
}
