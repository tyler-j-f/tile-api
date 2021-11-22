package com.tylerfitzgerald.demo_api.events;

import lombok.Builder;

import java.util.List;
import lombok.Getter;

@Builder
public class MintEvent {
  private List<String> topics;

  @Getter private String transactionHash;

  public String getTokenId() {
    return topics.get(1);
  }

  @Override
  public String toString() {
    return "MintEvent{"
        + "tokenId="
        + topics.get(0)
        + ", "
        + "transactionHash="
        + transactionHash
        + '}';
  }
}
