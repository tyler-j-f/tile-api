package com.tylerfitzgerald.demo_api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.application.contract-config")
@Data
public class ContractConfig {
  private String name;
  private String description;
  private String image;
  private String external_link;
  private String seller_fee_basis_points;
  private String fee_recipient;
}
