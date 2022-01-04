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
}
