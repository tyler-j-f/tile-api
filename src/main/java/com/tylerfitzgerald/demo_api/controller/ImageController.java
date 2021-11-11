package com.tylerfitzgerald.demo_api.controller;

import com.tylerfitzgerald.demo_api.image.ImageDrawer;
import com.tylerfitzgerald.demo_api.image.ImageException;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
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
    byte[] byteArray = imageDrawer.drawImage(tokenId, 3134L);
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

  @Autowired private ResourceLoader resourceLoader;

  Resource[] loadResources(String pattern) throws IOException {
    return ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources(pattern);
  }

  @GetMapping(value = "test")
  public void test() throws IOException {
    Resource[] resources = loadResources("classpath:openmoji/*.png");
    int x = 1;
    for (Resource resource : resources) {
      System.out.println("\nresource " + x);
      System.out.println("resource name: " + resource.getFilename());
      x++;
    }
  }
}
