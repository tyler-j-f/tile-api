package com.tylerfitzgerald.demo_api.image;

import java.io.IOException;
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

  public byte[] drawImage(Long tokenId, Long rarityScore, Resource[] resources)
      throws IOException, ImageException {
    Mat tiles = tilesDrawer.drawTiles(tokenId);
    titleDrawer.drawTitle(tiles, tokenId);
    int x = 1;
    for (Resource resource : resources) {
      emojiDrawer.drawEmoji(x++, tiles, emojiLoader.loadEmojiMat(resource));
    }
    subTitleDrawer.drawSubTitle(tiles, rarityScore);
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
