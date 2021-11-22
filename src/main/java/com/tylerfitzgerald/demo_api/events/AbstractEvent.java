package com.tylerfitzgerald.demo_api.events;

import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class AbstractEvent implements EventInterface {

  protected List<String> topics;
  @Getter protected String transactionHash;

  public AbstractEvent(List<String> topics, String transactionHash) {
    this.topics = topics;
    this.transactionHash = transactionHash;
  }

  @Override
  public String getTopicValue(int valueId) {
    return topics.get(valueId);
  }
}
