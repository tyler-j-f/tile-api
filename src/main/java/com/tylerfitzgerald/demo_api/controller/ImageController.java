package com.tylerfitzgerald.demo_api.controller;

import com.tylerfitzgerald.demo_api.image.ImageDrawer;
import com.tylerfitzgerald.demo_api.image.ImageException;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api/image"})
public class ImageController extends BaseController {

  @Autowired ImageDrawer imageDrawer;

  @GetMapping(value = "token/create/{tokenId}", produces = MediaType.IMAGE_PNG_VALUE)
  public void createTokenImage(HttpServletResponse response, @PathVariable Long tokenId) {
    return;
  }

  @GetMapping(value = "tile/get/{tokenId}", produces = MediaType.IMAGE_PNG_VALUE)
  public void getTokenImage(HttpServletResponse response, @PathVariable Long tokenId)
      throws ImageException, IOException {
    response.setContentType(MediaType.IMAGE_PNG_VALUE);
    byte[] byteArray = imageDrawer.drawImage(tokenId);
    writeBufferedImageToOutput(byteArray, response);
    return;
  }

  @GetMapping(value = "contractImage/get/{contractImageId}", produces = MediaType.IMAGE_PNG_VALUE)
  public void getContractImage(HttpServletResponse response, @PathVariable Long contractImageId) {
    return;
  }

  @GetMapping(value = "saleImage/get/{saleImageId}", produces = MediaType.IMAGE_PNG_VALUE)
  public void getSaleImage(HttpServletResponse response, @PathVariable Long saleImageId) {
    return;
  }

  private void writeBufferedImageToOutput(byte[] pixelArray, HttpServletResponse response)
      throws IOException {
    response.getOutputStream().write(pixelArray);
  }
}
