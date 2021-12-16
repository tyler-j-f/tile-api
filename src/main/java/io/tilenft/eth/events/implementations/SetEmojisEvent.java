package io.tilenft.eth.events.implementations;

import java.util.List;

public class SetEmojisEvent extends AbstractSingleTokenEvent {

  public SetEmojisEvent(List<String> topics, String transactionHash) {
    super(topics, transactionHash);
  }

  public String getEmojis() {
    return getTopicValue(2);
  }

  @Override
  public String toString() {
    return "SetEmojisEvent{"
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
