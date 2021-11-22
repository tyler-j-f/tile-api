package com.tylerfitzgerald.demo_api.events;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class SetColorsEvent extends AbstractEvent {

  public String getTokenId() {
    return getTopicValue(1);
  }

  public String getColors() {
    return getTopicValue(2);
  }

  @Override
  public String toString() {
    return "MintEvent{"
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
