package io.tilenft.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.tilenft.config.external.SalesConfig;
import io.tilenft.eth.metadata.SaleMetadataDTO;
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
  public String getSaleJSON(@PathVariable int id)
      throws JsonProcessingException, ControllerException {
    if (id > Integer.parseInt(salesConfig.getNumber_of_sales()) - 1) {
      throw new ControllerException("No sale available. id: " + id);
    }
    return new ObjectMapper()
        .writeValueAsString(
            SaleMetadataDTO.builder()
                .attributes(salesConfig.getAttributes())
                .description(salesConfig.getDescription())
                .external_url(salesConfig.getExternal_url())
                .image(salesConfig.getImage())
                .name(salesConfig.getName())
                .discord_url(salesConfig.getDiscord_url())
                .build());
  }
}
