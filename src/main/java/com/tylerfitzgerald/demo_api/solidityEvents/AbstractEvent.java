package com.tylerfitzgerald.demo_api.solidityEvents;

import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class AbstractEvent implements EventInterface {

  private List<String> topics;
  @Getter private String transactionHash;

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
