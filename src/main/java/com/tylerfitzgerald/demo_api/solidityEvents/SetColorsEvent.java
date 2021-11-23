package com.tylerfitzgerald.demo_api.solidityEvents;

import java.util.List;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class SetColorsEvent extends AbstractEvent {

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
