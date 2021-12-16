package io.tileNft.scheduler.tasks;

import io.tileNft.scheduler.TaskSchedulerException;

public interface TaskInterface {
  void execute() throws TaskSchedulerException;
}
