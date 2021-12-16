package io.tileNft.erc721.token.initializers;

public class TokenInitializeException extends Exception {

  public TokenInitializeException(String message) {
    super(message);
  }

  public TokenInitializeException(String message, Throwable cause) {
    super(message, cause);
  }
}
