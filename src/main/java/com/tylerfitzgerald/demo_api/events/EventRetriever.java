package com.tylerfitzgerald.demo_api.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.core.methods.response.Log;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class EventRetriever {

  @Autowired private Web3j web3j;

  public List<AbstractEvent> getEvents(
      String contractAddress, String eventHashSignature, BigInteger numberOfBlocksAgo)
      throws ExecutionException, InterruptedException {
    BigInteger currentBlockNumber = web3j.ethBlockNumber().sendAsync().get().getBlockNumber();
    EthFilter filter =
        new EthFilter(
            DefaultBlockParameter.valueOf(currentBlockNumber.subtract(numberOfBlocksAgo)),
            DefaultBlockParameter.valueOf(currentBlockNumber),
            contractAddress);
    List<EthLog.LogResult> logs = web3j.ethGetLogs(filter).sendAsync().get().getLogs();
    List<AbstractEvent> events = new ArrayList<>();
    if (logs.size() == 0) {
      return events;
    }
    for (EthLog.LogResult log : logs) {
      List<String> topics = ((Log) log).getTopics();
      if (topics.get(0).equals(eventHashSignature)) {
        AbstractEvent event =
            AbstractEvent.builder()
                .topics(topics)
                .transactionHash(((Log) log).getTransactionHash())
                .build();
        events.add(event);
        System.out.println(event);
      }
    }
    return events;
  }
}
