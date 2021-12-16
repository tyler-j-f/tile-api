package io.tileNft.erc721.ethEvents.events;

import java.util.List;

public class MintEvent extends AbstractSingleTokenEvent {

  public MintEvent(List<String> topics, String transactionHash) {
    super(topics, transactionHash);
  }

  @Override
  public String toString() {
    return "MintEvent{"
        + "tokenId="
        + getTokenId()
        + ", "
        + "transactionHash="
        + getTransactionHash()
        + '}';
  }
}
