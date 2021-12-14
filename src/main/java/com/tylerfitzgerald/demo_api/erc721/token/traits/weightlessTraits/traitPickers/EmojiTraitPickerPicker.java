package com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.traitPickers;

import com.tylerfitzgerald.demo_api.image.ImageResourcesLoader;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;

public class EmojiTraitPickerPicker implements WeightlessTraitPickerInterface {

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
