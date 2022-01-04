package io.tilenft.scheduler;

import io.tilenft.scheduler.tasks.HandleMergeEventsTask;
import io.tilenft.scheduler.tasks.HandleMintEventsTask;
import io.tilenft.scheduler.tasks.HandleResetSqlTask;
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
  @Autowired private HandleResetSqlTask handleResetSqlTask;
  @Autowired private Environment env;
  private boolean hasSqlTablesBeenReset = false;

  /**
   * Execute tasks every schedulerFixedRateMs If you would like to execute tasks on a different
   * cadence, add another method like this with a different @Scheduled notation.
   *
   * @throws ExecutionException
   * @throws InterruptedException
   */
  @Scheduled(fixedDelayString = "${spring.application.events-config.schedulerFixedRateMs}")
  public void executeTasks() throws TaskSchedulerException {
    if (Boolean.parseBoolean(env.getProperty("shouldResetSqlTables")) && !hasSqlTablesBeenReset) {
      System.out.println("Resetting all of the SQL tables!!!");
      handleResetSqlTask.execute();
      hasSqlTablesBeenReset = true;
    }
    //    handleMintEventsTask.execute();
    //    handleSetColorsEventsTask.execute();
    //    handleSetEmojisEventsTask.execute();
    //    handleMergeEventsTask.execute();
  }
}
