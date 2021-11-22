package com.tylerfitzgerald.demo_api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.application")
@Data
public class EnvConfig {
  private String name;
  private int tileCount;
  private int bitsPerTile;
  private String alchemyAPIKey;
  private String alchemyBaseUrl;
  private String nftContractAddress;
  private String nftFactoryContractAddress;
  private String mintEventHashSignature;
  private String mergeMintEventHashSignature;
  private String schedulerFixedRateMs;
  private String schedulerNumberOfBlocksToLookBack;

  public String getAlchemyURI() {
    return alchemyBaseUrl + alchemyAPIKey;
  }
}
