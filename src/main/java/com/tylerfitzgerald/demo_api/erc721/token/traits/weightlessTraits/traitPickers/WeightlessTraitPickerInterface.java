package com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.traitPickers;

public interface WeightlessTraitPickerInterface {

  Object getValue(WeightlessTraitPickerContext context) throws WeightlessTraitPickerException;

  Object getDisplayValue(WeightlessTraitPickerContext context)
      throws WeightlessTraitPickerException;
}
