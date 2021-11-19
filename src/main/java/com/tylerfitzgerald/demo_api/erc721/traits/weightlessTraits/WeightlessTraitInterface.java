package com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits;

public interface WeightlessTraitInterface {

  Object getValue(WeightlessTraitContext context) throws WeightlessTraitException;

  Object getDisplayValue(Long seedForTrait) throws WeightlessTraitException;
}
