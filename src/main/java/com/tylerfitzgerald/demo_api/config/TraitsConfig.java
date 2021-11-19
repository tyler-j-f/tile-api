package com.tylerfitzgerald.demo_api.config;

import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.TraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypes.TraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraitTypes.WeightlessTraitTypeDTO;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.application.traits-config")
@Data
public class TraitsConfig {
  private TraitTypeDTO[] weightedTypes;
  private WeightlessTraitTypeDTO[] weightlessTypes;
  private TraitTypeWeightDTO[] typeWeights;
}
