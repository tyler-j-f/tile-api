package com.tylerfitzgerald.demo_api.controller;

import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.token.TokenRetriever;
import com.tylerfitzgerald.demo_api.image.ImageDrawer;
import com.tylerfitzgerald.demo_api.image.ImageException;
import com.tylerfitzgerald.demo_api.image.ImageResourcesLoader;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits.WeightlessTraitDTO;
import java.io.IOException;
import java.util.List;
import java.util.Random;
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
  @Autowired ImageResourcesLoader imageResourcesLoader;
  @Autowired private TokenRetriever tokenRetriever;

  @GetMapping(value = "tile/get/{tokenId}", produces = MediaType.IMAGE_PNG_VALUE)
  public void getTokenImage(HttpServletResponse response, @PathVariable Long tokenId)
      throws ImageException, IOException, ControllerException {
    TokenFacadeDTO nft = tokenRetriever.get(tokenId);
    if (nft == null) {
      throw new ControllerException("Token id not able to be found");
    }
    String[] emojiFileNames = getEmojiFileNames(nft);
    response.setContentType(MediaType.IMAGE_PNG_VALUE);
    byte[] byteArray =
        imageDrawer.drawImage(
            tokenId, 3134L, imageResourcesLoader.getResourcesByName(emojiFileNames));
    writeBufferedImageToOutput(byteArray, response);
    return;
  }

  private String[] getEmojiFileNames(TokenFacadeDTO nft) {
    List<WeightlessTraitDTO> weightlessTraits = nft.getWeightlessTraits();
    String[] names = new String[weightlessTraits.size()];
    int x = 0;
    for (WeightlessTraitDTO weightlessTrait : weightlessTraits) {
      Long traitTypeId = weightlessTrait.getTraitTypeId();
      if (traitTypeId == 11 || traitTypeId == 12 || traitTypeId == 13 || traitTypeId == 14) {
        names[x++] = weightlessTrait.getValue();
      }
    }
    return names;
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
