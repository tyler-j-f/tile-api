package io.tileNft.erc721.token.traits.weightlessTraits.traitPickers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;
import org.junit.jupiter.api.Test;

public class ColorTraitPickerTest {

  private WeightlessTraitPickerContext context;
  private ColorTraitPicker colorTraitPicker = new ColorTraitPicker();
  private final Long SEED_FOR_TRAITS =
      Long.valueOf(new Random(System.currentTimeMillis()).nextInt());
  private final int NUMBER_OF_TIMES_TO_REPEAT_TEST = 10;

  @Test
  public void testColorTraitPicker() throws WeightlessTraitPickerException {
    mockContext();
    String traitValue = "";
    String traitValuePrevious = "";
    String displayValue = "";
    String displayValuePrevious = "";
    for (int x = 0; x < NUMBER_OF_TIMES_TO_REPEAT_TEST; x++) {
      if (traitValue.equals("")) {
        traitValue = colorTraitPicker.getValue(context);
        displayValue = colorTraitPicker.getDisplayValue(context);
      } else {
        traitValuePrevious = traitValue;
        traitValue = colorTraitPicker.getValue(context);
        assertThat(traitValue).isEqualTo(traitValuePrevious);
        displayValuePrevious = displayValue;
        displayValue = colorTraitPicker.getDisplayValue(context);
        assertThat(displayValue).isEqualTo(displayValuePrevious);
      }
    }
  }

  private WeightlessTraitPickerContext mockContext() {
    context = WeightlessTraitPickerContext.builder().seedForTrait(SEED_FOR_TRAITS).build();
    return context;
  }
}
