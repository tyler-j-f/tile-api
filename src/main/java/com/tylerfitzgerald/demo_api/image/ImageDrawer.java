package com.tylerfitzgerald.demo_api.image;

import java.io.IOException;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

public class ImageDrawer {

  public byte[] drawImage(Long tokenId) throws IOException, ImageException {
    Mat tiles = (new TilesDrawer()).drawTiles(tokenId);
    EmojiLoader emojiLoader = new EmojiLoader();
    EmojiDrawer emojiDrawer = new EmojiDrawer();
    emojiDrawer.drawEmoji(1, tiles, emojiLoader.loadEmojiMat("images/1F9D7-1F3FF.png"));
    emojiDrawer.drawEmoji(
        2,
        tiles,
        emojiLoader.loadEmojiMat(
            "images/1F469-1F3FC-200D-2764-FE0F-200D-1F48B-200D-1F468-1F3FD.png"));
    emojiDrawer.drawEmoji(
        3, tiles, emojiLoader.loadEmojiMat("images/1F926-1F3FE-200D-2642-FE0F.png"));
    emojiDrawer.drawEmoji(4, tiles, emojiLoader.loadEmojiMat("images/E329.png"));
    return getBufferedImageFromMat(tiles);
  }

  private byte[] getBufferedImageFromMat(Mat matrix) {
    MatOfByte byteMat = new MatOfByte();
    Imgcodecs.imencode(".png", matrix, byteMat);
    byte ba[] = byteMat.toArray();
    return ba;
  }
}
