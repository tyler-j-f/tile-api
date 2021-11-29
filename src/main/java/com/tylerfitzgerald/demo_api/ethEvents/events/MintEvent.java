package com.tylerfitzgerald.demo_api.ethEvents.events;

import java.util.List;

public class MintEvent extends AbstractSingleTokenEvent {

  public MintEvent(List<String> topics, String transactionHash) {
    super(topics, transactionHash);
  }

  public String getFromAddress() {
    return getTopicValue(1);
  }

  public String getToAddress() {
    return getTopicValue(2);
  }

  @Override
  public String getTokenId() {
    return getTopicValue(3);
  }

  @Override
  public String toString() {
    return "MintEvent{"
        + "tokenId="
        + getTokenId()
        + ", "
        + "fromAddress="
        + getFromAddress()
        + ", "
        + "toAddress="
        + getToAddress()
        + ", "
        + "transactionHash="
        + getTransactionHash()
        + '}';
  }
}
