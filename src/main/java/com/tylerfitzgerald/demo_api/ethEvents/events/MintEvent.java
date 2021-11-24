package com.tylerfitzgerald.demo_api.ethEvents.events;

import java.util.List;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class MintEvent extends AbstractEvent {

  public MintEvent(List<String> topics, String transactionHash) {
    super(topics, transactionHash);
  }

  @Override
  public String toString() {
    return "MintEvent{"
        + "tokenId="
        + getTokenId()
        + ", "
        + "transactionHash="
        + getTransactionHash()
        + '}';
  }
}
