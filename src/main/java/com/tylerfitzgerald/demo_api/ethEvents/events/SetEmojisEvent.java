package com.tylerfitzgerald.demo_api.ethEvents.events;

import java.util.List;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class SetEmojisEvent extends AbstractEvent {

  public SetEmojisEvent(List<String> topics, String transactionHash) {
    super(topics, transactionHash);
  }

  public String getEmojis() {
    return getTopicValue(2);
  }

  @Override
  public String toString() {
    return "SetColorsEvent{"
        + "tokenId="
        + getTokenId()
        + ", "
        + "emojis="
        + getEmojis()
        + ", "
        + "transactionHash="
        + getTransactionHash()
        + '}';
  }
}
