package com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.traitPickers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;
import org.junit.jupiter.api.Test;

public class ColorTraitPickerPickerTest {

  private WeightlessTraitPickerContext context;
  private ColorTraitPickerPicker colorTraitPickerPicker = new ColorTraitPickerPicker();
  private final Long SEED_FOR_TRAITS =
      Long.valueOf(new Random(System.currentTimeMillis()).nextInt());
  private final int NUMBER_OF_TIMES_TO_REPEAT_TEST = 10;

  @Test
  public void testColorTraitPickerPicker() throws WeightlessTraitPickerException {
    mockContext();
    String traitValue = "";
    String traitValuePrevious = "";
    String displayValue = "";
    String displayValuePrevious = "";
    for (int x = 0; x < NUMBER_OF_TIMES_TO_REPEAT_TEST; x++) {
      if (traitValue.equals("")) {
        traitValue = colorTraitPickerPicker.getValue(context);
        displayValue = colorTraitPickerPicker.getDisplayValue(context);
      } else {
        traitValuePrevious = traitValue;
        traitValue = colorTraitPickerPicker.getValue(context);
        assertThat(traitValue).isEqualTo(traitValuePrevious);
        displayValuePrevious = displayValue;
        displayValue = colorTraitPickerPicker.getDisplayValue(context);
        assertThat(displayValue).isEqualTo(displayValuePrevious);
      }
    }
  }

  private WeightlessTraitPickerContext mockContext() {
    context = WeightlessTraitPickerContext.builder().seedForTrait(SEED_FOR_TRAITS).build();
    return context;
  }
}
