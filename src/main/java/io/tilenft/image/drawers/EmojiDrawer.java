package io.tilenft.image.drawers;

import io.tilenft.image.ImageException;
import org.opencv.core.Mat;

public class EmojiDrawer {

  public void drawEmoji(int tileIndex, Mat destImage, Mat emojiSource) throws ImageException {
    switch (tileIndex) {
      case 1:
        emojiSource.copyTo(destImage.rowRange(71, 143).colRange(52, 124));
        break;
      case 2:
        emojiSource.copyTo(destImage.rowRange(71, 143).colRange(226, 298));
        break;
      case 3:
        emojiSource.copyTo(destImage.rowRange(207, 279).colRange(52, 124));
        break;
      case 4:
        emojiSource.copyTo(destImage.rowRange(207, 279).colRange(226, 298));
        break;
      default:
        throw new ImageException("Invalid tile index passed to drawEmoji");
    }
  }
}
