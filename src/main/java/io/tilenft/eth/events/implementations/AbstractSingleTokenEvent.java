package io.tilenft.eth.events.implementations;

import java.util.List;

public abstract class AbstractSingleTokenEvent extends AbstractEvent {

  public AbstractSingleTokenEvent(List<String> topics, String transactionHash) {
    super(topics, transactionHash);
  }

  public String getTokenId() {
    return getTopicValue(1);
  }
}
