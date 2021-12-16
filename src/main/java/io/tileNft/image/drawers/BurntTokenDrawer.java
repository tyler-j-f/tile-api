package io.tileNft.image.drawers;

import java.text.NumberFormat;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Autowired;

public class BurntTokenDrawer {

  @Autowired private NumberFormat numberFormat;

  public Mat drawBurntTokenText(Mat src) {
    Imgproc.putText(
        src,
        "BURNT TOKEN!",
        new Point(55, 185),
        Core.FONT_HERSHEY_TRIPLEX,
        1,
        new Scalar(0, 0, 255, 255),
        1);
    return src;
  }
}
