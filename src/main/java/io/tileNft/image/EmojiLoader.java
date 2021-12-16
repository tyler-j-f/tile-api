package io.tileNft.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class EmojiLoader {

  public BufferedImage loadEmojiImage(String filePath) throws IOException {
    ClassPathResource imgFile = new ClassPathResource(filePath);
    return ImageIO.read(imgFile.getInputStream());
  }

  public BufferedImage loadEmojiImage(Resource resource) throws IOException {
    return ImageIO.read(resource.getInputStream());
  }

  public Mat loadEmojiMat(String emojiFilePath, String fileType) throws IOException {
    return bufferedImage2Mat(loadEmojiImage(emojiFilePath), fileType);
  }

  public Mat loadEmojiMat(String emojiFilePath) throws IOException {
    return loadEmojiMat(emojiFilePath, "png");
  }

  public Mat loadEmojiMat(Resource resource, String fileType) throws IOException {
    return bufferedImage2Mat(loadEmojiImage(resource), fileType);
  }

  public Mat loadEmojiMat(Resource resource) throws IOException {
    return loadEmojiMat(resource, "png");
  }

  private Mat bufferedImage2Mat(BufferedImage image, String fileType) throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    ImageIO.write(image, fileType, byteArrayOutputStream);
    byteArrayOutputStream.flush();
    return Imgcodecs.imdecode(
        new MatOfByte(byteArrayOutputStream.toByteArray()), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);
  }
}
