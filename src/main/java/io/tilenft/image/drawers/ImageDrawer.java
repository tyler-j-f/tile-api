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
  @Autowired BurntTokenDrawer burntTokenDrawer;

  public byte[] drawImage(
      Long tokenId,
      Long rarityScore,
      Resource[] emojiResources,
      List<String> tileColors,
      boolean isBurntToken)
      throws IOException, ImageException {
    Mat tiles = tilesDrawer.drawTiles(tileColors);
    titleDrawer.drawTitle(tiles, tokenId);
    int x = 1;
    for (Resource emojiResource : emojiResources) {
      emojiDrawer.drawEmoji(x++, tiles, emojiLoader.loadEmojiMat(emojiResource));
    }
    subTitleDrawer.drawSubTitle(tiles, rarityScore);
    if (isBurntToken) {
      burntTokenDrawer.drawBurntTokenText(tiles);
    }
    return getBufferedImageFromMat(tiles);
  }

  private byte[] getBufferedImageFromMat(Mat matrix) {
    return getBufferedImageFromMat(matrix, ".png");
  }

  private byte[] getBufferedImageFromMat(Mat matrix, String filetype) {
    MatOfByte byteMat = new MatOfByte();
    Imgcodecs.imencode(".png", matrix, byteMat);
    byte ba[] = byteMat.toArray();
    return ba;
  }
}
