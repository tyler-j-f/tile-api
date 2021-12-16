package io.tileNft.config.external;

import io.tileNft.sql.dtos.WeightedTraitTypeDTO;
import io.tileNft.sql.dtos.WeightedTraitTypeWeightDTO;
import io.tileNft.sql.dtos.WeightlessTraitTypeDTO;
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
