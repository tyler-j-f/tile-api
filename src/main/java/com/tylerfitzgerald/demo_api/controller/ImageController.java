package com.tylerfitzgerald.demo_api.controller;

import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.token.TokenRetriever;
import com.tylerfitzgerald.demo_api.erc721.token.traits.WeightedTraitTypeConstants;
import com.tylerfitzgerald.demo_api.erc721.token.traits.WeightlessTraitTypeConstants;
import com.tylerfitzgerald.demo_api.etc.WeightedTraitsListFinder;
import com.tylerfitzgerald.demo_api.etc.WeightlessTraitsListFinder;
import com.tylerfitzgerald.demo_api.image.ImageException;
import com.tylerfitzgerald.demo_api.image.ImageResourcesLoader;
import com.tylerfitzgerald.demo_api.image.drawers.ImageDrawer;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightlessTraitDTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

  @Autowired private ImageDrawer imageDrawer;
  @Autowired private ImageResourcesLoader imageResourcesLoader;
  @Autowired private TokenRetriever tokenRetriever;
  @Autowired private WeightlessTraitsListFinder weightlessTraitsListFinder;
  @Autowired private WeightedTraitsListFinder weightedTraitsListFinder;

  @GetMapping(value = "tile/get/{tokenId}", produces = MediaType.IMAGE_PNG_VALUE)
  public void getTokenImage(HttpServletResponse response, @PathVariable Long tokenId)
      throws ImageException, IOException {
    TokenFacadeDTO nft = tokenRetriever.get(tokenId);
    if (nft == null) {
      System.out.println("Token id not able to be found. TokenId: " + tokenId);
      return;
    }
    String[] emojiFileNames = getEmojiFileNames(nft);
    response.setContentType(MediaType.IMAGE_PNG_VALUE);
    byte[] byteArray =
        imageDrawer.drawImage(
            tokenId,
            getOverallRarityScore(nft.getWeightlessTraits()),
            imageResourcesLoader.getResourcesByName(emojiFileNames),
            getTileColors(nft),
            getIsTokenBurnt(nft));
    writeBufferedImageToOutput(byteArray, response);
    return;
  }

  private Long getOverallRarityScore(List<WeightlessTraitDTO> weightlessTraits) {
    WeightlessTraitDTO weightlessTrait =
        weightlessTraitsListFinder.findFirstByTraitTypeId(
            weightlessTraits, (long) WeightlessTraitTypeConstants.OVERALL_RARITY);
    if (weightlessTrait == null) {
      return 0L;
    }
    return Long.valueOf(weightlessTrait.getValue());
  }

  private List<String> getTileColors(TokenFacadeDTO nft) {
    List<String> tileColors = new ArrayList<>();
    for (WeightlessTraitDTO weightlessTrait : nft.getWeightlessTraits()) {
      Long traitTypeId = weightlessTrait.getTraitTypeId();
      if (traitTypeId == 15 || traitTypeId == 16 || traitTypeId == 17 || traitTypeId == 18) {
        String rgb = weightlessTrait.getValue();
        tileColors.add(rgb.substring(0, 3));
        tileColors.add(rgb.substring(3, 6));
        tileColors.add(rgb.substring(6, 9));
      }
    }
    return tileColors;
  }

  private boolean getIsTokenBurnt(TokenFacadeDTO nft) {
    return weightedTraitsListFinder.findFirstByTraitTypeId(
            nft.getWeightedTraits(), (long) WeightedTraitTypeConstants.IS_BURNT_TOKEN_EQUALS_TRUE)
        != null;
  }

  private String[] getEmojiFileNames(TokenFacadeDTO nft) {
    List<WeightlessTraitDTO> weightlessTraits = nft.getWeightlessTraits();
    String[] names = new String[4];
    int x = 0;
    for (WeightlessTraitDTO weightlessTrait : weightlessTraits) {
      Long traitTypeId = weightlessTrait.getTraitTypeId();
      if (traitTypeId == 11 || traitTypeId == 12 || traitTypeId == 13 || traitTypeId == 14) {
        names[x++] = weightlessTrait.getValue() + ".png";
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
