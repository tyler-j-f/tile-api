package com.tylerfitzgerald.demo_api.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.core.io.ClassPathResource;

public class ImageDrawer {

  public byte[] drawImage(Long tokenId) throws IOException, ImageException {
    Mat tiles = (new TilesDrawer()).drawTiles(tokenId);
    EmojiDrawer emojiDrawer = new EmojiDrawer();
    emojiDrawer.drawEmoji(1, tiles, bufferedImage2Mat(loadEmoji("images/1F9D7-1F3FF.png"), "png"));
    emojiDrawer.drawEmoji(
        2,
        tiles,
        bufferedImage2Mat(
            loadEmoji("images/1F469-1F3FC-200D-2764-FE0F-200D-1F48B-200D-1F468-1F3FD.png"), "png"));
    emojiDrawer.drawEmoji(
        3, tiles, bufferedImage2Mat(loadEmoji("images/1F926-1F3FE-200D-2642-FE0F.png"), "png"));
    emojiDrawer.drawEmoji(4, tiles, bufferedImage2Mat(loadEmoji("images/E329.png"), "png"));
    return getBufferedImageFromMat(tiles);
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
