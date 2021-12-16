package io.tilenft.scheduler;

public class TaskSchedulerException extends Exception {
  public TaskSchedulerException(String message) {
    super(message);
  }

  public TaskSchedulerException(String message, Throwable cause) {
    super(message, cause);
  }
}
