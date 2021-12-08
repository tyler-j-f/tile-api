package com.tylerfitzgerald.demo_api.config.external;

import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightlessTraitTypeDTO;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.application.traits-config")
@Data
public class TraitsConfig {
  private WeightedTraitTypeDTO[] weightedTypes;
  private WeightlessTraitTypeDTO[] weightlessTypes;
  private WeightedTraitTypeWeightDTO[] typeWeights;
}
