package com.tylerfitzgerald.demo_api.config.external;

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
  private String setColorsEventHashSignature;
  private String setEmojisEventHashSignature;
  private String schedulerFixedRateMs;
  private String schedulerNumberOfBlocksToLookBack;
}
