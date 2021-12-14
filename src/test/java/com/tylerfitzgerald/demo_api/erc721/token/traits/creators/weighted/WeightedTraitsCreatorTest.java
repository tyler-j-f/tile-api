package com.tylerfitzgerald.demo_api.erc721.token.traits.creators.weighted;

import static org.assertj.core.api.Assertions.assertThat;

import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.TraitsCreatorContext;
import com.tylerfitzgerald.demo_api.etc.lsitFinders.WeightedTraitTypeWeightsListFinder;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightedTraitRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WeightedTraitsCreatorTest {
  private final Long WEIGHTED_TRAIT_TYPE_WEIGHT_ID_1 = 3L;
  private final Long WEIGHTED_TRAIT_TYPE_WEIGHT_ID_2 = 4L;
  private final Long WEIGHTED_TRAIT_TYPE_ID_1 = 5L;
  private final Long WEIGHTED_TRAIT_TYPE_ID_2 = 6L;
  private final Long WEIGHTED_TRAIT_TYPE_WEIGHT_LIKELIHOOD_1A = 30L;
  private final Long WEIGHTED_TRAIT_TYPE_WEIGHT_LIKELIHOOD_1B = 70L;
  private final Long WEIGHTED_TRAIT_TYPE_WEIGHT_LIKELIHOOD_2A = 10L;
  private final Long WEIGHTED_TRAIT_TYPE_WEIGHT_LIKELIHOOD_2B = 90L;
  private final Long TOKEN_ID = 11L;
  private final Long SEED_FOR_TRAITS = 123123321L;
  private final Long WEIGHTED_TRAIT_SIZE = 12L;
  private final Long NEW_TRAIT_ID_1 = 13L;
  private final Long NEW_TRAIT_ID_2 = 14L;
  private WeightedTraitDTO newTrait1;
  private WeightedTraitDTO newTrait2;
  private List<WeightedTraitDTO> newTraits = new ArrayList<>();
  private List<WeightedTraitTypeWeightDTO> mockWeightedTraitTypeWeights = new ArrayList<>();
  private List<WeightedTraitTypeDTO> mockWeightedTraitTypes = new ArrayList<>();
  @Mock protected WeightedTraitRepository weightedTraitRepository;
  @Mock protected WeightedTraitTypeWeightsListFinder weightedTraitTypeWeightsListFinder;
  @InjectMocks private WeightedTraitsCreator weightedTraitsCreator = new WeightedTraitsCreator();
  @Captor ArgumentCaptor<WeightedTraitDTO> weightedTraitCaptor;

  @Test
  void testCreateTraits() {
    setUpMocks();
    weightedTraitsCreator.createTraits(buildContext());
    assertions();
  }

  private void mockNewTraits() {
    newTrait1 =
        WeightedTraitDTO.builder()
            .tokenId(TOKEN_ID)
            .traitId(NEW_TRAIT_ID_1)
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_ID_1)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_1)
            .build();
    newTrait2 =
        WeightedTraitDTO.builder()
            .tokenId(TOKEN_ID)
            .traitId(NEW_TRAIT_ID_2)
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_ID_2)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_2)
            .build();
    newTraits.add(newTrait1);
    newTraits.add(newTrait2);
  }

  private void setUpMocks() {
    mockNewTraits();
    mockWeightedTraitTypes();
    mockWeightedTraitTypeWeights();
    mockWeightedTraitTypeWeightsListFinder();
    mockTraitRepositoryCreate();
    Mockito.when(weightedTraitRepository.getCount())
        .thenReturn(WEIGHTED_TRAIT_SIZE, WEIGHTED_TRAIT_SIZE + 1);
  }

  private void mockTraitRepositoryCreate() {
    Mockito.when(weightedTraitRepository.create(Mockito.any())).thenReturn(newTrait1, newTrait2);
  }

  private void mockWeightedTraitTypeWeightsListFinder() {
    List<WeightedTraitTypeWeightDTO> traitsList1 = new ArrayList<>(),
        traitsList2 = new ArrayList<>();
    traitsList1.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_ID_1)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_1)
            .likelihood(WEIGHTED_TRAIT_TYPE_WEIGHT_LIKELIHOOD_1A)
            .build());
    traitsList1.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_ID_1)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_1)
            .likelihood(WEIGHTED_TRAIT_TYPE_WEIGHT_LIKELIHOOD_1B)
            .build());
    traitsList2.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_ID_2)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_2)
            .likelihood(WEIGHTED_TRAIT_TYPE_WEIGHT_LIKELIHOOD_2A)
            .build());
    traitsList2.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_ID_2)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_2)
            .likelihood(WEIGHTED_TRAIT_TYPE_WEIGHT_LIKELIHOOD_2B)
            .build());
    Mockito.when(
            weightedTraitTypeWeightsListFinder.findByTraitTypeId(
                mockWeightedTraitTypeWeights, WEIGHTED_TRAIT_TYPE_ID_1))
        .thenReturn(traitsList1);
    Mockito.when(
            weightedTraitTypeWeightsListFinder.findByTraitTypeId(
                mockWeightedTraitTypeWeights, WEIGHTED_TRAIT_TYPE_ID_2))
        .thenReturn(traitsList2);
  }

  private void assertions() {
    Mockito.verify(weightedTraitRepository, Mockito.times(newTraits.size())).getCount();
    Mockito.verify(weightedTraitRepository, Mockito.times(newTraits.size())).create(Mockito.any());
    assertThat(weightedTraitsCreator.getCreatedWeightedTraits()).isEqualTo(newTraits);
  }

  private TraitsCreatorContext buildContext() {
    return TraitsCreatorContext.builder()
        .tokenId(TOKEN_ID)
        .seedForTraits(SEED_FOR_TRAITS)
        .weightedTraitTypes(mockWeightedTraitTypes)
        .weightedTraitTypeWeights(mockWeightedTraitTypeWeights)
        .build();
  }

  private void mockWeightedTraitTypes() {
    mockWeightedTraitTypes.add(
        WeightedTraitTypeDTO.builder().traitTypeId(WEIGHTED_TRAIT_TYPE_ID_1).build());
    mockWeightedTraitTypes.add(
        WeightedTraitTypeDTO.builder().traitTypeId(WEIGHTED_TRAIT_TYPE_ID_2).build());
    return;
  }

  private void mockWeightedTraitTypeWeights() {
    mockWeightedTraitTypeWeights.add(
        WeightedTraitTypeWeightDTO.builder().traitTypeId(WEIGHTED_TRAIT_TYPE_WEIGHT_ID_1).build());
    mockWeightedTraitTypeWeights.add(
        WeightedTraitTypeWeightDTO.builder().traitTypeId(WEIGHTED_TRAIT_TYPE_WEIGHT_ID_2).build());
    return;
  }
}
