package com.tylerfitzgerald.demo_api.events;

import lombok.Builder;

import java.util.List;
import lombok.Getter;

@Builder
public class MintEvent {
  private List<String> topics;

  @Getter private String transactionHash;

  public String getSaleOptionSupplyPostMint() {
    return topics.get(1);
  }

  public String getSaleOptionId() {
    return topics.get(2);
  }

  public String getTokenId() {
    return topics.get(3);
  }

  @Override
  public String toString() {
    return "MintEvent{"
        + "tokenId="
        + topics.get(3)
        + ", "
        + "saleOptionId="
        + topics.get(2)
        + ", "
        + "saleOptionSupplyPostMint="
        + topics.get(1)
        + ","
        + "transactionHash="
        + transactionHash
        + '}';
  }
}
