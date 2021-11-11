package com.tylerfitzgerald.demo_api.image;

import java.text.NumberFormat;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Autowired;

public class TitleDrawer {

  @Autowired private NumberFormat numberFormat;

  public Mat drawTitle(Mat src, Long tokenId) {
    Imgproc.putText(
        src,
        "Tile #" + numberFormat.format(tokenId),
        new Point(20, 27),
        Core.FONT_HERSHEY_COMPLEX,
        1,
        new Scalar(0, 0, 0, 255));
    return src;
  }
}
