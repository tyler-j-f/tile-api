package com.tylerfitzgerald.demo_api.config.external;

import java.util.ArrayList;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.application.sales-config")
@Data
public class SalesConfig {
  private ArrayList<Object> attributes;
  private String description;
  private String external_url;
  private String image;
  private String name;
}
