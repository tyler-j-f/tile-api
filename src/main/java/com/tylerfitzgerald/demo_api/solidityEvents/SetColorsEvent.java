package com.tylerfitzgerald.demo_api.solidityEvents;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class SetColorsEvent extends AbstractEvent {

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
