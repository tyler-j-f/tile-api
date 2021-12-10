package com.tylerfitzgerald.demo_api.erc721.token;

import static org.assertj.core.api.Assertions.assertThat;

import com.tylerfitzgerald.demo_api.sql.dtos.TokenDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightlessTraitDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightlessTraitTypeDTO;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TokenFacadeDTOTest {

  private TokenDTO tokenDTO;
  private List<WeightedTraitDTO> weightedTraits = new ArrayList<>();
  private WeightedTraitDTO weightedTrait1;
  private WeightedTraitDTO weightedTrait2;
  private List<WeightedTraitTypeDTO> weightedTraitTypes = new ArrayList<>();
  private WeightedTraitTypeDTO weightedTraitType1;
  private WeightedTraitTypeDTO weightedTraitType2;
  private List<WeightedTraitTypeWeightDTO> weightedTraitTypeWeights = new ArrayList<>();
  private WeightedTraitTypeWeightDTO weightedTraitTypeWeight1;
  private WeightedTraitTypeWeightDTO weightedTraitTypeWeight2;
  private List<WeightlessTraitDTO> weightlessTraits = new ArrayList<>();
  private WeightlessTraitDTO weightlessTrait1;
  private WeightlessTraitDTO weightlessTrait2;
  private List<WeightlessTraitTypeDTO> weightlessTraitTypes = new ArrayList<>();
  private WeightlessTraitTypeDTO weightlessTraitType1;
  private WeightlessTraitTypeDTO weightlessTraitType2;

  @BeforeEach
  public void setup() {
    // tokenDTO
    tokenDTO = Mockito.mock(TokenDTO.class);
    // weightedTraits
    weightedTrait1 = Mockito.mock(WeightedTraitDTO.class);
    weightedTrait2 = Mockito.mock(WeightedTraitDTO.class);
    weightedTraits.add(weightedTrait1);
    weightedTraits.add(weightedTrait2);
    // weightedTraitTypes
    weightedTraitType1 = Mockito.mock(WeightedTraitTypeDTO.class);
    weightedTraitType2 = Mockito.mock(WeightedTraitTypeDTO.class);
    weightedTraitTypes.add(weightedTraitType2);
    weightedTraitTypes.add(weightedTraitType1);
    // weightedTraitTypeWeights
    weightedTraitTypeWeight1 = Mockito.mock(WeightedTraitTypeWeightDTO.class);
    weightedTraitTypeWeight2 = Mockito.mock(WeightedTraitTypeWeightDTO.class);
    weightedTraitTypeWeights.add(weightedTraitTypeWeight1);
    weightedTraitTypeWeights.add(weightedTraitTypeWeight2);
    // weightlessTraits
    weightlessTrait1 = Mockito.mock(WeightlessTraitDTO.class);
    weightlessTrait2 = Mockito.mock(WeightlessTraitDTO.class);
    weightlessTraits.add(weightlessTrait1);
    weightlessTraits.add(weightlessTrait2);
    // weightlessTraitTypes
    weightlessTraitType1 = Mockito.mock(WeightlessTraitTypeDTO.class);
    weightlessTraitType2 = Mockito.mock(WeightlessTraitTypeDTO.class);
    weightlessTraitTypes.add(weightlessTraitType1);
    weightlessTraitTypes.add(weightlessTraitType2);
  }

  @Test
  void testBuilder() {
    TokenFacadeDTO tokenFacadeDTO =
        TokenFacadeDTO.builder()
            .tokenDTO(tokenDTO)
            .weightedTraits(weightedTraits)
            .weightedTraitTypes(weightedTraitTypes)
            .weightedTraitTypeWeights(weightedTraitTypeWeights)
            .weightlessTraits(weightlessTraits)
            .weightlessTraitTypes(weightlessTraitTypes)
            .build();
    // tokenDTO
    assertThat(tokenFacadeDTO.getTokenDTO()).isEqualTo(tokenDTO);
    // weightedTraits
    assertThat(tokenFacadeDTO.getWeightedTraits().get(0)).isEqualTo(weightedTrait1);
    assertThat(tokenFacadeDTO.getWeightedTraits().get(1)).isEqualTo(weightedTrait2);
    // weightedTraitTypes
    assertThat(tokenFacadeDTO.getWeightedTraitTypes().get(0)).isEqualTo(weightedTraitType2);
    assertThat(tokenFacadeDTO.getWeightedTraitTypes().get(1)).isEqualTo(weightedTraitType1);
    // weightedTraitTypeWeights
    assertThat(tokenFacadeDTO.getWeightedTraitTypeWeights().get(0))
        .isEqualTo(weightedTraitTypeWeight1);
    assertThat(tokenFacadeDTO.getWeightedTraitTypeWeights().get(1))
        .isEqualTo(weightedTraitTypeWeight2);
    // weightlessTraits
    assertThat(tokenFacadeDTO.getWeightlessTraits().get(0)).isEqualTo(weightlessTrait1);
    assertThat(tokenFacadeDTO.getWeightlessTraits().get(1)).isEqualTo(weightlessTrait2);
    // weightlessTraitTypes
    assertThat(tokenFacadeDTO.getWeightlessTraitTypes().get(0)).isEqualTo(weightlessTraitType1);
    assertThat(tokenFacadeDTO.getWeightlessTraitTypes().get(1)).isEqualTo(weightlessTraitType2);
  }
}
