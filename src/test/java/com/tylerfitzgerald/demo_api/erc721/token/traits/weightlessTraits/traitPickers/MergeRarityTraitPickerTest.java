package com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.traitPickers;

import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightedTraits.WeightedTraitTypeConstants;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.WeightlessTraitTypeConstants;
import com.tylerfitzgerald.demo_api.etc.lsitFinders.WeightedTraitTypeWeightsListFinder;
import com.tylerfitzgerald.demo_api.etc.lsitFinders.WeightedTraitsListFinder;
import com.tylerfitzgerald.demo_api.etc.lsitFinders.WeightlessTraitsListFinder;
import com.tylerfitzgerald.demo_api.sql.dtos.TokenDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightlessTraitDTO;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MergeRarityTraitPickerTest {
  @Mock private WeightedTraitsListFinder weightedTraitListHelper;
  @Mock private WeightlessTraitsListFinder weightlessTraitInListFinder;
  @Mock private WeightedTraitTypeWeightsListFinder weightedTraitTypeWeightsListFinder;
  @InjectMocks private MergeRarityTraitPicker mergeRarityTraitPicker = new MergeRarityTraitPicker();
  // WEIGHTED TRAIT TYPE IDs
  private Long WEIGHTED_TRAIT_TYPE_ID_1 =
      Long.valueOf(WeightedTraitTypeConstants.TILE_1_MULTIPLIER);
  private Long WEIGHTED_TRAIT_TYPE_ID_2 =
      Long.valueOf(WeightedTraitTypeConstants.TILE_2_MULTIPLIER);
  private Long WEIGHTED_TRAIT_TYPE_ID_3 =
      Long.valueOf(WeightedTraitTypeConstants.TILE_3_MULTIPLIER);
  private Long WEIGHTED_TRAIT_TYPE_ID_4 =
      Long.valueOf(WeightedTraitTypeConstants.TILE_4_MULTIPLIER);
  private Long WEIGHTED_TRAIT_TYPE_ID_5 = Long.valueOf(WeightedTraitTypeConstants.TILE_1_RARITY);
  private Long WEIGHTED_TRAIT_TYPE_ID_6 = Long.valueOf(WeightedTraitTypeConstants.TILE_2_RARITY);
  private Long WEIGHTED_TRAIT_TYPE_ID_7 = Long.valueOf(WeightedTraitTypeConstants.TILE_3_RARITY);
  private Long WEIGHTED_TRAIT_TYPE_ID_8 = Long.valueOf(WeightedTraitTypeConstants.TILE_4_RARITY);
  // WEIGHTED TRAIT TYPE WEIGHT IDs
  private Long WEIGHTED_TRAIT_TYPE_WEIGHT_1_ID = 212L;
  private Long WEIGHTED_TRAIT_TYPE_WEIGHT_2_ID = 213L;
  private Long WEIGHTED_TRAIT_TYPE_WEIGHT_3_ID = 214L;
  // WEIGHTED TRAIT IDs
  private Long WEIGHTED_TRAIT_BURNED_NFT1_1 = 100L;
  private Long WEIGHTED_TRAIT_BURNED_NFT1_2 = 101L;
  private Long WEIGHTED_TRAIT_BURNED_NFT1_3 = 102L;
  private Long WEIGHTED_TRAIT_BURNED_NFT1_4 = 103L;
  private Long WEIGHTED_TRAIT_BURNED_NFT1_5 = 100L;
  private Long WEIGHTED_TRAIT_BURNED_NFT1_6 = 101L;
  private Long WEIGHTED_TRAIT_BURNED_NFT1_7 = 102L;
  private Long WEIGHTED_TRAIT_BURNED_NFT1_8 = 103L;
  private Long WEIGHTED_TRAIT_BURNED_NFT2_1 = 104L;
  private Long WEIGHTED_TRAIT_BURNED_NFT2_2 = 105L;
  private Long WEIGHTED_TRAIT_BURNED_NFT2_3 = 106L;
  private Long WEIGHTED_TRAIT_BURNED_NFT2_4 = 107L;
  // WEIGHTLESS TRAIT TYPE IDs
  private Long WEIGHTLESS_TRAIT_TYPE_ID_1 =
      Long.valueOf(WeightlessTraitTypeConstants.TILE_1_RARITY);
  private Long WEIGHTLESS_TRAIT_TYPE_ID_2 =
      Long.valueOf(WeightlessTraitTypeConstants.TILE_1_RARITY);
  private Long WEIGHTLESS_TRAIT_TYPE_ID_3 =
      Long.valueOf(WeightlessTraitTypeConstants.TILE_1_RARITY);
  private Long WEIGHTLESS_TRAIT_TYPE_ID_4 =
      Long.valueOf(WeightlessTraitTypeConstants.TILE_1_RARITY);
  // WEIGHTLESS TRAIT IDs
  private Long WEIGHTLESS_TRAIT_BURNED_NFT2_1 = 104L;
  private Long WEIGHTLESS_TRAIT_BURNED_NFT2_2 = 105L;
  private Long WEIGHTLESS_TRAIT_BURNED_NFT2_3 = 106L;
  private Long WEIGHTLESS_TRAIT_BURNED_NFT2_4 = 107L;
  // BURNED TOKENs
  private TokenFacadeDTO burnedNft1;
  private TokenFacadeDTO burnedNft2;
  private Long BURNED_TOKEN_1_ID = 202L;
  private Long BURNED_TOKEN_2_ID = 203L;
  private Long MERGE_RARITY_TRAIT_PICKER_VALUE_1 = 147L;
  private int TRAIT_TYPE_ID_TO_CREATE = WeightlessTraitTypeConstants.TILE_1_RARITY;
  private WeightlessTraitPickerContext context;

  @BeforeEach
  public void setup() throws WeightlessTraitPickerException {
    setupBurnedNft1();
    setupBurnedNft2();
    setupContext();
    setupMocks();
  }
  //
  //  @Test
  //  public void testMergeRarityTraitPicker() throws WeightlessTraitPickerException {
  //    assertThat(mergeRarityTraitPicker.getValue(context))
  //        .isEqualTo(MERGE_RARITY_TRAIT_PICKER_VALUE_1);
  //    assertThat(mergeRarityTraitPicker.getValue(context)).isEqualTo("");
  //  }

  private void setupMocks() {
    List<WeightedTraitTypeWeightDTO> weightedTraitTypeWeights =
        burnedNft1.getWeightedTraitTypeWeights();
    Mockito.when(
            weightedTraitTypeWeightsListFinder.findFirstByTraitTypeWeightId(weightedTraitTypeWeights, 1L))
        .thenReturn(weightedTraitTypeWeights.get(0));
  }

  // Burned NFT1 will have all rarity and multiplier traits be weighted
  private void setupBurnedNft1() {
    burnedNft1 =
        TokenFacadeDTO.builder()
            .tokenDTO(TokenDTO.builder().tokenId(BURNED_TOKEN_1_ID).build())
            .weightedTraitTypeWeights(getBurnedNft1WeightedTraitTypeWeights())
            .weightedTraits(getBurnedNft1WeightedTraits())
            .weightlessTraits(getBurnedNft1WeightlessTraits())
            .build();
  }

  private List<WeightedTraitDTO> getBurnedNft1WeightedTraits() {
    List<WeightedTraitDTO> list = new ArrayList<>();
    list.add(
        WeightedTraitDTO.builder()
            .tokenId(BURNED_TOKEN_1_ID)
            .traitId(WEIGHTED_TRAIT_BURNED_NFT1_1)
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_1_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_1)
            .build());
    list.add(
        WeightedTraitDTO.builder()
            .tokenId(BURNED_TOKEN_1_ID)
            .traitId(WEIGHTED_TRAIT_BURNED_NFT1_2)
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_2_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_2)
            .build());
    list.add(
        WeightedTraitDTO.builder()
            .tokenId(BURNED_TOKEN_1_ID)
            .traitId(WEIGHTED_TRAIT_BURNED_NFT1_3)
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_3_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_3)
            .build());
    list.add(
        WeightedTraitDTO.builder()
            .tokenId(BURNED_TOKEN_1_ID)
            .traitId(WEIGHTED_TRAIT_BURNED_NFT1_4)
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_1_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_4)
            .build());
    list.add(
        WeightedTraitDTO.builder()
            .tokenId(BURNED_TOKEN_1_ID)
            .traitId(WEIGHTED_TRAIT_BURNED_NFT1_5)
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_2_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_5)
            .build());
    list.add(
        WeightedTraitDTO.builder()
            .tokenId(BURNED_TOKEN_1_ID)
            .traitId(WEIGHTED_TRAIT_BURNED_NFT1_6)
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_3_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_6)
            .build());
    list.add(
        WeightedTraitDTO.builder()
            .tokenId(BURNED_TOKEN_1_ID)
            .traitId(WEIGHTED_TRAIT_BURNED_NFT1_7)
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_1_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_7)
            .build());
    list.add(
        WeightedTraitDTO.builder()
            .tokenId(BURNED_TOKEN_1_ID)
            .traitId(WEIGHTED_TRAIT_BURNED_NFT1_8)
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_2_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_8)
            .build());
    return list;
  }

  private List<WeightedTraitTypeWeightDTO> getBurnedNft1WeightedTraitTypeWeights() {
    List<WeightedTraitTypeWeightDTO> list = new ArrayList<>();
    list.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_1_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_1)
            .likelihood(100L)
            .value("2")
            .build());
    list.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_2_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_2)
            .likelihood(100L)
            .value("3")
            .build());
    list.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_3_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_3)
            .likelihood(100L)
            .value("4")
            .build());
    list.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_1_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_4)
            .likelihood(100L)
            .value("5")
            .build());
    list.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_2_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_5)
            .likelihood(100L)
            .value("6")
            .build());
    list.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_3_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_6)
            .likelihood(100L)
            .value("7")
            .build());
    list.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_1_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_7)
            .likelihood(100L)
            .value("8")
            .build());
    list.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_2_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_8)
            .likelihood(100L)
            .value("9")
            .build());
    return list;
  }

  private List<WeightlessTraitDTO> getBurnedNft1WeightlessTraits() {
    return new ArrayList<>();
  }

  // Burned NFT2 will have multiplier traits be weighted rarity values be weightless.
  // This indicates that burnedNFT2 came from a prior merge event.
  private void setupBurnedNft2() {
    burnedNft2 =
        TokenFacadeDTO.builder()
            .tokenDTO(TokenDTO.builder().tokenId(BURNED_TOKEN_2_ID).build())
            .weightedTraitTypeWeights(getBurnedNft2WeightedTraitTypeWeights())
            .weightedTraits(getBurnedNft2WeightedTraits())
            .weightlessTraits(getBurnedNft2WeightlessTraits())
            .build();
  }

  private List<WeightedTraitDTO> getBurnedNft2WeightedTraits() {
    List<WeightedTraitDTO> list = new ArrayList<>();
    list.add(
        WeightedTraitDTO.builder()
            .tokenId(BURNED_TOKEN_2_ID)
            .traitId(WEIGHTED_TRAIT_BURNED_NFT2_1)
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_1_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_1)
            .build());
    list.add(
        WeightedTraitDTO.builder()
            .tokenId(BURNED_TOKEN_2_ID)
            .traitId(WEIGHTED_TRAIT_BURNED_NFT2_2)
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_2_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_2)
            .build());
    list.add(
        WeightedTraitDTO.builder()
            .tokenId(BURNED_TOKEN_2_ID)
            .traitId(WEIGHTED_TRAIT_BURNED_NFT2_3)
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_3_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_3)
            .build());
    list.add(
        WeightedTraitDTO.builder()
            .tokenId(BURNED_TOKEN_2_ID)
            .traitId(WEIGHTED_TRAIT_BURNED_NFT2_4)
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_1_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_4)
            .build());
    return list;
  }

  private List<WeightedTraitTypeWeightDTO> getBurnedNft2WeightedTraitTypeWeights() {
    List<WeightedTraitTypeWeightDTO> list = new ArrayList<>();
    list.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_1_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_1)
            .likelihood(100L)
            .value("10")
            .build());
    list.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_2_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_2)
            .likelihood(100L)
            .value("11")
            .build());
    list.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_3_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_3)
            .likelihood(100L)
            .value("12")
            .build());
    list.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_1_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_4)
            .likelihood(100L)
            .value("13")
            .build());
    return list;
  }

  private List<WeightlessTraitDTO> getBurnedNft2WeightlessTraits() {
    List<WeightlessTraitDTO> list = new ArrayList<>();
    list.add(
        WeightlessTraitDTO.builder()
            .tokenId(BURNED_TOKEN_2_ID)
            .traitId(WEIGHTLESS_TRAIT_BURNED_NFT2_1)
            .traitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_1)
            .value("14")
            .displayTypeValue("")
            .build());
    list.add(
        WeightlessTraitDTO.builder()
            .tokenId(BURNED_TOKEN_2_ID)
            .traitId(WEIGHTLESS_TRAIT_BURNED_NFT2_2)
            .traitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_1)
            .value("15")
            .displayTypeValue("")
            .build());
    list.add(
        WeightlessTraitDTO.builder()
            .tokenId(BURNED_TOKEN_2_ID)
            .traitId(WEIGHTLESS_TRAIT_BURNED_NFT2_3)
            .traitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_1)
            .value("16")
            .displayTypeValue("")
            .build());
    list.add(
        WeightlessTraitDTO.builder()
            .tokenId(BURNED_TOKEN_2_ID)
            .traitId(WEIGHTLESS_TRAIT_BURNED_NFT2_4)
            .traitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_1)
            .value("17")
            .displayTypeValue("")
            .build());
    return list;
  }

  private void setupContext() {
    context =
        WeightlessTraitPickerContext.builder()
            .traitTypeId(TRAIT_TYPE_ID_TO_CREATE)
            .burnedNft1(burnedNft1)
            .burnedNft2(burnedNft2)
            .build();
  }
}
