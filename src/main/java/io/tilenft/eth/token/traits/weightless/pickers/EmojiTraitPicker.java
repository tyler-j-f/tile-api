package io.tilenft.eth.token.traits.weightless.pickers;

import io.tilenft.image.ImageResourcesLoader;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;

public class EmojiTraitPicker implements WeightlessTraitPickerInterface {

  @Autowired private ImageResourcesLoader imageResourcesLoader;

  @Override
  public String getValue(WeightlessTraitPickerContext context)
      throws WeightlessTraitPickerException {
    try {
      return stripExtension(
          imageResourcesLoader.getRandomResource(context.getSeedForTrait()).getFilename());
    } catch (IOException e) {
      throw new WeightlessTraitPickerException(e.getMessage(), e.getCause());
    }
  }

  public String stripExtension(String fileName) {
    return stripExtension(fileName, ".png");
  }

  private String stripExtension(String fileName, String extension) {
    return fileName.replace(extension, "");
  }

  @Override
  public String getDisplayValue(WeightlessTraitPickerContext context)
      throws WeightlessTraitPickerException {
    return "";
  }
}