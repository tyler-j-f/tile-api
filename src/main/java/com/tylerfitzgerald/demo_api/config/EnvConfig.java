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
  private String schedulerFixedRateMs;
  private String schedulerNumberOfBlocksToLookBack;
  private String nftName;
  private String nftExternalUrl;
  private String nftBaseImageUrl;
  private String nftDescription;

  public String getAlchemyURI() {
    return alchemyBaseUrl + alchemyAPIKey;
  }
}
