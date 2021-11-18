package com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits;

import com.tylerfitzgerald.demo_api.image.ImageResourcesLoader;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;

public class EmojiTraitPicker implements WeightlessTraitInterface {

  @Autowired private ImageResourcesLoader imageResourcesLoader;

  @Override
  public String getValue(Long seedForTrait) throws WeightlessTraitException {
    try {
      return stripExtension(imageResourcesLoader.getRandomResource(seedForTrait).getFilename());
    } catch (IOException e) {
      throw new WeightlessTraitException(e.getMessage(), e.getCause());
    }
  }

  private String stripExtension(String fileName) {
    return stripExtension(fileName, ".png");
  }

  private String stripExtension(String fileName, String extension) {
    return fileName.replace(extension, "");
  }

  @Override
  public String getDisplayValue(Long seedForTrait) throws WeightlessTraitException {
    return "";
  }
}
