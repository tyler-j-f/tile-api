package com.tylerfitzgerald.demo_api.image;

import org.opencv.core.Mat;

public class EmojiDrawer {

  public void drawEmoji(int tileIndex, Mat destImage, Mat emojiSource) throws ImageException {
    switch (tileIndex) {
      case 1:
        emojiSource.copyTo(destImage.rowRange(89, 161).colRange(52, 124));
        break;
      case 2:
        emojiSource.copyTo(destImage.rowRange(89, 161).colRange(226, 298));
        break;
      case 3:
        emojiSource.copyTo(destImage.rowRange(239, 311).colRange(52, 124));
        break;
      case 4:
        emojiSource.copyTo(destImage.rowRange(239, 311).colRange(226, 298));
        break;
      default:
        throw new ImageException("Invalid tile index passed to drawEmoji");
    }
  }
}
