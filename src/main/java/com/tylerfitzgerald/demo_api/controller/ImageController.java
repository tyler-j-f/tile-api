package com.tylerfitzgerald.demo_api.controller;

import com.tylerfitzgerald.demo_api.erc721.token.TokenInitializer;
import com.tylerfitzgerald.demo_api.erc721.token.TokenRetriever;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Autowired;
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
    writeImageToOutput(response);
    return;
  }

  @GetMapping(value = "token/get/{tokenId}", produces = MediaType.IMAGE_PNG_VALUE)
  public void getTokenImage(HttpServletResponse response, @PathVariable Long tokenId)
      throws IOException {
    writeImageToOutput(response);
    return;
  }

  @GetMapping(value = "contractImage/get/{contractImageId}", produces = MediaType.IMAGE_PNG_VALUE)
  public void getContractImage(HttpServletResponse response, @PathVariable Long contractImageId)
      throws IOException {
    writeImageToOutput(response);
    return;
  }

  @GetMapping(value = "saleImage/get/{saleImageId}", produces = MediaType.IMAGE_PNG_VALUE)
  public void getSaleImage(HttpServletResponse response, @PathVariable Long saleImageId)
      throws IOException {
    writeImageToOutput(response);
    return;
  }

  @GetMapping(value = "test")
  public void test() throws IOException {
    //    String path = "src/main/resources/images/two.jpeg";
    Mat mat = new Mat(400, 400, CvType.CV_8U);
    mat.setTo(new Scalar(0));
    Imgproc.circle(mat, new Point(200, 200), 20, new Scalar(100), -1);
    // Create an empty image in matching format
    BufferedImage bufferedImage = getMat2BufferedImage(mat);
    saveBufferedImage(bufferedImage);
    // System.out.println(bufferedImage);
    // Get the BufferedImage's backing array and copy the pixels directly into it
    //    byte[] data = ((DataBufferByte) gray.getRaster().getDataBuffer()).getData();
    //    mat.get(0, 0, data);
    // saveMatAsImage(mat, "src/main/resources/images/test_circle.jpeg");
    return;
  }

  private void writeImageToOutput(HttpServletResponse response) throws IOException {
    ClassPathResource imgFile = new ClassPathResource("images/one.jpeg");
    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
    StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
  }

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

  public BufferedImage getMat2BufferedImage(Mat matrix) throws IOException {
    MatOfByte mob = new MatOfByte();
    Imgcodecs.imencode(".jpg", matrix, mob);
    byte ba[] = mob.toArray();

    BufferedImage bi = ImageIO.read(new ByteArrayInputStream(ba));
    return bi;
  }
}
