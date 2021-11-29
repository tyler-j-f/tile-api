package com.tylerfitzgerald.demo_api.scheduler;

import com.tylerfitzgerald.demo_api.scheduler.tasks.HandleMergeEventsTask;
import com.tylerfitzgerald.demo_api.scheduler.tasks.HandleMintEventsTask;
import com.tylerfitzgerald.demo_api.scheduler.tasks.HandleSetColorsEventsTask;
import com.tylerfitzgerald.demo_api.scheduler.tasks.HandleSetEmojisEventsTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class Scheduler {

  @Autowired private HandleMintEventsTask handleMintEventsTask;
  @Autowired private HandleSetColorsEventsTask handleSetColorsEventsTask;
  @Autowired private HandleSetEmojisEventsTask handleSetEmojisEventsTask;
  @Autowired private HandleMergeEventsTask handleMergeEventsTask;

  /**
   * Execute tasks every schedulerFixedRateMs If you would like to execute tasks on a different
   * cadence, add another method like this with a different @Scheduled notation.
   *
   * @throws ExecutionException
   * @throws InterruptedException
   */
  @Scheduled(fixedRateString = "${spring.application.events-config.schedulerFixedRateMs}")
  public void executeTasks() throws TaskSchedulerException {
    handleMintEventsTask.execute();
    handleSetColorsEventsTask.execute();
    handleSetEmojisEventsTask.execute();
    handleMergeEventsTask.execute();
  }
}
