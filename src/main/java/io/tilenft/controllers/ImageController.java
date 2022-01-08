package io.tilenft.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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
      @RequestParam(required = false, defaultValue = "") String tile4Emoji)
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
            .tile4Emoji(tile4Emoji)
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

  @GetMapping(value = "emoji/indexes")
  public String getEmojiIndexes(
      @RequestParam(required = false, defaultValue = "") String tile1Emoji,
      @RequestParam(required = false, defaultValue = "") String tile2Emoji,
      @RequestParam(required = false, defaultValue = "") String tile3Emoji,
      @RequestParam(required = false, defaultValue = "") String tile4Emoji,
      @RequestParam(required = false, defaultValue = "0") Long tokenId)
      throws ImageException, IOException {
    boolean isThereAMissingEmojiValue =
        tile1Emoji.equals("")
            || tile2Emoji.equals("")
            || tile3Emoji.equals("")
            || tile4Emoji.equals("");
    if (tokenId.equals(0L) && isThereAMissingEmojiValue) {
      throw new ImageException(
          "All tile emoji values must be specified. Or a tokenId can be passed and any tile emojis, 0-4, that's not requested will be replaced with current tokenId corresponding values.");
    }
    TokenFacadeDTO nft = isThereAMissingEmojiValue ? tokenRetriever.get(tokenId) : null;
    String[] emojiFilenames = new String[4];
    emojiFilenames[0] =
        !tile1Emoji.equals("")
            ? appendPngFileExtension(tile1Emoji.toUpperCase())
            : getCurrentTokenEmojiValue(1, nft);
    emojiFilenames[1] =
        !tile2Emoji.equals("")
            ? appendPngFileExtension(tile2Emoji.toUpperCase())
            : getCurrentTokenEmojiValue(2, nft);
    emojiFilenames[2] =
        !tile3Emoji.equals("")
            ? appendPngFileExtension(tile3Emoji.toUpperCase())
            : getCurrentTokenEmojiValue(3, nft);
    emojiFilenames[3] =
        !tile4Emoji.equals("")
            ? appendPngFileExtension(tile4Emoji.toUpperCase())
            : getCurrentTokenEmojiValue(4, nft);
    System.out.println("DEBUG emojiFilenames: " + Arrays.toString(emojiFilenames));
    int[] indexes = imageResourcesLoader.getResourcesIndexes(emojiFilenames);
    System.out.println("DEBUG indexes: " + Arrays.toString(indexes));
    return new ObjectMapper().writeValueAsString(indexes);
  }

  private String getCurrentTokenEmojiValue(int tileNumber, TokenFacadeDTO nft)
      throws ImageException {
    List<WeightlessTraitDTO> weightlessTraits = nft.getWeightlessTraits();
    switch (tileNumber) {
      case 1:
        return getEmojiValueFromTraitsList(
            weightlessTraits, WeightlessTraitTypeConstants.TILE_1_EMOJI);
      case 2:
        return getEmojiValueFromTraitsList(
            weightlessTraits, WeightlessTraitTypeConstants.TILE_2_EMOJI);
      case 3:
        return getEmojiValueFromTraitsList(
            weightlessTraits, WeightlessTraitTypeConstants.TILE_3_EMOJI);
      case 4:
        return getEmojiValueFromTraitsList(
            weightlessTraits, WeightlessTraitTypeConstants.TILE_4_EMOJI);
      default:
        throw new ImageException(
            "getCurrentTokenEmojiValue failed. tokenId: "
                + nft.getTokenDTO().getTokenId()
                + ", tileNumber: "
                + tileNumber
                + ", nft: "
                + nft);
    }
  }

  private String getEmojiValueFromTraitsList(
      List<WeightlessTraitDTO> weightlessTraits, int traitTypeId) {
    return appendPngFileExtension(
        weightlessTraits.stream()
            .filter(trait -> trait.getTraitTypeId() == traitTypeId)
            .findFirst()
            .get()
            .getValue());
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
      updateTileColors(tileColors, metadataSetDTO);
    }
    String[] emojiFileNames = getEmojiFileNames(nft);
    if (wasTileEmojiChangeRequested(metadataSetDTO)) {
      updateTileEmojis(emojiFileNames, metadataSetDTO);
    }
    System.out.println(Arrays.toString(emojiFileNames));
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

  private boolean wasTileEmojiChangeRequested(MetadataSetDTO metadataSetDTO) {
    return metadataSetDTO != null
        && (!metadataSetDTO.getTile1Emoji().equals("")
            || !metadataSetDTO.getTile2Emoji().equals("")
            || !metadataSetDTO.getTile3Emoji().equals("")
            || !metadataSetDTO.getTile4Emoji().equals(""));
  }

  private void updateTileEmojis(String[] emojiFileNames, MetadataSetDTO metadataSetDTO)
      throws ImageException {
    System.out.println(
        "updateTileEmojis called. emojiFileNames: "
            + Arrays.toString(emojiFileNames)
            + ", metadataSetDTO: "
            + metadataSetDTO);
    if (!metadataSetDTO.getTile1Emoji().equals("")) {
      emojiFileNames[0] = metadataSetDTO.getEmojiFilename(1);
    }
    if (!metadataSetDTO.getTile2Emoji().equals("")) {
      emojiFileNames[1] = metadataSetDTO.getEmojiFilename(2);
    }
    if (!metadataSetDTO.getTile3Emoji().equals("")) {
      emojiFileNames[2] = metadataSetDTO.getEmojiFilename(3);
    }
    if (!metadataSetDTO.getTile4Emoji().equals("")) {
      emojiFileNames[3] = metadataSetDTO.getEmojiFilename(4);
    }
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
        names[x++] = appendPngFileExtension(weightlessTrait.getValue());
      }
    }
    return names;
  }

  private String appendPngFileExtension(String input) {
    return input + ".png";
  }

  private void writeBufferedImageToOutput(byte[] pixelArray, HttpServletResponse response)
      throws IOException {
    response.getOutputStream().write(pixelArray);
  }
}
