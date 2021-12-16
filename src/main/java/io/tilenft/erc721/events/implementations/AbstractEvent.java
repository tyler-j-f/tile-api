package io.tilenft.erc721.events.implementations;

import java.util.List;
import lombok.Getter;

public abstract class AbstractEvent implements EventInterface {

  private List<String> topics;
  @Getter private String transactionHash;

  public AbstractEvent(List<String> topics, String transactionHash) {
    this.topics = topics;
    this.transactionHash = transactionHash;
  }

  @Override
  public String getTopicValue(int valueId) {
    return topics.get(valueId);
  }
}
