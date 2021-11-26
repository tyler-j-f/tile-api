package com.tylerfitzgerald.demo_api.ethEvents.events;

import java.util.List;

public class SetColorsEvent extends AbstractSingleTokenEvent {

  public SetColorsEvent(List<String> topics, String transactionHash) {
    super(topics, transactionHash);
  }

  public String getColors() {
    return getTopicValue(2);
  }

  @Override
  public String toString() {
    return "SetColorsEvent{"
        + "tokenId="
        + getTokenId()
        + ", "
        + "colors="
        + getColors()
        + ", "
        + "transactionHash="
        + getTransactionHash()
        + '}';
  }
}
