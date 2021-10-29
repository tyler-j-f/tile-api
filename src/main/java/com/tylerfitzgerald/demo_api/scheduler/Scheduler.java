package com.tylerfitzgerald.demo_api.scheduler;

import com.tylerfitzgerald.demo_api.scheduler.tasks.HandleMintEvents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class Scheduler {

  @Autowired private HandleMintEvents handleMintEventsAndCreateDBTokensTask;

  /**
   * Execute tasks every schedulerFixedRateMs If you would like to execute tasks on a different
   * cadence, add another method like this with a different @Scheduled notation.
   *
   * @throws ExecutionException
   * @throws InterruptedException
   */
  // @Scheduled(fixedRateString = "${spring.application.schedulerFixedRateMs}")
  public void executeTasks() throws ExecutionException, InterruptedException {
    handleMintEventsAndCreateDBTokensTask.execute();
  }
}
