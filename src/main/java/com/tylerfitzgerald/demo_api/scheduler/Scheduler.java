package com.tylerfitzgerald.demo_api.scheduler;

import com.tylerfitzgerald.demo_api.config.EnvConfig;
import com.tylerfitzgerald.demo_api.events.MintEvent;
import com.tylerfitzgerald.demo_api.events.MintEventRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class Scheduler {

    @Autowired
    private MintEventRetriever mintEventRetriever;

    @Autowired
    private EnvConfig appConfig;

    @Scheduled(fixedRateString = "${spring.application.schedulerFixedRateMs}")
    public void getMintEvents() throws ExecutionException, InterruptedException {
        List<MintEvent> events = mintEventRetriever.getMintEvents(
                new BigInteger(appConfig.getSchedulerNumberOfBlocksToLookBack())
        );
        String output;
        if (events.size() == 0) {
            System.out.println("No events found");
            return;
        }
        System.out.println(events.toString());
    }
}
