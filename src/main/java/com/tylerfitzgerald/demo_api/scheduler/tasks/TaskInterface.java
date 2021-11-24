package com.tylerfitzgerald.demo_api.scheduler.tasks;

import com.tylerfitzgerald.demo_api.scheduler.TaskSchedulerException;

public interface TaskInterface {
  void execute() throws TaskSchedulerException;
}
