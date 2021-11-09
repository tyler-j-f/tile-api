package com.tylerfitzgerald.demo_api.controller;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api/image"})
public class ImageController extends BaseController {

  @GetMapping(value = "token/create/{tokenId}", produces = MediaType.IMAGE_PNG_VALUE)
  public void createTokenImage(HttpServletResponse response, @PathVariable Long tokenId)
      throws IOException {
    writeImageFileToOutput(response);
    return;
  }

  @GetMapping(value = "tile/get/{tokenId}", produces = MediaType.IMAGE_PNG_VALUE)
  public void getTokenImage(HttpServletResponse response, @PathVariable Long tokenId)
      throws Exception {
    test(response, tokenId);
    return;
  }

  @GetMapping(value = "contractImage/get/{contractImageId}", produces = MediaType.IMAGE_PNG_VALUE)
  public void getContractImage(HttpServletResponse response, @PathVariable Long contractImageId)
      throws IOException {
    writeImageFileToOutput(response);
    return;
  }

  @GetMapping(value = "saleImage/get/{saleImageId}", produces = MediaType.IMAGE_PNG_VALUE)
  public void getSaleImage(HttpServletResponse response, @PathVariable Long saleImageId)
      throws IOException {
    writeImageFileToOutput(response);
    return;
  }

  @GetMapping(value = "test")
  public void test(HttpServletResponse response, Long tokenId) throws Exception {
    response.setContentType(MediaType.IMAGE_PNG_VALUE);
    Mat tiles = drawTiles(tokenId);
    drawEmojiOnTile(1, tiles, bufferedImage2Mat(loadEmoji("images/1F9D7-1F3FF.png"), "png"));
    BufferedImage bufferedImage = getBufferedImageFromMat(tiles);
    writeBufferedImageToOutput(bufferedImage, response);
    return;
  }

  private void drawEmojiOnTile(int tileIndex, Mat destImage, Mat emojiSource) throws Exception {
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
        throw new Exception("Invalid tile index passed to drawEmojiOnTile");
    }
  }

  public Mat bufferedImage2Mat(BufferedImage image, String fileType) throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    ImageIO.write(image, fileType, byteArrayOutputStream);
    byteArrayOutputStream.flush();
    Mat output = new Mat(72, 72, CvType.CV_8UC4);
    output =
        Imgcodecs.imdecode(
            new MatOfByte(byteArrayOutputStream.toByteArray()), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);
    return output;
  }

  private BufferedImage loadEmoji(String filePath) throws IOException {
    ClassPathResource imgFile = new ClassPathResource(filePath);
    return ImageIO.read(imgFile.getInputStream());
  }

  private Mat drawTiles(Long tokenId) {
    Mat src = new Mat(350, 350, CvType.CV_8UC4);
    // Draw title
    src.setTo(new Scalar(255, 255, 255));
    // Top left square, blue
    Imgproc.rectangle(src, new Point(0, 50), new Point(175, 200), new Scalar(255, 0, 0), -1);
    // Top right square, green
    Imgproc.rectangle(src, new Point(175, 50), new Point(350, 200), new Scalar(0, 102, 0), -1);
    // Bottom left square, red
    Imgproc.rectangle(src, new Point(0, 200), new Point(175, 350), new Scalar(0, 0, 255), -1);
    // Bottom right square, yellow
    Imgproc.rectangle(src, new Point(175, 200), new Point(350, 350), new Scalar(102, 255, 255), -1);
    // Draw title
    Imgproc.putText(
        src,
        "Tile #" + tokenId,
        new Point(20, 30),
        Core.FONT_HERSHEY_COMPLEX,
        1,
        new Scalar(0, 0, 0));
    return src;
  }

  private void writeBufferedImageToOutput(BufferedImage bufferedImage, HttpServletResponse response)
      throws IOException {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    ImageIO.write(bufferedImage, "png", os);
    StreamUtils.copy(new ByteArrayInputStream(os.toByteArray()), response.getOutputStream());
  }

  private void writeImageFileToOutput(HttpServletResponse response) throws IOException {
    ClassPathResource imgFile = new ClassPathResource("images/one.jpeg");
    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
    StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
  }

  public BufferedImage getBufferedImageFromMat(Mat matrix) throws IOException {
    MatOfByte mob = new MatOfByte();
    Imgcodecs.imencode(".png", matrix, mob);
    byte ba[] = mob.toArray();

    BufferedImage bi = ImageIO.read(new ByteArrayInputStream(ba));
    return bi;
  }

  // System.out.println(bufferedImage);
  // Get the BufferedImage's backing array and copy the pixels directly into it
  //    byte[] data = ((DataBufferByte) gray.getRaster().getDataBuffer()).getData();
  //    mat.get(0, 0, data);
  // saveMatAsImage(mat, "src/main/resources/images/test_circle.jpeg");

  public Mat loadImageAsMat(String imagePath) {
    Imgcodecs imageCodecs = new Imgcodecs();
    return imageCodecs.imread(imagePath);
  }

  public void saveMatAsImage(Mat imageMatrix, String targetPath) {
    Imgcodecs imgcodecs = new Imgcodecs();
    imgcodecs.imwrite(targetPath, imageMatrix);
  }

  public void saveBufferedImage(BufferedImage bufferedImage) throws IOException {
    File outputfile = new File("src/main/resources/images/test_buffered_image.jpeg");
    ImageIO.write(bufferedImage, "jpeg", outputfile);
  }
}
