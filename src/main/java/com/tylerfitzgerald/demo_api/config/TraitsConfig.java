package com.tylerfitzgerald.demo_api.config;

import com.tylerfitzgerald.demo_api.sql.TraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.TraitTypeDTO;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.application.traits-config")
@Data
public class TraitsConfig {
  private TraitTypeDTO[] types;
  private TraitTypeWeightDTO[] typeWeights;
}
