package com.tylerfitzgerald.demo_api.config.beans;

import com.tylerfitzgerald.demo_api.scheduler.tasks.HandleSetColorsEventsTask;
import com.tylerfitzgerald.demo_api.solidityEvents.EthEventsRetriever;
import com.tylerfitzgerald.demo_api.scheduler.tasks.HandleMintEventsTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventsBeansConfig {
  @Bean
  public EthEventsRetriever eventRetriever() {
    return new EthEventsRetriever();
  }

  @Bean
  public HandleMintEventsTask handleMintEventsTask() {
    return new HandleMintEventsTask();
  }

  @Bean
  public HandleSetColorsEventsTask handleSetColorsEventsTask() {
    return new HandleSetColorsEventsTask();
  }
}
