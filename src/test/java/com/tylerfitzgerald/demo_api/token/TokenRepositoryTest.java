package com.tylerfitzgerald.demo_api.token;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TokenRepositoryTest {

  private JdbcTemplate jdbcTemplate;
  private BeanPropertyRowMapper beanPropertyRowMapper;
  // Value set 1
  private static Long ID = 1L;
  private static Long TOKEN_ID = 2L;
  private static Long SALE_ID = 3L;
  private static String NAME = "STRING_A";
  private static String DESCRIPTION = "STRING_B";
  private static String EXTERNAL_URL = "STRING_C";
  private static String IMAGE_URL = "STRING_D";
  // Value set 2
  private static Long ID_2 = 1L;
  private static Long TOKEN_ID_2 = 2L;
  private static Long SALE_ID_2 = 3L;
  private static String NAME_2 = "STRING_A";
  private static String DESCRIPTION_2 = "STRING_B";
  private static String EXTERNAL_URL_2 = "STRING_C";
  private static String IMAGE_URL_2 = "STRING_D";

  @BeforeEach
  public void setup() {
    this.jdbcTemplate = Mockito.mock(JdbcTemplate.class);
    this.beanPropertyRowMapper = new BeanPropertyRowMapper(TokenDTO.class);
  }

  @Test
  void testConstructor() {
    assertThat(new TokenRepository(jdbcTemplate, beanPropertyRowMapper))
        .isInstanceOf(TokenRepository.class);
  }

  //  @Test
  //  void testCreateNonExisting() {
  //    TraitTypeWeightDTO traitWeightTypeDTO =
  //        TraitTypeWeightDTO.builder()
  //            .id(ID)
  //            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
  //            .traitTypeId(TRAIT_TYPE_ID)
  //            .likelihood(LIKELIHOOD)
  //            .value(VALUE)
  //            .displayTypeValue(DISPLAY_TYPE_VALUE)
  //            .build();
  //    Mockito.when(
  //            jdbcTemplate.queryForStream(
  //                TraitTypeWeightRepository.READ_BY_ID_SQL,
  //                beanPropertyRowMapper,
  //                TRAIT_TYPE_WEIGHT_ID))
  //        .thenReturn(Stream.empty(), Stream.of(traitWeightTypeDTO));
  //    Mockito.when(
  //            jdbcTemplate.update(
  //                TraitTypeWeightRepository.CREATE_SQL,
  //                TRAIT_TYPE_WEIGHT_ID,
  //                TRAIT_TYPE_ID,
  //                LIKELIHOOD,
  //                VALUE,
  //                DISPLAY_TYPE_VALUE))
  //        .thenReturn(1);
  //    TraitTypeWeightDTO traitTypeDTOResult =
  //        new TraitTypeWeightRepository(jdbcTemplate, beanPropertyRowMapper)
  //            .create(traitWeightTypeDTO);
  //    Mockito.verify(jdbcTemplate, Mockito.times(2))
  //        .queryForStream(
  //            TraitTypeWeightRepository.READ_BY_ID_SQL, beanPropertyRowMapper,
  // TRAIT_TYPE_WEIGHT_ID);
  //    Mockito.verify(jdbcTemplate, Mockito.times(1))
  //        .update(
  //            TraitTypeWeightRepository.CREATE_SQL,
  //            TRAIT_TYPE_WEIGHT_ID,
  //            TRAIT_TYPE_ID,
  //            LIKELIHOOD,
  //            VALUE,
  //            DISPLAY_TYPE_VALUE);
  //    assertThat(traitTypeDTOResult).isEqualTo(traitWeightTypeDTO);
  //  }

}
