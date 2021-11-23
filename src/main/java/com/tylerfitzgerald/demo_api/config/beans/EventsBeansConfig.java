package com.tylerfitzgerald.demo_api.config.beans;

import com.tylerfitzgerald.demo_api.solidityEvents.EventRetriever;
import com.tylerfitzgerald.demo_api.scheduler.tasks.HandleMintEventsTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventsBeansConfig {
  @Bean
  public EventRetriever eventRetriever() {
    return new EventRetriever();
  }

  @Bean
  public HandleMintEventsTask handleMintEventsTask() {
    return new HandleMintEventsTask();
  }
}
