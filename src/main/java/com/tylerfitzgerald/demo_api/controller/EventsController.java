package com.tylerfitzgerald.demo_api.controller;

import com.tylerfitzgerald.demo_api.events.MintEvent;
import com.tylerfitzgerald.demo_api.config.EnvConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.core.methods.response.Log;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = {"/api/events"})
public class EventsController {

    @Autowired
    private Web3j web3j;

    @Autowired
    private EnvConfig appConfig;

    @GetMapping(
            value = {"mint/getAll/{numberOfBlocksAgo}", "mint/getAll"}
    )
    public String getMintEvents(@PathVariable(required = false) String numberOfBlocksAgo) throws ExecutionException, InterruptedException {
        if (numberOfBlocksAgo == null) {
            numberOfBlocksAgo = "5760";
        }
        BigInteger currentBlockNumber = web3j.ethBlockNumber().sendAsync().get().getBlockNumber();
        EthFilter filter = new EthFilter(
                DefaultBlockParameter.valueOf(currentBlockNumber.subtract(new BigInteger(numberOfBlocksAgo))),
                DefaultBlockParameter.valueOf(currentBlockNumber), appConfig.getNftFactoryContractAddress()
        );
        List<EthLog.LogResult> logs = web3j.ethGetLogs(filter).sendAsync().get().getLogs();
        int size = logs.size();
        if (size == 0) {
            String output = "No events found";
            System.out.println(output);
            return output;
        }
        List<MintEvent> events = new ArrayList<>();
        for (EthLog.LogResult log : logs) {
            List<String> topics = ((Log) log).getTopics();
            if (topics.get(0).equals(appConfig.getMintEventHashSignature())) {
                events.add(
                        MintEvent.builder()
                                .topics(topics)
                                .build()
                );
            }
        }
        return events.toString();
    }

}
