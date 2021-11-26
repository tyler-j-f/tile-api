package com.tylerfitzgerald.demo_api.ethEvents.events;

import java.util.List;

public abstract class AbstractSingleTokenEvent extends AbstractEvent {

  public AbstractSingleTokenEvent(List<String> topics, String transactionHash) {
    super(topics, transactionHash);
  }

  public String getTokenId() {
    return getTopicValue(1);
  }
}
