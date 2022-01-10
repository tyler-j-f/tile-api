package io.tilenft.scheduler.tasks;

import io.tilenft.scheduler.TaskSchedulerException;
import io.tilenft.sql.tbls.SqlTablesCreator;
import io.tilenft.sql.tbls.SqlTablesDropper;
import io.tilenft.sql.tbls.SqlTablesInitializer;
import org.springframework.beans.factory.annotation.Autowired;

public class HandleResetSqlTask extends AbstractEthEventsRetrieverTask {

  @Autowired private SqlTablesCreator sqlTablesCreator;
  @Autowired private SqlTablesInitializer sqlTablesInitializer;
  @Autowired private SqlTablesDropper sqlTablesDropper;

  @Override
  public void execute() throws TaskSchedulerException {
    System.out.println("HandleResetSqlTask, dropSqlTables: " + sqlTablesDropper.dropSqlTables());
    System.out.println(
        "HandleResetSqlTask, createSqlTables: " + sqlTablesCreator.createSqlTables());
    System.out.println(
        "HandleResetSqlTask, initialTablesPopulate: "
            + sqlTablesInitializer.initialTablesPopulate());
  }
}
