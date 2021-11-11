package com.tylerfitzgerald.demo_api.image;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class TilesDrawer {

  public Mat drawTiles(Long tokenId) {
    Mat src = new Mat(350, 350, CvType.CV_8UC4);
    src.setTo(new Scalar(255, 255, 255, 0));
    // Top left square, blue
    Imgproc.rectangle(src, new Point(0, 50), new Point(175, 200), new Scalar(255, 0, 0, 255), -1);
    Imgproc.rectangle(src, new Point(0, 50), new Point(175, 200), new Scalar(0, 0, 0, 255), 3);
    // Top right square, green
    Imgproc.rectangle(src, new Point(175, 50), new Point(350, 200), new Scalar(0, 102, 0, 255), -1);
    Imgproc.rectangle(src, new Point(175, 50), new Point(350, 200), new Scalar(0, 0, 0, 255), 3);
    //    // Bottom left square, red
    Imgproc.rectangle(src, new Point(0, 200), new Point(175, 350), new Scalar(0, 0, 255, 255), -1);
    Imgproc.rectangle(src, new Point(0, 200), new Point(175, 350), new Scalar(0, 0, 0, 255), 3);
    //    // Bottom right square, yellow
    Imgproc.rectangle(
        src, new Point(175, 200), new Point(350, 350), new Scalar(102, 255, 255, 255), -1);
    Imgproc.rectangle(src, new Point(175, 200), new Point(350, 350), new Scalar(0, 0, 0, 255), 3);
    //    // Draw title
    Imgproc.putText(
        src,
        "Tile #" + tokenId,
        new Point(20, 30),
        Core.FONT_HERSHEY_COMPLEX,
        1,
        new Scalar(0, 0, 0, 255));
    return src;
  }
}
