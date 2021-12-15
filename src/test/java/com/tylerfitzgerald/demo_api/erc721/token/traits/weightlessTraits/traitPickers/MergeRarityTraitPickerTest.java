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
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MergeRarityTraitPickerTest {
  @Mock private WeightedTraitsListFinder weightedTraitListHelper;
  @Mock private WeightlessTraitsListFinder weightlessTraitInListFinder;
  @Mock private WeightedTraitTypeWeightsListFinder weightedTraitTypeWeightsListFinder;
  @InjectMocks private MergeRarityTraitPicker mergeRarityTraitPicker = new MergeRarityTraitPicker();
  private int WEIGHTLESS_TRAIT_TYPE_ID_1 = WeightlessTraitTypeConstants.TILE_1_RARITY;
  private int WEIGHTLESS_TRAIT_TYPE_ID_2 = WeightlessTraitTypeConstants.TILE_1_RARITY;
  private int WEIGHTLESS_TRAIT_TYPE_ID_3 = WeightlessTraitTypeConstants.TILE_1_RARITY;
  private int WEIGHTLESS_TRAIT_TYPE_ID_4 = WeightlessTraitTypeConstants.TILE_1_RARITY;
  private int WEIGHTED_TRAIT_TYPE_ID_1 = WeightedTraitTypeConstants.TILE_1_MULTIPLIER;
  private int WEIGHTED_TRAIT_TYPE_ID_2 = WeightedTraitTypeConstants.TILE_2_MULTIPLIER;
  private int WEIGHTED_TRAIT_TYPE_ID_3 = WeightedTraitTypeConstants.TILE_3_MULTIPLIER;
  private int WEIGHTED_TRAIT_TYPE_ID_4 = WeightedTraitTypeConstants.TILE_4_MULTIPLIER;
  private Long BURNED_TOKEN_1_ID = 202L;
  private Long BURNED_TOKEN_2_ID = 203L;
  private Long WEIGHTED_TRAIT_TYPE_WEIGHT_1_ID = 212L;
  private Long WEIGHTED_TRAIT_TYPE_WEIGHT_2_ID = 213L;
  private Long WEIGHTED_TRAIT_TYPE_WEIGHT_3_ID = 214L;
  private Long WEIGHTED_TRAIT_TYPE_WEIGHT_4_ID = 215L;
  private WeightlessTraitPickerContext context;
  private TokenFacadeDTO burnedNft1;
  private TokenFacadeDTO burnedNft2;
  // Trait Ids
  private Long BURNED_NFT1_TRAIT_TYPE_WEIGHT_ID_1 = 204L;
  private Long BURNED_NFT1_TRAIT_TYPE_WEIGHT_ID_2 = 205L;
  private Long BURNED_NFT1_TRAIT_TYPE_WEIGHT_ID_3 = 206L;
  private Long BURNED_NFT1_TRAIT_TYPE_WEIGHT_ID_4 = 207L;
  private Long BURNED_NFT2_TRAIT_TYPE_WEIGHT_ID_1 = 208L;
  private Long BURNED_NFT2_TRAIT_TYPE_WEIGHT_ID_2 = 209L;
  private Long BURNED_NFT2_TRAIT_TYPE_WEIGHT_ID_3 = 210L;
  private Long BURNED_NFT2_TRAIT_TYPE_WEIGHT_ID_4 = 211L;

  @BeforeEach
  public void setup() throws WeightlessTraitPickerException {
    setupBurnedNft1();
    setupBurnedNft2();
    setupContext();
  }

  @Test
  public void testMergeRarityTraitPicker() throws WeightlessTraitPickerException {
    assertThat(mergeRarityTraitPicker.getValue(context))
        .isEqualTo(MERGE_RARITY_TRAIT_PICKER_VALUE_1);
    assertThat(mergeRarityTraitPicker.getValue(context)).isEqualTo("");
  }

  private void setupBurnedNft1() {
    burnedNft1 =
        TokenFacadeDTO.builder()
            .tokenDTO(
                TokenDTO.builder().tokenId(BURNED_TOKEN_1_ID).build()
            )
            .weightedTraitTypeWeights(getBurnedNft1WeightedTraitTypeWeights())
            .weightedTraits(getBurnedNft1WeightedTraits())
            .weightlessTraits(getBurnedNft1WeightlessTraits())
            .build();
  }

  private List<WeightedTraitDTO> getBurnedNft1WeightedTraits() {
    List<WeightedTraitDTO> list = new ArrayList<>();
    list.add(WeightedTraitDTO.builder().tokenId(BURNED_TOKEN_1_ID).traitTypeWeightId(null).traitTypeId(null).traitId(null).build());
    return list;
  }

  private List<WeightedTraitTypeWeightDTO> getBurnedNft1WeightedTraitTypeWeights() {
    List<WeightedTraitTypeWeightDTO> list = new ArrayList<>();
    list.add(WeightedTraitTypeWeightDTO.builder().traitTypeWeightId(BURNED_NFT1_TRAIT_TYPE_WEIGHT_ID_1).traitTypeId().build());
    return list;
  }

  private List<WeightlessTraitDTO> getBurnedNft1WeightlessTraits() {
    List<WeightlessTraitDTO> list = new ArrayList<>();
    list.add(WeightlessTraitDTO.builder().build());
    list.add(WeightlessTraitDTO.builder().build());
    return list;
  }

  private void setupBurnedNft2() {
    burnedNft2 =
        TokenFacadeDTO.builder()
            .tokenDTO(
                TokenDTO.builder().tokenId(BURNED_TOKEN_2_ID).build()
            )
            .weightedTraitTypeWeights(getBurnedNft1WeightedTraitTypeWeights())
            .weightedTraits(getBurnedNft1WeightedTraits())
            .weightlessTraits(getBurnedNft1WeightlessTraits())
            .build();
  }
//
//  private List<WeightedTraitDTO> getBurnedNft2WeightedTraits() {
//    List<WeightedTraitDTO> list = new ArrayList<>();
//    list.add(WeightedTraitDTO.builder().build());
//    list.add(WeightedTraitDTO.builder().build());
//    return list;
//  }
//
//  private List<WeightedTraitTypeWeightDTO> getBurnedNft2WeightedTraitTypeWeights() {
//    List<WeightedTraitTypeWeightDTO> list = new ArrayList<>();
//    list.add(WeightedTraitTypeWeightDTO.builder().build());
//    list.add(WeightedTraitTypeWeightDTO.builder().build());
//    return list;
//  }
//
//  private List<WeightlessTraitDTO> getBurnedNft2WeightlessTraits() {
//    List<WeightlessTraitDTO> list = new ArrayList<>();
//    list.add(WeightlessTraitDTO.builder().build());
//    list.add(WeightlessTraitDTO.builder().build());
//    return list;
//  }

  private void setupContext() {
    context =
        WeightlessTraitPickerContext.builder()
            .traitTypeId(TRAIT_TYPE_ID)
            .burnedNft1(burnedNft1)
            .burnedNft2(burnedNft2)
            .build();
  }
}
