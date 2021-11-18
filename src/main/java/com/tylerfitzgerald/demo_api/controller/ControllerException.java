package com.tylerfitzgerald.demo_api.controller;

public class ControllerException extends Exception {

  public ControllerException(String message) {
    super(message);
  }

  public ControllerException(String message, Throwable cause) {
    super(message, cause);
  }
}
