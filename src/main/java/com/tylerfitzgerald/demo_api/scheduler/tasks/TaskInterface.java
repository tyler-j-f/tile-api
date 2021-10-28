package com.tylerfitzgerald.demo_api.scheduler.tasks;

import java.util.concurrent.ExecutionException;

public interface TaskInterface {
  public void execute() throws ExecutionException, InterruptedException;
}
