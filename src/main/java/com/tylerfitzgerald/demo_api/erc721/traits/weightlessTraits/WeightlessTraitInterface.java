package com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits;

public interface WeightlessTraitInterface {

  Object getValue(Long seedForTrait) throws WeightlessTraitException;

  Object getDisplayValue(Long seedForTrait) throws WeightlessTraitException;
}
