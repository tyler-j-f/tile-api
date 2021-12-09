package com.tylerfitzgerald.demo_api.erc721.token.traits.creators;

import com.tylerfitzgerald.demo_api.erc721.token.initializers.TokenInitializeException;

public interface TraitsCreatorInterface {
  void createTraits(TraitsCreatorContext context) throws TokenInitializeException;
}
