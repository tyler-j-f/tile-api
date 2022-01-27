package io.tilenft.config.external;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.application.contract-config")
@Data
public class ContractConfig {
  private String name;
  private String description;
  private String external_link;
  private String get_image_url_postfix;
  private String seller_fee_basis_points;
  private String fee_recipient;
  private String block_explorer_base_url;
  private String discord_url;

  public String getImage() {
    return external_link + get_image_url_postfix;
  }
}
