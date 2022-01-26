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
  private String external_url;
  private String name;
  private String get_image_url_postfix;
  private String number_of_sales;

  public String getImage() {
    return contractConfig.getExternal_link() + get_image_url_postfix;
  }
}
