package io.tilenft.config.beans;

import io.tilenft.config.external.EnvConfig;
import io.tilenft.etc.BigIntegerFactory;
import io.tilenft.etc.HexStringPrefixStripper;
import io.tilenft.etc.HexValueToDecimal;
import io.tilenft.eth.events.EthEventsRetriever;
import io.tilenft.eth.events.RemoveDuplicateEthEventsForToken;
import io.tilenft.eth.events.RemoveDuplicateMergeEthEvents;
import io.tilenft.scheduler.tasks.HandleMergeEventsTask;
import io.tilenft.scheduler.tasks.HandleMintEventsTask;
import io.tilenft.scheduler.tasks.HandleResetSqlTask;
import io.tilenft.scheduler.tasks.HandleSetColorsEventsTask;
import io.tilenft.scheduler.tasks.HandleSetEmojisEventsTask;
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
    return Web3j.build(new HttpService(envConfig.getAlchemyUri()));
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
  public HandleResetSqlTask handleResetSqlTask() {
    return new HandleResetSqlTask();
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

  @Bean
  public HexValueToDecimal hexValueToDecimal() {
    return new HexValueToDecimal();
  }

  @Bean
  public HexStringPrefixStripper hexStringPrefixStripper() {
    return new HexStringPrefixStripper();
  }
}
