package com.tylerfitzgerald.demo_api.config.beans;

import com.tylerfitzgerald.demo_api.image.ImageDrawer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImageConfig {

  @Bean
  public ImageDrawer imageDrawer() {
    return new ImageDrawer();
  }
}
