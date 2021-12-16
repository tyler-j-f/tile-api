package io.tileNft.config.beans;

import io.tileNft.config.external.EnvConfig;
import io.tileNft.erc721.ethEvents.EthEventsRetriever;
import io.tileNft.erc721.ethEvents.RemoveDuplicateEthEventsForToken;
import io.tileNft.erc721.ethEvents.RemoveDuplicateMergeEthEvents;
import io.tileNft.etc.BigIntegerFactory;
import io.tileNft.scheduler.tasks.HandleMergeEventsTask;
import io.tileNft.scheduler.tasks.HandleMintEventsTask;
import io.tileNft.scheduler.tasks.HandleSetColorsEventsTask;
import io.tileNft.scheduler.tasks.HandleSetEmojisEventsTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class EthEventsBeansConfig {

  @Autowired private EnvConfig envConfig;

  @Bean
  public Web3j web3j() {
    return Web3j.build(new HttpService(envConfig.getAlchemyURI()));
  }

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
  public BigIntegerFactory bigIntegerFactory() {
    return new BigIntegerFactory();
  }
}
