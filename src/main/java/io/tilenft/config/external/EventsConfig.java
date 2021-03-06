package io.tilenft.config.external;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.application.events-config")
@Data
public class EventsConfig {
  private String nftContractAddress;
  private String nftFactoryContractAddress;
  private String mintEventHashSignature;
  private String mergeEventHashSignature;
  private String setMetadataHashSignature;
  private String schedulerFixedRateMs;
  private String schedulerNumberOfBlocksToLookBack;
  private String baseOpenSeaUrl;
  private String openSeaCollectionPath;

  public String getOpenSeaTokenUrl(Long tokenId) {
    return baseOpenSeaUrl + "/assets/" + nftContractAddress + "/" + tokenId;
  }

  public String getOpenSeaSaleUrl() {
    return baseOpenSeaUrl + "/assets/" + nftFactoryContractAddress + "/0";
  }

  public String getOpenSeaCollectionUrl() {
    return baseOpenSeaUrl + "/collection/" + openSeaCollectionPath;
  }
}
