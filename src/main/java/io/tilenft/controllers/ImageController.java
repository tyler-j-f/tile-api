package io.tilenft.controllers;

import io.tilenft.etc.lists.finders.WeightedTraitsListFinder;
import io.tilenft.etc.lists.finders.WeightlessTraitsListFinder;
import io.tilenft.eth.token.TokenFacadeDTO;
import io.tilenft.eth.token.TokenRetriever;
import io.tilenft.eth.token.traits.weighted.WeightedTraitTypeConstants;
import io.tilenft.eth.token.traits.weightless.WeightlessTraitTypeConstants;
import io.tilenft.image.ImageException;
import io.tilenft.image.ImageResourcesLoader;
import io.tilenft.image.MetadataSetDTO;
import io.tilenft.image.drawers.ImageDrawer;
import io.tilenft.sql.dtos.WeightlessTraitDTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    getImage(response, tokenId);
  }

  @GetMapping(value = "metadataSet/get/{tokenId}", produces = MediaType.IMAGE_PNG_VALUE)
  public void getMetadataSetImage(
      HttpServletResponse response,
      @PathVariable Long tokenId,
      @RequestParam(required = false, defaultValue = "") String tile1Color,
      @RequestParam(required = false, defaultValue = "") String tile2Color,
      @RequestParam(required = false, defaultValue = "") String tile3Color,
      @RequestParam(required = false, defaultValue = "") String tile4Color,
      @RequestParam(required = false, defaultValue = "") String tile1Emoji,
      @RequestParam(required = false, defaultValue = "") String tile2Emoji,
      @RequestParam(required = false, defaultValue = "") String tile3Emoji,
      @RequestParam(required = false, defaultValue = "") String tile41Emoji)
      throws ImageException, IOException {
    getImage(
        response,
        tokenId,
        MetadataSetDTO.builder()
            .tile1Color(tile1Color)
            .tile2Color(tile2Color)
            .tile3Color(tile3Color)
            .tile4Color(tile4Color)
            .tile1Emoji(tile1Emoji)
            .tile2Emoji(tile2Emoji)
            .tile3Emoji(tile3Emoji)
            .tile4Emoji(tile41Emoji)
            .build());
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

  private void getImage(HttpServletResponse response, Long tokenId)
      throws ImageException, IOException {
    getImage(response, tokenId, null);
  }

  private void getImage(HttpServletResponse response, Long tokenId, MetadataSetDTO metadataSetDTO)
      throws ImageException, IOException {
    TokenFacadeDTO nft = tokenRetriever.get(tokenId);
    if (nft == null) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      System.out.println("Token id not able to be found. TokenId: " + tokenId);
      return;
    }
    response.setContentType(MediaType.IMAGE_PNG_VALUE);
    List<String> tileColors = getTileColors(nft);
    if (wasTileColorChangeRequested(metadataSetDTO)) {
      System.out.println("tile change requested -> true");
      updateTileColors(tileColors, metadataSetDTO);
    }
    String[] emojiFileNames = getEmojiFileNames(nft);
    if (wasTileEmojiChangeRequested(metadataSetDTO)) {
      System.out.println("emoji change request -> true");
      updateTileEmojis(emojiFileNames, metadataSetDTO);
    }
    byte[] byteArray =
        imageDrawer.drawImage(
            tokenId,
            getOverallRarityScore(nft.getWeightlessTraits()),
            imageResourcesLoader.getResourcesByName(emojiFileNames),
            tileColors,
            getIsTokenBurnt(nft));
    writeBufferedImageToOutput(byteArray, response);
    return;
  }

  private boolean wasTileColorChangeRequested(MetadataSetDTO metadataSetDTO) {
    return metadataSetDTO != null
        && (!metadataSetDTO.getTile1Color().equals("")
            || !metadataSetDTO.getTile2Color().equals("")
            || !metadataSetDTO.getTile3Color().equals("")
            || !metadataSetDTO.getTile4Color().equals(""));
  }

  private void updateTileColors(List<String> tileColors, MetadataSetDTO metadataSetDTO) {
    String tile1Color = metadataSetDTO.getTile1Color();
    if (!tile1Color.equals("")) {
      tileColors.set(0, tile1Color.substring(0, 3));
      tileColors.set(1, tile1Color.substring(3, 6));
      tileColors.set(2, tile1Color.substring(6, 9));
    }
    String tile2Color = metadataSetDTO.getTile2Color();
    if (!tile2Color.equals("")) {
      tileColors.set(3, tile2Color.substring(0, 3));
      tileColors.set(4, tile2Color.substring(3, 6));
      tileColors.set(5, tile2Color.substring(6, 9));
    }
    String tile3Color = metadataSetDTO.getTile3Color();
    if (!tile3Color.equals("")) {
      tileColors.set(6, tile3Color.substring(0, 3));
      tileColors.set(7, tile3Color.substring(3, 6));
      tileColors.set(8, tile3Color.substring(6, 9));
    }
    String tile4Color = metadataSetDTO.getTile4Color();
    if (!tile4Color.equals("")) {
      tileColors.set(9, tile4Color.substring(0, 3));
      tileColors.set(10, tile4Color.substring(3, 6));
      tileColors.set(11, tile4Color.substring(6, 9));
    }
  }

  private void updateTileEmojis(String[] emojiFileNames, MetadataSetDTO metadataSetDTO) {
    System.out.println(
        "updateTileEmojis called. emojiFileNames: "
            + Arrays.toString(emojiFileNames)
            + ", metadataSetDTO: "
            + metadataSetDTO);
    String tile1Emoji = metadataSetDTO.getTile1Emoji();
    if (!tile1Emoji.equals("")) {
      emojiFileNames[0] = tile1Emoji;
    }
    String tile2Emoji = metadataSetDTO.getTile2Emoji();
    if (!tile2Emoji.equals("")) {
      emojiFileNames[1] = tile2Emoji;
    }
    String tile3Emoji = metadataSetDTO.getTile3Emoji();
    if (!tile3Emoji.equals("")) {
      emojiFileNames[2] = tile3Emoji;
    }
    String tile4Emoji = metadataSetDTO.getTile4Color();
    if (!tile4Emoji.equals("")) {
      emojiFileNames[3] = tile4Emoji;
    }
  }

  private boolean wasTileEmojiChangeRequested(MetadataSetDTO metadataSetDTO) {
    return metadataSetDTO != null
        && (!metadataSetDTO.getTile1Emoji().equals("")
            || !metadataSetDTO.getTile2Emoji().equals("")
            || !metadataSetDTO.getTile3Emoji().equals("")
            || !metadataSetDTO.getTile4Emoji().equals(""));
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

  private void writeBufferedImageToOutput(byte[] pixelArray, HttpServletResponse response)
      throws IOException {
    response.getOutputStream().write(pixelArray);
  }
}
