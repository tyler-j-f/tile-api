package io.tilenft.config.external;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.application.token-config")
@Data
public class TokenConfig {
  @Autowired private ContractConfig contractConfig;
  private String base_name;
  private String description;
  private String base_image_url_postfix;
  private String base_external_url_postfix;
  private String background_color;
  private String base_animation_url;
  private String base_youtube_url;
  // 'attributes' key decided by TraitsConfig

  public String getBase_image_url() {
    return contractConfig.getExternal_link() + base_image_url_postfix;
  }

  public String getBase_external_url() {
    return contractConfig.getExternal_link() + base_external_url_postfix;
  }

  public String getDiscord_url() {
    return contractConfig.getDiscord_url();
  }
}
