package io.tilenft.image;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MetadataSetDTO {
  private String tile1Color;
  private String tile2Color;
  private String tile3Color;
  private String tile4Color;
  private String tile1Emoji;
  private String tile2Emoji;
  private String tile3Emoji;
  private String tile4Emoji;

  public String getEmojiFilename(int tileNumber) throws ImageException {
    String pngFilExtension = ".png";
    switch (tileNumber) {
      case 1:
        return tile1Emoji.toUpperCase() + pngFilExtension;
      case 2:
        return tile2Emoji.toUpperCase() + pngFilExtension;
      case 3:
        return tile3Emoji.toUpperCase() + pngFilExtension;
      case 4:
        return tile4Emoji.toUpperCase() + pngFilExtension;
      default:
        throw new ImageException("Invalid tileNumber: " + tileNumber);
    }
  }
}
