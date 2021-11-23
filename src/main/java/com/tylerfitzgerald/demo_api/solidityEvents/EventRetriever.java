package com.tylerfitzgerald.demo_api.solidityEvents;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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

public class EventRetriever<T> {

  @Autowired private Web3j web3j;

  public List<T> getEvents(
      String eventClassName,
      String contractAddress,
      String eventHashSignature,
      BigInteger numberOfBlocksAgo)
      throws ExecutionException, InterruptedException, ClassNotFoundException,
          NoSuchMethodException, InvocationTargetException, InstantiationException,
          IllegalAccessException {
    Class<?> eventClass = Class.forName(eventClassName);
    Constructor<?> eventConstructor = eventClass.getConstructor(List.class, String.class);

    BigInteger currentBlockNumber = web3j.ethBlockNumber().sendAsync().get().getBlockNumber();
    EthFilter filter =
        new EthFilter(
            DefaultBlockParameter.valueOf(currentBlockNumber.subtract(numberOfBlocksAgo)),
            DefaultBlockParameter.valueOf(currentBlockNumber),
            contractAddress);
    List<EthLog.LogResult> logs = web3j.ethGetLogs(filter).sendAsync().get().getLogs();
    List<T> events = new ArrayList<>();
    if (logs.size() == 0) {
      return events;
    }
    for (EthLog.LogResult log : logs) {
      List<String> topics = ((Log) log).getTopics();
      if (topics.get(0).equals(eventHashSignature)) {
        T event =
            (T)
                eventConstructor.newInstance(
                    new Object[] {topics, ((Log) log).getTransactionHash()});
        events.add(event);
        System.out.println(event);
      }
    }
    return events;
  }
}
