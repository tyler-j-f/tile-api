package com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class WeightlessRepositoryTest {

  private JdbcTemplate jdbcTemplate;
  private BeanPropertyRowMapper beanPropertyRowMapper;

  // Value set 1
  private static final Long ID = 1L;
  private static final Long WEIGHTLESS_TRAIT_ID = 2L;
  private static final Long TOKEN_ID = 3L;
  private static final Long WEIGHTLESS_TRAIT_TYPE_ID = 4L;
  private static final String VALUE = "STRING_A";
  private static final String DISPLAY_TYPE_VALUE = "STRING_B";
  // Value set 2
  private static final Long ID_2 = 5L;
  private static final Long WEIGHTLESS_TRAIT_ID_2 = 6L;
  private static final Long TOKEN_ID_2 = 7L;
  private static final Long WEIGHTLESS_TRAIT_TYPE_ID_2 = 8L;
  private static final String VALUE_2 = "STRING_C";
  private static final String DISPLAY_TYPE_VALUE_2 = "STRING_D";

  @BeforeEach
  public void setup() {
    this.jdbcTemplate = Mockito.mock(JdbcTemplate.class);
    this.beanPropertyRowMapper = new BeanPropertyRowMapper(WeightlessTraitDTO.class);
  }

  @Test
  void testConstructor() {
    assertThat(new WeightlessTraitRepository(jdbcTemplate, beanPropertyRowMapper))
        .isInstanceOf(WeightlessTraitRepository.class);
  }

  @Test
  void testCreateNonExisting() {
    WeightlessTraitDTO weightlessTraitDTO =
        WeightlessTraitDTO.builder()
            .id(ID)
            .weightlessTraitId(WEIGHTLESS_TRAIT_ID)
            .tokenId(TOKEN_ID)
            .weightlessTraitTypeId(WEIGHTLESS_TRAIT_TYPE_ID)
            .value(VALUE)
            .displayTypeValue(DISPLAY_TYPE_VALUE)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightlessTraitRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                WEIGHTLESS_TRAIT_ID))
        .thenReturn(Stream.empty(), Stream.of(weightlessTraitDTO));
    Mockito.when(
            jdbcTemplate.update(
                WeightlessTraitRepository.CREATE_SQL,
                WEIGHTLESS_TRAIT_ID,
                TOKEN_ID,
                WEIGHTLESS_TRAIT_TYPE_ID,
                VALUE,
                DISPLAY_TYPE_VALUE))
        .thenReturn(1);
    WeightlessTraitDTO tokenDTOResult =
        new WeightlessTraitRepository(jdbcTemplate, beanPropertyRowMapper)
            .create(weightlessTraitDTO);
    Mockito.verify(jdbcTemplate, Mockito.times(2))
        .queryForStream(
            WeightlessTraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, WEIGHTLESS_TRAIT_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .update(
            WeightlessTraitRepository.CREATE_SQL,
            WEIGHTLESS_TRAIT_ID,
            TOKEN_ID,
            WEIGHTLESS_TRAIT_TYPE_ID,
            VALUE,
            DISPLAY_TYPE_VALUE);
    assertThat(tokenDTOResult).isEqualTo(weightlessTraitDTO);
  }

  @Test
  void testCreateExisting() {
    WeightlessTraitDTO weightlessTraitDTO =
        WeightlessTraitDTO.builder()
            .id(ID)
            .weightlessTraitId(WEIGHTLESS_TRAIT_ID)
            .tokenId(TOKEN_ID)
            .weightlessTraitTypeId(WEIGHTLESS_TRAIT_TYPE_ID)
            .value(VALUE)
            .displayTypeValue(DISPLAY_TYPE_VALUE)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightlessTraitRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                WEIGHTLESS_TRAIT_ID))
        .thenReturn(Stream.of(weightlessTraitDTO));
    WeightlessTraitDTO traitTypeWeightDTOResult =
        new WeightlessTraitRepository(jdbcTemplate, beanPropertyRowMapper)
            .create(weightlessTraitDTO);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(
            WeightlessTraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, WEIGHTLESS_TRAIT_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(0))
        .update(
            WeightlessTraitRepository.CREATE_SQL,
            WEIGHTLESS_TRAIT_ID,
            TOKEN_ID,
            WEIGHTLESS_TRAIT_TYPE_ID,
            VALUE,
            DISPLAY_TYPE_VALUE);
    assertThat(traitTypeWeightDTOResult).isEqualTo(null);
  }

  @Test
  void testRead() {
    WeightlessTraitDTO weightlessTraitDTO =
        WeightlessTraitDTO.builder()
            .id(ID)
            .weightlessTraitId(WEIGHTLESS_TRAIT_ID)
            .tokenId(TOKEN_ID)
            .weightlessTraitTypeId(WEIGHTLESS_TRAIT_TYPE_ID)
            .value(VALUE)
            .displayTypeValue(DISPLAY_TYPE_VALUE)
            .build();
    WeightlessTraitDTO weightlessTraitDTO2 =
        WeightlessTraitDTO.builder()
            .id(ID_2)
            .weightlessTraitId(WEIGHTLESS_TRAIT_ID_2)
            .tokenId(TOKEN_ID_2)
            .weightlessTraitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_2)
            .value(VALUE_2)
            .displayTypeValue(DISPLAY_TYPE_VALUE_2)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(WeightlessTraitRepository.READ_SQL, beanPropertyRowMapper))
        .thenReturn(Stream.of(weightlessTraitDTO, weightlessTraitDTO2));
    List<WeightlessTraitDTO> tokens =
        new WeightlessTraitRepository(jdbcTemplate, beanPropertyRowMapper).read();
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(WeightlessTraitRepository.READ_SQL, beanPropertyRowMapper);
    assertThat(tokens.get(0)).isEqualTo(weightlessTraitDTO);
    assertThat(tokens.get(1)).isEqualTo(weightlessTraitDTO2);
  }

  @Test
  void testReadEmptyTable() {
    Mockito.when(
            jdbcTemplate.queryForStream(WeightlessTraitRepository.READ_SQL, beanPropertyRowMapper))
        .thenReturn(Stream.empty());
    List<WeightlessTraitDTO> tokens =
        new WeightlessTraitRepository(jdbcTemplate, beanPropertyRowMapper).read();
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(WeightlessTraitRepository.READ_SQL, beanPropertyRowMapper);
    assertThat(tokens.isEmpty()).isEqualTo(true);
  }

  @Test
  void testReadExistingById() {
    WeightlessTraitDTO weightlessTraitDTO =
        WeightlessTraitDTO.builder()
            .id(ID)
            .weightlessTraitId(WEIGHTLESS_TRAIT_ID)
            .tokenId(TOKEN_ID)
            .weightlessTraitTypeId(WEIGHTLESS_TRAIT_TYPE_ID)
            .value(VALUE)
            .displayTypeValue(DISPLAY_TYPE_VALUE)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightlessTraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, ID))
        .thenReturn(Stream.of(weightlessTraitDTO));
    WeightlessTraitDTO tokenDTOResult =
        new WeightlessTraitRepository(jdbcTemplate, beanPropertyRowMapper).readById(ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(WeightlessTraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, ID);
    assertThat(tokenDTOResult).isEqualTo(weightlessTraitDTO);
  }

  @Test
  void testReadNonExistingById() {
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightlessTraitRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                WEIGHTLESS_TRAIT_ID))
        .thenReturn(Stream.empty());
    WeightlessTraitDTO tokenDTOResult =
        new WeightlessTraitRepository(jdbcTemplate, beanPropertyRowMapper).readById(TOKEN_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(WeightlessTraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TOKEN_ID);
    assertThat(tokenDTOResult).isEqualTo(null);
  }

  @Test
  void testUpdateExistingEntry() {
    WeightlessTraitDTO weightlessTraitDTO =
        WeightlessTraitDTO.builder()
            .id(ID)
            .weightlessTraitId(WEIGHTLESS_TRAIT_ID)
            .tokenId(TOKEN_ID)
            .weightlessTraitTypeId(WEIGHTLESS_TRAIT_TYPE_ID)
            .value(VALUE)
            .displayTypeValue(DISPLAY_TYPE_VALUE)
            .build();
    // weightlessTraitDTO2 Will have the same id and WEIGHTLESS_TRAIT_ID as weightlessTraitDTO
    WeightlessTraitDTO weightlessTraitDTO2 =
        WeightlessTraitDTO.builder()
            .id(ID)
            .weightlessTraitId(WEIGHTLESS_TRAIT_ID)
            .tokenId(TOKEN_ID_2)
            .weightlessTraitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_2)
            .value(VALUE_2)
            .displayTypeValue(DISPLAY_TYPE_VALUE_2)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightlessTraitRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                WEIGHTLESS_TRAIT_ID))
        .thenReturn(Stream.of(weightlessTraitDTO), Stream.of(weightlessTraitDTO2));
    Mockito.when(
            jdbcTemplate.update(
                WeightlessTraitRepository.UPDATE_SQL,
                TOKEN_ID_2,
                WEIGHTLESS_TRAIT_TYPE_ID_2,
                VALUE_2,
                DISPLAY_TYPE_VALUE_2,
                WEIGHTLESS_TRAIT_ID))
        .thenReturn(1);
    WeightlessTraitDTO tokenDTOResults =
        new WeightlessTraitRepository(jdbcTemplate, beanPropertyRowMapper)
            .update(weightlessTraitDTO2);
    Mockito.verify(jdbcTemplate, Mockito.times(2))
        .queryForStream(
            WeightlessTraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, WEIGHTLESS_TRAIT_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .update(
            WeightlessTraitRepository.UPDATE_SQL,
            TOKEN_ID_2,
            WEIGHTLESS_TRAIT_TYPE_ID_2,
            VALUE_2,
            DISPLAY_TYPE_VALUE_2,
            WEIGHTLESS_TRAIT_ID);
    assertThat(tokenDTOResults).isEqualTo(weightlessTraitDTO2);
  }

  @Test
  void testUpdateNonExistingEntry() {
    WeightlessTraitDTO weightlessTraitDTO =
        WeightlessTraitDTO.builder()
            .id(ID)
            .weightlessTraitId(WEIGHTLESS_TRAIT_ID)
            .tokenId(TOKEN_ID)
            .weightlessTraitTypeId(WEIGHTLESS_TRAIT_TYPE_ID)
            .value(VALUE)
            .displayTypeValue(DISPLAY_TYPE_VALUE)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightlessTraitRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                WEIGHTLESS_TRAIT_ID))
        .thenReturn(Stream.empty());
    WeightlessTraitDTO tokenDTOResults =
        new WeightlessTraitRepository(jdbcTemplate, beanPropertyRowMapper)
            .update(weightlessTraitDTO);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(
            WeightlessTraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, WEIGHTLESS_TRAIT_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(0))
        .update(
            WeightlessTraitRepository.UPDATE_SQL,
            TOKEN_ID_2,
            WEIGHTLESS_TRAIT_TYPE_ID_2,
            VALUE_2,
            DISPLAY_TYPE_VALUE_2,
            WEIGHTLESS_TRAIT_ID);
    assertThat(tokenDTOResults).isEqualTo(null);
  }

  @Test
  void testDeleteExistingEntry() {
    WeightlessTraitDTO weightlessTraitDTO =
        WeightlessTraitDTO.builder()
            .id(ID)
            .weightlessTraitId(WEIGHTLESS_TRAIT_ID)
            .tokenId(TOKEN_ID)
            .weightlessTraitTypeId(WEIGHTLESS_TRAIT_TYPE_ID)
            .value(VALUE)
            .displayTypeValue(DISPLAY_TYPE_VALUE)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightlessTraitRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                WEIGHTLESS_TRAIT_ID))
        .thenReturn(Stream.of(weightlessTraitDTO), Stream.empty());
    boolean isDeletedSuccessfully =
        new WeightlessTraitRepository(jdbcTemplate, beanPropertyRowMapper)
            .delete(weightlessTraitDTO);
    /*
    Read by id is called twice. Once at the start of the delete method and once at the end of the delete method.
    */
    Mockito.verify(jdbcTemplate, Mockito.times(2))
        .queryForStream(
            WeightlessTraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, WEIGHTLESS_TRAIT_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .update(WeightlessTraitRepository.DELETE_BY_ID_SQL, WEIGHTLESS_TRAIT_ID);
    assertThat(isDeletedSuccessfully).isEqualTo(true);
  }

  @Test
  void testDeleteNonExistingEntry() {
    WeightlessTraitDTO weightlessTraitDTO =
        WeightlessTraitDTO.builder()
            .id(ID)
            .weightlessTraitId(WEIGHTLESS_TRAIT_ID)
            .tokenId(TOKEN_ID)
            .weightlessTraitTypeId(WEIGHTLESS_TRAIT_TYPE_ID)
            .value(VALUE)
            .displayTypeValue(DISPLAY_TYPE_VALUE)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightlessTraitRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                WEIGHTLESS_TRAIT_ID))
        .thenReturn(Stream.empty());
    boolean isDeletedSuccessfully =
        new WeightlessTraitRepository(jdbcTemplate, beanPropertyRowMapper)
            .delete(weightlessTraitDTO);
    /*
    Read by id is called twice. Once at the start of the delete method and once at the end of the delete method.
     */
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(
            WeightlessTraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, WEIGHTLESS_TRAIT_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(0))
        .update(WeightlessTraitRepository.DELETE_BY_ID_SQL, WEIGHTLESS_TRAIT_ID);
    assertThat(isDeletedSuccessfully).isEqualTo(false);
  }
}
