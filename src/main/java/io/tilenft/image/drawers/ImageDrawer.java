package io.tilenft.image.drawers;

import io.tilenft.image.EmojiLoader;
import io.tilenft.image.ImageException;
import java.io.IOException;
import java.util.List;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

public class ImageDrawer {

  @Autowired EmojiDrawer emojiDrawer;
  @Autowired EmojiLoader emojiLoader;
  @Autowired TilesDrawer tilesDrawer;
  @Autowired TitleDrawer titleDrawer;
  @Autowired SubTitleDrawer subTitleDrawer;
  @Autowired BackgroundDrawer backgroundDrawer;
  @Autowired BurntTokenDrawer burntTokenDrawer;

  public byte[] drawImage(
      Long tokenId,
      Long rarityScore,
      Resource[] emojiResources,
      List<String> tileColors,
      boolean isBurntToken)
      throws IOException, ImageException {
    return drawImage(tokenId, rarityScore, emojiResources, tileColors, isBurntToken, false);
  }

  public byte[] drawImage(
      Long tokenId,
      Long rarityScore,
      Resource[] emojiResources,
      List<String> tileColors,
      boolean isBurntToken,
      boolean shouldDrawABackground)
      throws IOException, ImageException {
    Mat tiles = drawBaseTiles(tileColors);
    if (tiles == null) {
      return new byte[0];
    }
    titleDrawer.drawTitle(tiles, tokenId);
    int x = 1;
    for (Resource emojiResource : emojiResources) {
      emojiDrawer.drawEmoji(x++, tiles, emojiLoader.loadEmojiMat(emojiResource));
    }
    subTitleDrawer.drawSubTitle(tiles, rarityScore);
    if (isBurntToken) {
      burntTokenDrawer.drawBurntTokenText(tiles);
    }
    if (shouldDrawABackground) {
      Mat background = backgroundDrawer.drawBackground();
      tiles = backgroundDrawer.drawTileOnBackground(background, tiles);
    }
    return getBufferedImageFromMat(tiles);
  }

  private Mat drawBaseTiles(List<String> tileColors) {
    try {
      return tilesDrawer.drawTiles(tileColors);
    } catch (Exception e) {
      System.out.println(
          "ImageDrawer -> drawImage Failure. Invalid RGB color values were most likely passed.");
      System.out.println(e);
      return null;
    }
  }

  private byte[] getBufferedImageFromMat(Mat matrix) {
    return getBufferedImageFromMat(matrix, ".png");
  }

  private byte[] getBufferedImageFromMat(Mat matrix, String filetype) {
    MatOfByte byteMat = new MatOfByte();
    Imgcodecs.imencode(filetype, matrix, byteMat);
    byte ba[] = byteMat.toArray();
    return ba;
  }
}
