package com.tylerfitzgerald.demo_api.erc721.token;

public class TokenInitializeException extends Exception {

  public TokenInitializeException(String message) {
    super(message);
  }

  public TokenInitializeException(String message, Throwable cause) {
    super(message, cause);
  }
}
