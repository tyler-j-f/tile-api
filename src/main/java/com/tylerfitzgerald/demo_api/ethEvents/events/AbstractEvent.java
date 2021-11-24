package com.tylerfitzgerald.demo_api.ethEvents.events;

import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class AbstractEvent implements EventInterface {

  private List<String> topics;
  @Getter private String transactionHash;

  public AbstractEvent(List<String> topics, String transactionHash) {
    this.topics = topics;
    this.transactionHash = transactionHash;
  }

  public String getTokenId() {
    return getTopicValue(1);
  }

  public String getTokenId(int topicValue) {
    return getTopicValue(topicValue);
  }

  @Override
  public String getTopicValue(int valueId) {
    return topics.get(valueId);
  }
}