package io.tilenft.eth.token.traits.creators;

import io.tilenft.eth.token.initializers.TokenInitializeException;

public interface TraitsCreatorInterface {
  void createTraits(TraitsCreatorContext context) throws TokenInitializeException;
}
