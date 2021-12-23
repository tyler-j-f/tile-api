package io.tilenft.eth.events.implementations;

import java.util.List;

public class SetMetadataEvent extends AbstractSingleTokenEvent {

  public SetMetadataEvent(List<String> topics, String transactionHash) {
    super(topics, transactionHash);
  }

  public String getMetadataToSetIndex() {
    return getTopicValue(2);
  }

  public String getMetadataToSet() {
    return getTopicValue(3);
  }

  @Override
  public String toString() {
    return "SetMetadataEvent{"
        + "tokenId="
        + getTokenId()
        + ", "
        + "metadataToSetIndex="
        + getMetadataToSetIndex()
        + ", "
        + "metadataToSet="
        + getMetadataToSet()
        + ", "
        + "transactionHash="
        + getTransactionHash()
        + '}';
  }
}
