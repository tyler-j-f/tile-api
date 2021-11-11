package com.tylerfitzgerald.demo_api.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.core.io.ClassPathResource;

public class Image_Drawer {

  public byte[] drawImage(Long tokenId) throws IOException, Image_Exception {
    Mat tiles = drawTiles(tokenId);
    drawEmojiOnTile(1, tiles, bufferedImage2Mat(loadEmoji("images/1F9D7-1F3FF.png"), "png"));
    drawEmojiOnTile(
        2,
        tiles,
        bufferedImage2Mat(
            loadEmoji("images/1F469-1F3FC-200D-2764-FE0F-200D-1F48B-200D-1F468-1F3FD.png"), "png"));
    drawEmojiOnTile(
        3, tiles, bufferedImage2Mat(loadEmoji("images/1F926-1F3FE-200D-2642-FE0F.png"), "png"));
    drawEmojiOnTile(4, tiles, bufferedImage2Mat(loadEmoji("images/E329.png"), "png"));
    return getBufferedImageFromMat(tiles);
  }

  private Mat drawTiles(Long tokenId) {
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

  private void drawEmojiOnTile(int tileIndex, Mat destImage, Mat emojiSource)
      throws Image_Exception {
    switch (tileIndex) {
      case 1:
        emojiSource.copyTo(destImage.rowRange(89, 161).colRange(52, 124));
        break;
      case 2:
        emojiSource.copyTo(destImage.rowRange(89, 161).colRange(226, 298));
        break;
      case 3:
        emojiSource.copyTo(destImage.rowRange(239, 311).colRange(52, 124));
        break;
      case 4:
        emojiSource.copyTo(destImage.rowRange(239, 311).colRange(226, 298));
        break;
      default:
        throw new Image_Exception("Invalid tile index passed to drawEmojiOnTile");
    }
  }

  private Mat bufferedImage2Mat(BufferedImage image, String fileType) throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    ImageIO.write(image, fileType, byteArrayOutputStream);
    byteArrayOutputStream.flush();
    return Imgcodecs.imdecode(
        new MatOfByte(byteArrayOutputStream.toByteArray()), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);
  }

  private byte[] getBufferedImageFromMat(Mat matrix) {
    MatOfByte byteMat = new MatOfByte();
    Imgcodecs.imencode(".png", matrix, byteMat);
    byte ba[] = byteMat.toArray();
    return ba;
  }

  private BufferedImage loadEmoji(String filePath) throws IOException {
    ClassPathResource imgFile = new ClassPathResource(filePath);
    return ImageIO.read(imgFile.getInputStream());
  }
}
