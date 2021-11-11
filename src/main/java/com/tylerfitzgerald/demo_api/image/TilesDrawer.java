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
    // Set background white
    src.setTo(new Scalar(255, 255, 255, 0));
    // Top left square, blue
    Imgproc.rectangle(src, new Point(0, 38), new Point(175, 175), new Scalar(255, 0, 0, 255), -1);
    Imgproc.rectangle(src, new Point(0, 38), new Point(175, 175), new Scalar(0, 0, 0, 255), 1);
    // Top right square, green
    Imgproc.rectangle(src, new Point(175, 38), new Point(350, 175), new Scalar(0, 102, 0, 255), -1);
    Imgproc.rectangle(src, new Point(175, 38), new Point(350, 175), new Scalar(0, 0, 0, 255), 1);
    //    // Bottom left square, red
    Imgproc.rectangle(src, new Point(0, 175), new Point(175, 312), new Scalar(0, 0, 255, 255), -1);
    Imgproc.rectangle(src, new Point(0, 175), new Point(175, 312), new Scalar(0, 0, 0, 255), 1);
    //    // Bottom right square, yellow
    Imgproc.rectangle(
        src, new Point(175, 175), new Point(350, 312), new Scalar(102, 255, 255, 255), -1);
    Imgproc.rectangle(src, new Point(175, 175), new Point(350, 312), new Scalar(0, 0, 0, 255), 1);
    // Draw a border
    Imgproc.rectangle(src, new Point(0, 0), new Point(349, 349), new Scalar(0, 0, 0, 255), 1);
    //    // Draw title
    Imgproc.putText(
        src,
        "Tile #" + tokenId,
        new Point(20, 20),
        Core.FONT_HERSHEY_COMPLEX,
        1,
        new Scalar(0, 0, 0, 255));
    return src;
  }
}
