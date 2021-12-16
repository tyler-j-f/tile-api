package io.tileNft.image.drawers;

import java.text.NumberFormat;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Autowired;

public class SubTitleDrawer {

  @Autowired private NumberFormat numberFormat;

  public Mat drawSubTitle(Mat src, Long score) {
    Imgproc.putText(
        src,
        "Rarity: " + numberFormat.format(score),
        new Point(20, 335),
        Core.FONT_HERSHEY_COMPLEX_SMALL,
        1,
        new Scalar(0, 0, 0, 255),
        1);
    return src;
  }
}
