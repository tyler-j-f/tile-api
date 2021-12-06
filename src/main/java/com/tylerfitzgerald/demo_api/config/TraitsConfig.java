package com.tylerfitzgerald.demo_api.config;

import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.WeightedTraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypes.WeightedTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraitTypes.WeightlessTraitTypeDTO;
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
