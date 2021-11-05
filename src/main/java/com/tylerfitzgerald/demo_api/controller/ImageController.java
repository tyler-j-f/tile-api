package com.tylerfitzgerald.demo_api.controller;

import java.awt.image.BufferedImage;
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
      throws IOException {
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
  public void test(HttpServletResponse response, Long tokenId) throws IOException {
    response.setContentType(MediaType.IMAGE_JPEG_VALUE);

    //    Mat tiles = drawTiles(tokenId);
    //    // Create an empty image in matching format
    //    BufferedImage bufferedImage = getBufferedImageFromMat(tiles);
    //    // saveBufferedImage(bufferedImage);
    ClassPathResource imgFile = new ClassPathResource("images/1F9D7-1F3FF.png");
    BufferedImage bufferedImage = ImageIO.read(imgFile.getInputStream());
    writeBufferedImageToOutput(bufferedImage, response);
    return;
  }

  private Mat drawTiles(Long tokenId) {
    Mat src = new Mat(350, 350, CvType.CV_8UC3);
    // Draw title
    Point textOrg = new Point((src.cols() - src.width()) / 2, (src.rows() + src.height()) / 2);
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
    Imgcodecs.imencode(".jpg", matrix, mob);
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
