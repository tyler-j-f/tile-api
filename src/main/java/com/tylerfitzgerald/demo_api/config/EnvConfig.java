package com.tylerfitzgerald.demo_api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.application")
@Data
public class EnvConfig {
  private String alchemyAPIKey;
  private String alchemyBaseUrl;

  public String getAlchemyURI() {
    return alchemyBaseUrl + alchemyAPIKey;
  }
}
