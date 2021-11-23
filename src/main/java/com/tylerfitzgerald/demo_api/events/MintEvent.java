package com.tylerfitzgerald.demo_api.events;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class MintEvent extends AbstractEvent {

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
