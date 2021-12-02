package com.tylerfitzgerald.demo_api.config.beans;

import com.tylerfitzgerald.demo_api.erc721.token.initializers.MergeTokenInitializer;
import com.tylerfitzgerald.demo_api.ethEvents.RemoveDuplicateMergeEthEvents;
import com.tylerfitzgerald.demo_api.scheduler.tasks.HandleMergeEventsTask;
import com.tylerfitzgerald.demo_api.scheduler.tasks.HandleSetColorsEventsTask;
import com.tylerfitzgerald.demo_api.ethEvents.EthEventsRetriever;
import com.tylerfitzgerald.demo_api.scheduler.tasks.HandleMintEventsTask;
import com.tylerfitzgerald.demo_api.ethEvents.RemoveDuplicateEthEventsForToken;
import com.tylerfitzgerald.demo_api.scheduler.tasks.HandleSetEmojisEventsTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EthEventsBeansConfig {
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

  @Bean
  public HandleSetEmojisEventsTask handleSetEmojisEventsTask() {
    return new HandleSetEmojisEventsTask();
  }

  @Bean
  public HandleMergeEventsTask handleMergeEventsTask() {
    return new HandleMergeEventsTask();
  }

  @Bean
  public RemoveDuplicateEthEventsForToken removeDuplicateEvents() {
    return new RemoveDuplicateEthEventsForToken();
  }

  @Bean
  public RemoveDuplicateMergeEthEvents removeDuplicateMergeEthEvents() {
    return new RemoveDuplicateMergeEthEvents();
  }

  @Bean
  public MergeTokenInitializer mergeTokenInitializer() {
    return new MergeTokenInitializer();
  }
}
