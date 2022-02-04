package io.tilenft.config.external;

import java.util.ArrayList;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.application.sales-config")
@Data
public class SalesConfig {
  @Autowired private ContractConfig contractConfig;
  private ArrayList<Object> attributes;
  private String description;
  private String name;
  private String get_image_url_postfix;
  private String number_of_sales;

  public String getExternal_url() {
    return contractConfig.getExternal_link();
  }

  public String getImage() {
    return contractConfig.getExternal_link() + get_image_url_postfix;
  }

  public String getDiscord_url() {
    return contractConfig.getDiscord_url();
  }
}
