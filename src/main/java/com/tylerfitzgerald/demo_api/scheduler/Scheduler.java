package com.tylerfitzgerald.demo_api.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    @Scheduled(fixedRate = 5000)
    public void scheduledTasks() {
        System.out.println("TasK Ran!!!");
    }
}
