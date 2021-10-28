package com.tylerfitzgerald.demo_api.config;

import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.TraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypes.TraitTypeDTO;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.application.token-config")
@Data
public class TokenConfig {
  private String name;
  private String description;
  private String base_image_url;
  private String base_external_url;
  private String background_color;
  private String base_animation_url;
  private String base_youtube_url;
  // 'attributes' key decided by TraitsConfig
}
