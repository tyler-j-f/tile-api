package io.tileNft.erc721.token.traits.weightlessTraits.traitPickers;

import static org.assertj.core.api.Assertions.assertThat;

import io.tileNft.image.ImageResourcesLoader;
import java.io.IOException;
import java.util.Random;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

@SpringBootTest
public class EmojiTraitPickerTest {
  private WeightlessTraitPickerContext context;
  private final Long SEED_FOR_TRAITS =
      Long.valueOf(new Random(System.currentTimeMillis()).nextInt());
  private final int NUMBER_OF_TIMES_TO_REPEAT_TEST = 10;
  private String MOCK_FILENAME = "A";
  private Resource resource;
  @Mock private ImageResourcesLoader imageResourcesLoader;
  @InjectMocks private EmojiTraitPicker emojiTraitPicker = new EmojiTraitPicker();

  @Test
  public void testEmojiTraitPicker() throws WeightlessTraitPickerException, IOException {
    String traitValue = "";
    String traitValuePrevious = "";
    String displayValue = "";
    String displayValuePrevious = "";
    mockContext();
    mockResource();
    mockForImageResourcesLoader();
    for (int x = 0; x < NUMBER_OF_TIMES_TO_REPEAT_TEST; x++) {
      if (traitValue.equals("")) {
        traitValue = emojiTraitPicker.getValue(context);
        displayValue = emojiTraitPicker.getDisplayValue(context);
      } else {
        traitValuePrevious = traitValue;
        traitValue = emojiTraitPicker.getValue(context);
        assertThat(traitValue).isEqualTo(traitValuePrevious);
        displayValuePrevious = displayValue;
        displayValue = emojiTraitPicker.getDisplayValue(context);
        assertThat(displayValue).isEqualTo(displayValuePrevious);
      }
    }
    assertForTest(traitValue, displayValue);
  }

  private Resource mockResource() {
    resource = Mockito.mock(Resource.class);
    Mockito.when(resource.getFilename()).thenReturn(MOCK_FILENAME);
    return resource;
  }

  private void mockForImageResourcesLoader() throws IOException {
    Mockito.when(imageResourcesLoader.getRandomResource(Mockito.any())).thenReturn(resource);
  }

  private void assertForTest(String finalTraitValue, String finalDisplayValue) throws IOException {
    Mockito.verify(imageResourcesLoader, Mockito.times(NUMBER_OF_TIMES_TO_REPEAT_TEST))
        .getRandomResource(Mockito.any());
    assertThat(finalTraitValue).isEqualTo(MOCK_FILENAME);
    assertThat(finalDisplayValue).isEqualTo("");
  }

  private WeightlessTraitPickerContext mockContext() {
    context = WeightlessTraitPickerContext.builder().seedForTrait(SEED_FOR_TRAITS).build();
    return context;
  }
}
