package com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.traitPickers;

import static org.assertj.core.api.Assertions.assertThat;

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
import org.junit.jupiter.api.Test;
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
  private Long WEIGHTED_TRAIT_TYPE_ID_9 = Long.valueOf(WeightedTraitTypeConstants.MERGE_MULTIPLIER);
  // WEIGHTED TRAIT TYPE WEIGHT IDs
  private Long WEIGHTED_TRAIT_TYPE_WEIGHT_1_ID = 212L;
  private Long WEIGHTED_TRAIT_TYPE_WEIGHT_2_ID = 213L;
  private Long WEIGHTED_TRAIT_TYPE_WEIGHT_3_ID = 214L;
  private Long WEIGHTED_TRAIT_TYPE_WEIGHT_4_ID = 215L;
  private Long WEIGHTED_TRAIT_TYPE_WEIGHT_5_ID = 216L;
  private Long WEIGHTED_TRAIT_TYPE_WEIGHT_6_ID = 217L;
  private Long WEIGHTED_TRAIT_TYPE_WEIGHT_7_ID = 218L;
  private Long WEIGHTED_TRAIT_TYPE_WEIGHT_8_ID = 219L;
  private Long WEIGHTED_TRAIT_TYPE_WEIGHT_9_ID = 220L;
  private Long WEIGHTED_TRAIT_TYPE_WEIGHT_10_ID = 221L;
  private Long WEIGHTED_TRAIT_TYPE_WEIGHT_11_ID = 222L;
  private Long WEIGHTED_TRAIT_TYPE_WEIGHT_12_ID = 223L;
  private Long WEIGHTED_TRAIT_TYPE_WEIGHT_13_ID = 224L;
  private Long WEIGHTED_TRAIT_TYPE_WEIGHT_14_ID = 225L;
  // WEIGHTED TRAIT IDs
  private Long WEIGHTED_TRAIT_BURNED_NFT1_1 = 100L;
  private Long WEIGHTED_TRAIT_BURNED_NFT1_2 = 101L;
  private Long WEIGHTED_TRAIT_BURNED_NFT1_3 = 102L;
  private Long WEIGHTED_TRAIT_BURNED_NFT1_4 = 103L;
  private Long WEIGHTED_TRAIT_BURNED_NFT1_5 = 104L;
  private Long WEIGHTED_TRAIT_BURNED_NFT1_6 = 105L;
  private Long WEIGHTED_TRAIT_BURNED_NFT1_7 = 106L;
  private Long WEIGHTED_TRAIT_BURNED_NFT1_8 = 107L;
  private Long WEIGHTED_TRAIT_BURNED_NFT1_9 = 108L;
  private Long WEIGHTED_TRAIT_BURNED_NFT2_1 = 109L;
  private Long WEIGHTED_TRAIT_BURNED_NFT2_2 = 110L;
  private Long WEIGHTED_TRAIT_BURNED_NFT2_3 = 111L;
  private Long WEIGHTED_TRAIT_BURNED_NFT2_4 = 112L;
  private Long WEIGHTED_TRAIT_BURNED_NFT2_5 = 113L;
  // WEIGHTLESS TRAIT TYPE IDs
  private Long WEIGHTLESS_TRAIT_TYPE_ID_1 =
      Long.valueOf(WeightlessTraitTypeConstants.TILE_1_RARITY);
  private Long WEIGHTLESS_TRAIT_TYPE_ID_2 =
      Long.valueOf(WeightlessTraitTypeConstants.TILE_2_RARITY);
  private Long WEIGHTLESS_TRAIT_TYPE_ID_3 =
      Long.valueOf(WeightlessTraitTypeConstants.TILE_3_RARITY);
  private Long WEIGHTLESS_TRAIT_TYPE_ID_4 =
      Long.valueOf(WeightlessTraitTypeConstants.TILE_4_RARITY);
  // WEIGHTLESS TRAIT IDs
  private Long WEIGHTLESS_TRAIT_BURNED_NFT2_1 = 114L;
  private Long WEIGHTLESS_TRAIT_BURNED_NFT2_2 = 115L;
  private Long WEIGHTLESS_TRAIT_BURNED_NFT2_3 = 116L;
  private Long WEIGHTLESS_TRAIT_BURNED_NFT2_4 = 117L;
  // BURNED TOKENs
  private TokenFacadeDTO burnedNft1;
  private TokenFacadeDTO burnedNft2;
  private Long BURNED_TOKEN_1_ID = 202L;
  private Long BURNED_TOKEN_2_ID = 203L;
  private String MERGE_RARITY_TRAIT_PICKER_VALUE_1 = "2052";
  private int TRAIT_TYPE_ID_TO_CREATE = WeightlessTraitTypeConstants.TILE_2_RARITY;
  private WeightlessTraitPickerContext context;

  @BeforeEach
  public void setup() throws WeightlessTraitPickerException {
    setupBurnedNft1();
    setupBurnedNft2();
    setupContext();
    setupMocks();
  }

  @Test
  public void testMergeRarityTraitPicker() throws WeightlessTraitPickerException {
    assertThat(mergeRarityTraitPicker.getValue(context))
        .isEqualTo(MERGE_RARITY_TRAIT_PICKER_VALUE_1);
    assertThat(mergeRarityTraitPicker.getValue(context)).isEqualTo("");
  }

  private void setupMocks() {
    mockForGetBurnedTokenRarityTraitValues();
    mockForFindWeightedTraitValue();
  }

  private void mockForGetBurnedTokenRarityTraitValues() {
    List<WeightlessTraitDTO> burnedNft1WeightlessTraits = burnedNft1.getWeightlessTraits();
    // Nft1 has no weightless traits, so return null
    Mockito.when(
            weightlessTraitInListFinder.findFirstByTraitTypeId(
                burnedNft1WeightlessTraits, Long.valueOf(TRAIT_TYPE_ID_TO_CREATE)))
        .thenReturn(null);
    List<WeightlessTraitDTO> burnedNft2WeightlessTraits = burnedNft2.getWeightlessTraits();
    // Nft2 Has weightless traits, so return index 1 which matches with tile 2 rarity.
    Mockito.when(
            weightlessTraitInListFinder.findFirstByTraitTypeId(
                burnedNft2WeightlessTraits, Long.valueOf(TRAIT_TYPE_ID_TO_CREATE)))
        .thenReturn(burnedNft2WeightlessTraits.get(1));
    // Nft1 will have to call getBurnedTokenWeightedTraitValues, so return the 5th index, which
    // matches to tile 2 rarity
    List<WeightedTraitDTO> burnedNft1WeightedTraits = burnedNft1.getWeightedTraits();
    List<WeightedTraitTypeWeightDTO> burnedNft1WeightedTraitTypeWeights =
        burnedNft1.getWeightedTraitTypeWeights();
    WeightedTraitDTO foundTrait = burnedNft1WeightedTraits.get(5);
    Mockito.when(
            weightedTraitListHelper.findFirstByTraitTypeId(
                burnedNft1WeightedTraits, (long) WeightedTraitTypeConstants.TILE_2_RARITY))
        .thenReturn(foundTrait);
    Mockito.when(
            weightedTraitTypeWeightsListFinder.findFirstByTraitTypeWeightId(
                burnedNft1WeightedTraitTypeWeights, foundTrait.getTraitTypeWeightId()))
        .thenReturn(burnedNft1WeightedTraitTypeWeights.get(5));
  }

  private void mockForFindWeightedTraitValue() {
    // Burned Nft1 multiplier 1
    List<WeightedTraitDTO> burnedNft1WeightedTraits = burnedNft1.getWeightedTraits();
    WeightedTraitDTO burnedNft1FoundTrait = burnedNft1WeightedTraits.get(1);
    Mockito.when(
            weightedTraitListHelper.findFirstByTraitTypeId(
                burnedNft1WeightedTraits, (long) WeightedTraitTypeConstants.TILE_2_MULTIPLIER))
        .thenReturn(burnedNft1FoundTrait);
    List<WeightedTraitTypeWeightDTO> burnedNft1WeightedTraitTypeWeights =
        burnedNft1.getWeightedTraitTypeWeights();
    Mockito.when(
            weightedTraitTypeWeightsListFinder.findFirstByTraitTypeWeightId(
                burnedNft1WeightedTraitTypeWeights, burnedNft1FoundTrait.getTraitTypeWeightId()))
        .thenReturn(burnedNft1WeightedTraitTypeWeights.get(1));
    // Burned Nft2 multiplier 1
    List<WeightedTraitDTO> burnedNft2WeightedTraits = burnedNft2.getWeightedTraits();
    WeightedTraitDTO burnedNft2FoundTrait = burnedNft2WeightedTraits.get(1);
    Mockito.when(
            weightedTraitListHelper.findFirstByTraitTypeId(
                burnedNft2WeightedTraits, (long) WeightedTraitTypeConstants.TILE_2_MULTIPLIER))
        .thenReturn(burnedNft2FoundTrait);
    List<WeightedTraitTypeWeightDTO> burnedNft2WeightedTraitTypeWeights =
        burnedNft2.getWeightedTraitTypeWeights();
    Mockito.when(
            weightedTraitTypeWeightsListFinder.findFirstByTraitTypeWeightId(
                burnedNft2WeightedTraitTypeWeights, burnedNft2FoundTrait.getTraitTypeWeightId()))
        .thenReturn(burnedNft2WeightedTraitTypeWeights.get(1));
    // Burned Nft1 Merge multiplier 1
    WeightedTraitDTO burnedNft1FoundTrait2 = burnedNft1WeightedTraits.get(8);
    Mockito.when(
            weightedTraitListHelper.findFirstByTraitTypeId(
                burnedNft1WeightedTraits, (long) WeightedTraitTypeConstants.MERGE_MULTIPLIER))
        .thenReturn(burnedNft1FoundTrait2);
    Mockito.when(
            weightedTraitTypeWeightsListFinder.findFirstByTraitTypeWeightId(
                burnedNft1WeightedTraitTypeWeights, burnedNft1FoundTrait2.getTraitTypeWeightId()))
        .thenReturn(burnedNft1WeightedTraitTypeWeights.get(8));
    // Burned Nft1 Merge multiplier 2
    WeightedTraitDTO burnedNft2FoundTrait2 = burnedNft2WeightedTraits.get(4);
    Mockito.when(
            weightedTraitListHelper.findFirstByTraitTypeId(
                burnedNft2WeightedTraits, (long) WeightedTraitTypeConstants.MERGE_MULTIPLIER))
        .thenReturn(burnedNft2FoundTrait);
    Mockito.when(
            weightedTraitTypeWeightsListFinder.findFirstByTraitTypeWeightId(
                burnedNft2WeightedTraitTypeWeights, burnedNft2FoundTrait2.getTraitTypeWeightId()))
        .thenReturn(burnedNft2WeightedTraitTypeWeights.get(4));
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
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_4_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_4)
            .build());
    list.add(
        WeightedTraitDTO.builder()
            .tokenId(BURNED_TOKEN_1_ID)
            .traitId(WEIGHTED_TRAIT_BURNED_NFT1_5)
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_5_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_5)
            .build());
    list.add(
        WeightedTraitDTO.builder()
            .tokenId(BURNED_TOKEN_1_ID)
            .traitId(WEIGHTED_TRAIT_BURNED_NFT1_6)
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_6_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_6)
            .build());
    list.add(
        WeightedTraitDTO.builder()
            .tokenId(BURNED_TOKEN_1_ID)
            .traitId(WEIGHTED_TRAIT_BURNED_NFT1_7)
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_7_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_7)
            .build());
    list.add(
        WeightedTraitDTO.builder()
            .tokenId(BURNED_TOKEN_1_ID)
            .traitId(WEIGHTED_TRAIT_BURNED_NFT1_8)
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_8_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_8)
            .build());
    list.add(
        WeightedTraitDTO.builder()
            .tokenId(BURNED_TOKEN_1_ID)
            .traitId(WEIGHTED_TRAIT_BURNED_NFT1_9)
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_9_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_9)
            .build());
    return list;
  }

  private List<WeightedTraitTypeWeightDTO> getBurnedNft1WeightedTraitTypeWeights() {
    List<WeightedTraitTypeWeightDTO> list = new ArrayList<>();
    // Multiplier 1
    list.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_1_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_1)
            .likelihood(100L)
            .value("2")
            .build());
    // Multiplier 2
    list.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_2_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_2)
            .likelihood(100L)
            .value("3")
            .build());
    // Multiplier 3
    list.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_3_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_3)
            .likelihood(100L)
            .value("4")
            .build());
    // Multiplier 4
    list.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_4_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_4)
            .likelihood(100L)
            .value("5")
            .build());
    // Rarity 1
    list.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_5_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_5)
            .likelihood(100L)
            .value("6")
            .build());
    // Rarity 2
    list.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_6_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_6)
            .likelihood(100L)
            .value("7")
            .build());
    // Rarity 3
    list.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_7_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_7)
            .likelihood(100L)
            .value("8")
            .build());
    // Rarity 4
    list.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_8_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_8)
            .likelihood(100L)
            .value("9")
            .build());
    // Merge multiplier
    list.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_9_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_8)
            .likelihood(100L)
            .value("10")
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
    // Multiplier 1
    list.add(
        WeightedTraitDTO.builder()
            .tokenId(BURNED_TOKEN_2_ID)
            .traitId(WEIGHTED_TRAIT_BURNED_NFT2_1)
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_10_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_1)
            .build());
    // Multiplier 2
    list.add(
        WeightedTraitDTO.builder()
            .tokenId(BURNED_TOKEN_2_ID)
            .traitId(WEIGHTED_TRAIT_BURNED_NFT2_2)
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_11_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_2)
            .build());
    // Multiplier 3
    list.add(
        WeightedTraitDTO.builder()
            .tokenId(BURNED_TOKEN_2_ID)
            .traitId(WEIGHTED_TRAIT_BURNED_NFT2_3)
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_12_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_3)
            .build());
    // Multiplier 4
    list.add(
        WeightedTraitDTO.builder()
            .tokenId(BURNED_TOKEN_2_ID)
            .traitId(WEIGHTED_TRAIT_BURNED_NFT2_4)
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_13_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_4)
            .build());
    // Merge Multiplier
    list.add(
        WeightedTraitDTO.builder()
            .tokenId(BURNED_TOKEN_1_ID)
            .traitId(WEIGHTED_TRAIT_BURNED_NFT1_9)
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_14_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_9)
            .build());
    return list;
  }

  private List<WeightedTraitTypeWeightDTO> getBurnedNft2WeightedTraitTypeWeights() {
    List<WeightedTraitTypeWeightDTO> list = new ArrayList<>();
    // Multiplier 1
    list.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_10_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_1)
            .likelihood(100L)
            .value("11")
            .build());
    // Multiplier 2
    list.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_11_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_2)
            .likelihood(100L)
            .value("12")
            .build());
    // Multiplier 3
    list.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_12_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_3)
            .likelihood(100L)
            .value("13")
            .build());
    // Multiplier 4
    list.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_13_ID)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_4)
            .likelihood(100L)
            .value("14")
            .build());
    // Merge multiplier
    list.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_ID_9)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_4)
            .likelihood(100L)
            .value("15")
            .build());
    return list;
  }

  private List<WeightlessTraitDTO> getBurnedNft2WeightlessTraits() {
    List<WeightlessTraitDTO> list = new ArrayList<>();
    // Rarity 1
    list.add(
        WeightlessTraitDTO.builder()
            .tokenId(BURNED_TOKEN_2_ID)
            .traitId(WEIGHTLESS_TRAIT_BURNED_NFT2_1)
            .traitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_1)
            .value("14")
            .displayTypeValue("")
            .build());
    // Rarity 2
    list.add(
        WeightlessTraitDTO.builder()
            .tokenId(BURNED_TOKEN_2_ID)
            .traitId(WEIGHTLESS_TRAIT_BURNED_NFT2_2)
            .traitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_2)
            .value("15")
            .displayTypeValue("")
            .build());
    // Rarity 3
    list.add(
        WeightlessTraitDTO.builder()
            .tokenId(BURNED_TOKEN_2_ID)
            .traitId(WEIGHTLESS_TRAIT_BURNED_NFT2_3)
            .traitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_3)
            .value("16")
            .displayTypeValue("")
            .build());
    // Rarity 4
    list.add(
        WeightlessTraitDTO.builder()
            .tokenId(BURNED_TOKEN_2_ID)
            .traitId(WEIGHTLESS_TRAIT_BURNED_NFT2_4)
            .traitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_4)
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
