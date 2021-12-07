package com.tylerfitzgerald.demo_api.erc721.ethEvents.events;

import java.util.List;

public class MergeEvent extends AbstractEvent {

  public MergeEvent(List<String> topics, String transactionHash) {
    super(topics, transactionHash);
  }

  public String getBurnedToken1Id() {
    return getTopicValue(1);
  }

  public String getBurnedToken2Id() {
    return getTopicValue(2);
  }

  public String getNewTokenId() {
    return getTopicValue(3);
  }

  @Override
  public String toString() {
    return "MergeEvent{"
        + "tokenId="
        + getBurnedToken1Id()
        + ", "
        + "transactionHash="
        + getTransactionHash()
        + '}';
  }
}
