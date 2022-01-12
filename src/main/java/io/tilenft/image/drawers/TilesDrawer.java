package io.tilenft.image.drawers;

import java.util.List;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class TilesDrawer {

  public Mat drawTiles(List<String> tileColors) {
    Mat src = new Mat(350, 350, CvType.CV_8UC4);
    // Set background white
    src.setTo(new Scalar(255, 255, 255, 255));
    // Top left square, blue
    Imgproc.rectangle(
        src,
        new Point(0, 38),
        new Point(175, 175),
        new Scalar(
            getRGBValue(tileColors, 2),
            getRGBValue(tileColors, 1),
            getRGBValue(tileColors, 0),
            255),
        -1);
    Imgproc.rectangle(src, new Point(0, 38), new Point(175, 175), new Scalar(0, 0, 0, 255), 1);
    // Top right square, green
    Imgproc.rectangle(
        src,
        new Point(175, 38),
        new Point(350, 175),
        new Scalar(
            getRGBValue(tileColors, 5),
            getRGBValue(tileColors, 4),
            getRGBValue(tileColors, 3),
            255),
        -1);
    Imgproc.rectangle(src, new Point(175, 38), new Point(350, 175), new Scalar(0, 0, 0, 255), 1);
    // Bottom left square, red
    Imgproc.rectangle(
        src,
        new Point(0, 175),
        new Point(175, 312),
        new Scalar(
            getRGBValue(tileColors, 8),
            getRGBValue(tileColors, 7),
            getRGBValue(tileColors, 6),
            255),
        -1);
    Imgproc.rectangle(src, new Point(0, 175), new Point(175, 312), new Scalar(0, 0, 0, 255), 1);
    // Bottom right square, yellow
    Imgproc.rectangle(
        src,
        new Point(175, 175),
        new Point(350, 312),
        new Scalar(
            getRGBValue(tileColors, 11),
            getRGBValue(tileColors, 10),
            getRGBValue(tileColors, 9),
            255),
        -1);
    Imgproc.rectangle(src, new Point(175, 175), new Point(350, 312), new Scalar(0, 0, 0, 255), 1);
    // Draw a border
    Imgproc.rectangle(src, new Point(0, 0), new Point(349, 349), new Scalar(0, 0, 0, 255), 1);
    return src;
  }

  private int getRGBValue(List<String> tileColors, int index) {
    return Integer.parseInt(tileColors.get(index));
  }
}
