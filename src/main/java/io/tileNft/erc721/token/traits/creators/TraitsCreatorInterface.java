package io.tileNft.erc721.token.traits.creators;

import io.tileNft.erc721.token.initializers.TokenInitializeException;

public interface TraitsCreatorInterface {
  void createTraits(TraitsCreatorContext context) throws TokenInitializeException;
}
