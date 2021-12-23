package io.tilenft.scheduler;

import io.tilenft.scheduler.tasks.HandleMergeEventsTask;
import io.tilenft.scheduler.tasks.HandleMintEventsTask;
import io.tilenft.scheduler.tasks.HandleSetColorsEventsTask;
import io.tilenft.scheduler.tasks.HandleSetEmojisEventsTask;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

  @Autowired private HandleMintEventsTask handleMintEventsTask;
  @Autowired private HandleSetColorsEventsTask handleSetColorsEventsTask;
  @Autowired private HandleSetEmojisEventsTask handleSetEmojisEventsTask;
  @Autowired private HandleMergeEventsTask handleMergeEventsTask;
  @Autowired private Environment env;

  /**
   * Execute tasks every schedulerFixedRateMs If you would like to execute tasks on a different
   * cadence, add another method like this with a different @Scheduled notation.
   *
   * @throws ExecutionException
   * @throws InterruptedException
   */
  @Scheduled(fixedRateString = "${spring.application.events-config.schedulerFixedRateMs}")
  public void executeTasks() throws TaskSchedulerException {
    boolean shouldResetSqlTables = Boolean.parseBoolean(env.getProperty("shouldResetSqlTables"));
    handleMintEventsTask.execute();
    handleSetColorsEventsTask.execute();
    handleSetEmojisEventsTask.execute();
    handleMergeEventsTask.execute();
  }
}
