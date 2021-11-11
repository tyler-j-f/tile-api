package com.tylerfitzgerald.demo_api.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.core.io.ClassPathResource;

public class EmojiLoader {

  public BufferedImage loadEmojiImage(String filePath) throws IOException {
    ClassPathResource imgFile = new ClassPathResource(filePath);
    return ImageIO.read(imgFile.getInputStream());
  }

  public Mat loadEmojiMat(String emojiFilePath, String fileType) throws IOException {
    return bufferedImage2Mat(loadEmojiImage(emojiFilePath), fileType);
  }

  public Mat loadEmojiMat(String emojiFilePath) throws IOException {
    return loadEmojiMat(emojiFilePath, "png");
  }

  private Mat bufferedImage2Mat(BufferedImage image, String fileType) throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    ImageIO.write(image, fileType, byteArrayOutputStream);
    byteArrayOutputStream.flush();
    return Imgcodecs.imdecode(
        new MatOfByte(byteArrayOutputStream.toByteArray()), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);
  }
}
