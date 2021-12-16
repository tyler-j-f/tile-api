package io.tilenft.scheduler.tasks;

import io.tilenft.scheduler.TaskSchedulerException;

public interface TaskInterface {
  void execute() throws TaskSchedulerException;
}
