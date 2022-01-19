package io.tilenft.image.drawers;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

public class BackgroundDrawer {

  public Mat drawBackground() {
    Mat src = new Mat(700, 700, CvType.CV_8UC4);
    // Set background white
    src.setTo(new Scalar(255, 255, 255, 255));
    return src;
  }

  public Mat drawTileOnBackground(Mat background, Mat tiles) {
    tiles.copyTo(background.rowRange(175, 525).colRange(175, 525));
    return background;
  }
}
