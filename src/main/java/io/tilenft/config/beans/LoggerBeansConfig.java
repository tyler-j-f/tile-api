package io.tilenft.config.beans;

import io.tilenft.TileApiApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerBeansConfig {
  @Bean
  public Logger logger() {
    return LoggerFactory.getLogger(TileApiApplication.class);
  }
}
