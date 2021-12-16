package io.tilenft.erc721.token.traits.weightless.traitPickers;

public interface WeightlessTraitPickerInterface {

  Object getValue(WeightlessTraitPickerContext context) throws WeightlessTraitPickerException;

  Object getDisplayValue(WeightlessTraitPickerContext context)
      throws WeightlessTraitPickerException;
}
