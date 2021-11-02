package com.tylerfitzgerald.demo_api.scheduler;

import com.tylerfitzgerald.demo_api.config.SalesConfig;
import com.tylerfitzgerald.demo_api.config.TokenConfig;
import com.tylerfitzgerald.demo_api.config.TraitsConfig;
import com.tylerfitzgerald.demo_api.scheduler.tasks.HandleMintEvents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class Scheduler {

  @Autowired private HandleMintEvents handleMintEventsAndCreateDBTokensTask;
  @Autowired private SalesConfig salesConfig;
  @Autowired private TokenConfig tokenConfig;
  @Autowired private TraitsConfig traitsConfig;

  /**
   * Execute tasks every schedulerFixedRateMs If you would like to execute tasks on a different
   * cadence, add another method like this with a different @Scheduled notation.
   *
   * @throws ExecutionException
   * @throws InterruptedException
   */
  @Scheduled(fixedRateString = "${spring.application.schedulerFixedRateMs}")
  public void executeTasks() throws ExecutionException, InterruptedException {
    handleMintEventsAndCreateDBTokensTask.execute();
    //    System.out.println(salesConfig);
    //    System.out.println(tokenConfig);
    //    System.out.println(traitsConfig);
  }
}
