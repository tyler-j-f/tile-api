package com.tylerfitzgerald.demo_api.image;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class SubTitleDrawer {
  public Mat drawSubTitle(Mat src, Long score) {
    Imgproc.putText(
        src,
        "Rarity Score : " + score,
        new Point(20, 340),
        Core.FONT_HERSHEY_COMPLEX,
        1,
        new Scalar(0, 0, 0, 255),
        1);
    return src;
  }
}
