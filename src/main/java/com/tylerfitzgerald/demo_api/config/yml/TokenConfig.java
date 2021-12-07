package com.tylerfitzgerald.demo_api.config.yml;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.application.token-config")
@Data
public class TokenConfig {
  private String base_name;
  private String description;
  private String base_image_url;
  private String base_external_url;
  private String background_color;
  private String base_animation_url;
  private String base_youtube_url;
  // 'attributes' key decided by TraitsConfig
}
