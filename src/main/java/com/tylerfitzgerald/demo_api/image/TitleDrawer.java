package com.tylerfitzgerald.demo_api.image;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class TitleDrawer {
  public Mat drawTitle(Mat src, Long tokenId) {
    Imgproc.putText(
        src,
        "Tile #" + tokenId,
        new Point(20, 27),
        Core.FONT_HERSHEY_COMPLEX,
        1,
        new Scalar(0, 0, 0, 255));
    return src;
  }
}
