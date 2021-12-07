package com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits;

public interface WeightlessTraitInterface {

  Object getValue(WeightlessTraitContext context) throws WeightlessTraitException;

  Object getDisplayValue(WeightlessTraitContext context) throws WeightlessTraitException;
}
