package io.tilenft.eth.token.traits.weightless.pickers;

public interface WeightlessTraitPickerInterface {

  Object getValue(WeightlessTraitPickerContext context) throws WeightlessTraitPickerException;

  Object getDisplayValue(WeightlessTraitPickerContext context)
      throws WeightlessTraitPickerException;
}
