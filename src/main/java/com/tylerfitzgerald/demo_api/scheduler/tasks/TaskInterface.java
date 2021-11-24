package com.tylerfitzgerald.demo_api.scheduler.tasks;

import com.tylerfitzgerald.demo_api.scheduler.TaskSchedulerException;

public interface TaskInterface {
  public void execute() throws TaskSchedulerException;
}
