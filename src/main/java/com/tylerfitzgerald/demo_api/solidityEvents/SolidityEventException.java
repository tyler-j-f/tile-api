package com.tylerfitzgerald.demo_api.solidityEvents;

public class SolidityEventException extends Exception {

  public SolidityEventException(String message) {
    super(message);
  }

  public SolidityEventException(String message, Throwable cause) {
    super(message, cause);
  }
}
