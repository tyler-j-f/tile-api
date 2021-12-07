package com.tylerfitzgerald.demo_api.image.drawers;

import com.tylerfitzgerald.demo_api.image.EmojiLoader;
import com.tylerfitzgerald.demo_api.image.ImageException;
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
      Resource[] emojiiResources,
      List<String> tileColors,
      boolean isBurntToken)
      throws IOException, ImageException {
    Mat tiles = tilesDrawer.drawTiles(tileColors);
    titleDrawer.drawTitle(tiles, tokenId);
    int x = 1;
    for (Resource emojiiResource : emojiiResources) {
      emojiDrawer.drawEmoji(x++, tiles, emojiLoader.loadEmojiMat(emojiiResource));
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
