package com.tylerfitzgerald.demo_api.sql.tblWeightlessTraitTypes;

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
public class WeightlessTraitTypeRepositoryTest {

  private JdbcTemplate jdbcTemplate;
  private BeanPropertyRowMapper beanPropertyRowMapper;

  // Value set 1
  private static final Long ID = 1L;
  private static final Long WEIGHTLESS_TRAIT_TYPE_ID = 2L;
  private static final String NAME = "STRING_A";
  private static final String DESCRIPTION = "STRING_B";
  // Value set 2
  private static final Long ID_2 = 3L;
  private static final Long WEIGHTLESS_TRAIT_TYPE_ID_2 = 4L;
  private static final String NAME_2 = "STRING_C";
  private static final String DESCRIPTION_2 = "STRING_D";

  @BeforeEach
  public void setup() {
    this.jdbcTemplate = Mockito.mock(JdbcTemplate.class);
    this.beanPropertyRowMapper = new BeanPropertyRowMapper(WeightlessTraitTypeDTO.class);
  }

  @Test
  void testConstructor() {
    assertThat(new WeightlessTraitTypeRepository(jdbcTemplate, beanPropertyRowMapper))
        .isInstanceOf(WeightlessTraitTypeRepository.class);
  }

  @Test
  void testCreateNonExisting() {
    WeightlessTraitTypeDTO weightlessTraitTypeDTO =
        WeightlessTraitTypeDTO.builder()
            .id(ID)
            .weightlessTraitTypeId(WEIGHTLESS_TRAIT_TYPE_ID)
            .weightlessTraitTypeName(NAME)
            .description(DESCRIPTION)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightlessTraitTypeRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                WEIGHTLESS_TRAIT_TYPE_ID))
        .thenReturn(Stream.empty(), Stream.of(weightlessTraitTypeDTO));
    Mockito.when(
            jdbcTemplate.update(
                WeightlessTraitTypeRepository.CREATE_SQL,
                WEIGHTLESS_TRAIT_TYPE_ID,
                NAME,
                DESCRIPTION))
        .thenReturn(1);
    WeightlessTraitTypeDTO tokenDTOResult =
        new WeightlessTraitTypeRepository(jdbcTemplate, beanPropertyRowMapper)
            .create(weightlessTraitTypeDTO);
    Mockito.verify(jdbcTemplate, Mockito.times(2))
        .queryForStream(
            WeightlessTraitTypeRepository.READ_BY_ID_SQL,
            beanPropertyRowMapper,
            WEIGHTLESS_TRAIT_TYPE_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .update(
            WeightlessTraitTypeRepository.CREATE_SQL, WEIGHTLESS_TRAIT_TYPE_ID, NAME, DESCRIPTION);
    assertThat(tokenDTOResult).isEqualTo(weightlessTraitTypeDTO);
  }

  @Test
  void testCreateExisting() {
    WeightlessTraitTypeDTO weightlessTraitTypeDTO =
        WeightlessTraitTypeDTO.builder()
            .id(ID)
            .weightlessTraitTypeId(WEIGHTLESS_TRAIT_TYPE_ID)
            .weightlessTraitTypeName(NAME)
            .description(DESCRIPTION)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightlessTraitTypeRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                WEIGHTLESS_TRAIT_TYPE_ID))
        .thenReturn(Stream.of(weightlessTraitTypeDTO));
    WeightlessTraitTypeDTO traitTypeWeightDTOResult =
        new WeightlessTraitTypeRepository(jdbcTemplate, beanPropertyRowMapper)
            .create(weightlessTraitTypeDTO);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(
            WeightlessTraitTypeRepository.READ_BY_ID_SQL,
            beanPropertyRowMapper,
            WEIGHTLESS_TRAIT_TYPE_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(0))
        .update(
            WeightlessTraitTypeRepository.CREATE_SQL, WEIGHTLESS_TRAIT_TYPE_ID, NAME, DESCRIPTION);
    assertThat(traitTypeWeightDTOResult).isEqualTo(null);
  }

  @Test
  void testRead() {
    WeightlessTraitTypeDTO weightlessTraitTypeDTO =
        WeightlessTraitTypeDTO.builder()
            .id(ID)
            .weightlessTraitTypeId(WEIGHTLESS_TRAIT_TYPE_ID)
            .weightlessTraitTypeName(NAME)
            .description(DESCRIPTION)
            .build();
    WeightlessTraitTypeDTO weightlessTraitTypeDTO2 =
        WeightlessTraitTypeDTO.builder()
            .id(ID_2)
            .weightlessTraitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_2)
            .weightlessTraitTypeName(NAME_2)
            .description(DESCRIPTION_2)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightlessTraitTypeRepository.READ_SQL, beanPropertyRowMapper))
        .thenReturn(Stream.of(weightlessTraitTypeDTO, weightlessTraitTypeDTO2));
    List<WeightlessTraitTypeDTO> weightlessTraitTypes =
        new WeightlessTraitTypeRepository(jdbcTemplate, beanPropertyRowMapper).read();
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(WeightlessTraitTypeRepository.READ_SQL, beanPropertyRowMapper);
    assertThat(weightlessTraitTypes.get(0)).isEqualTo(weightlessTraitTypeDTO);
    assertThat(weightlessTraitTypes.get(1)).isEqualTo(weightlessTraitTypeDTO2);
  }

  @Test
  void testReadEmptyTable() {
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightlessTraitTypeRepository.READ_SQL, beanPropertyRowMapper))
        .thenReturn(Stream.empty());
    List<WeightlessTraitTypeDTO> weightlessTraitTypes =
        new WeightlessTraitTypeRepository(jdbcTemplate, beanPropertyRowMapper).read();
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(WeightlessTraitTypeRepository.READ_SQL, beanPropertyRowMapper);
    assertThat(weightlessTraitTypes.isEmpty()).isEqualTo(true);
  }

  @Test
  void testReadExistingById() {
    WeightlessTraitTypeDTO weightlessTraitTypeDTO =
        WeightlessTraitTypeDTO.builder()
            .id(ID)
            .weightlessTraitTypeId(WEIGHTLESS_TRAIT_TYPE_ID)
            .weightlessTraitTypeName(NAME)
            .description(DESCRIPTION)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightlessTraitTypeRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                WEIGHTLESS_TRAIT_TYPE_ID))
        .thenReturn(Stream.of(weightlessTraitTypeDTO));
    WeightlessTraitTypeDTO tokenDTOResult =
        new WeightlessTraitTypeRepository(jdbcTemplate, beanPropertyRowMapper)
            .readById(WEIGHTLESS_TRAIT_TYPE_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(
            WeightlessTraitTypeRepository.READ_BY_ID_SQL,
            beanPropertyRowMapper,
            WEIGHTLESS_TRAIT_TYPE_ID);
    assertThat(tokenDTOResult).isEqualTo(weightlessTraitTypeDTO);
  }

  @Test
  void testReadNonExistingById() {
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightlessTraitTypeRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                WEIGHTLESS_TRAIT_TYPE_ID))
        .thenReturn(Stream.empty());
    WeightlessTraitTypeDTO tokenDTOResult =
        new WeightlessTraitTypeRepository(jdbcTemplate, beanPropertyRowMapper)
            .readById(WEIGHTLESS_TRAIT_TYPE_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(
            WeightlessTraitTypeRepository.READ_BY_ID_SQL,
            beanPropertyRowMapper,
            WEIGHTLESS_TRAIT_TYPE_ID);
    assertThat(tokenDTOResult).isEqualTo(null);
  }

  @Test
  void testUpdateExistingEntry() {
    WeightlessTraitTypeDTO weightlessTraitTypeDTO =
        WeightlessTraitTypeDTO.builder()
            .id(ID)
            .weightlessTraitTypeId(WEIGHTLESS_TRAIT_TYPE_ID)
            .weightlessTraitTypeName(NAME)
            .description(DESCRIPTION)
            .build();
    /* weightlessTraitTypeDTO2 Will have the same id and WEIGHTLESS_TRAIT_TYPE_ID as weightlessTraitTypeDTO */
    WeightlessTraitTypeDTO weightlessTraitTypeDTO2 =
        WeightlessTraitTypeDTO.builder()
            .id(ID)
            .weightlessTraitTypeId(WEIGHTLESS_TRAIT_TYPE_ID)
            .weightlessTraitTypeName(NAME_2)
            .description(DESCRIPTION_2)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightlessTraitTypeRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                WEIGHTLESS_TRAIT_TYPE_ID))
        .thenReturn(Stream.of(weightlessTraitTypeDTO), Stream.of(weightlessTraitTypeDTO2));
    Mockito.when(
            jdbcTemplate.update(
                WeightlessTraitTypeRepository.UPDATE_SQL,
                NAME_2,
                DESCRIPTION_2,
                WEIGHTLESS_TRAIT_TYPE_ID))
        .thenReturn(1);
    WeightlessTraitTypeDTO tokenDTOResults =
        new WeightlessTraitTypeRepository(jdbcTemplate, beanPropertyRowMapper)
            .update(weightlessTraitTypeDTO2);
    Mockito.verify(jdbcTemplate, Mockito.times(2))
        .queryForStream(
            WeightlessTraitTypeRepository.READ_BY_ID_SQL,
            beanPropertyRowMapper,
            WEIGHTLESS_TRAIT_TYPE_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .update(
            WeightlessTraitTypeRepository.UPDATE_SQL,
            NAME_2,
            DESCRIPTION_2,
            WEIGHTLESS_TRAIT_TYPE_ID);
    assertThat(tokenDTOResults).isEqualTo(weightlessTraitTypeDTO2);
  }

  @Test
  void testUpdateNonExistingEntry() {
    WeightlessTraitTypeDTO weightlessTraitTypeDTO =
        WeightlessTraitTypeDTO.builder()
            .id(ID)
            .weightlessTraitTypeId(WEIGHTLESS_TRAIT_TYPE_ID)
            .weightlessTraitTypeName(NAME)
            .description(DESCRIPTION)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightlessTraitTypeRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                WEIGHTLESS_TRAIT_TYPE_ID))
        .thenReturn(Stream.empty());
    Mockito.when(
            jdbcTemplate.update(
                WeightlessTraitTypeRepository.UPDATE_SQL,
                NAME_2,
                DESCRIPTION_2,
                WEIGHTLESS_TRAIT_TYPE_ID))
        .thenReturn(1);
    WeightlessTraitTypeDTO tokenDTOResults =
        new WeightlessTraitTypeRepository(jdbcTemplate, beanPropertyRowMapper)
            .update(weightlessTraitTypeDTO);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(
            WeightlessTraitTypeRepository.READ_BY_ID_SQL,
            beanPropertyRowMapper,
            WEIGHTLESS_TRAIT_TYPE_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(0))
        .update(
            WeightlessTraitTypeRepository.UPDATE_SQL,
            NAME_2,
            DESCRIPTION_2,
            WEIGHTLESS_TRAIT_TYPE_ID);
    assertThat(tokenDTOResults).isEqualTo(null);
  }

    @Test
    void testDeleteExistingEntry() {
      WeightlessTraitTypeDTO weightlessTraitTypeDTO =
          WeightlessTraitTypeDTO.builder()
              .id(ID)
              .weightlessTraitTypeId(WEIGHTLESS_TRAIT_TYPE_ID)
              .weightlessTraitTypeName(NAME)
              .description(DESCRIPTION)
              .build();
      Mockito.when(
              jdbcTemplate.queryForStream(
                  WeightlessTraitTypeRepository.READ_BY_ID_SQL,
                  beanPropertyRowMapper,
                  WEIGHTLESS_TRAIT_TYPE_ID))
          .thenReturn(Stream.of(weightlessTraitTypeDTO), Stream.empty());
      boolean isDeletedSuccessfully =
          new WeightlessTraitTypeRepository(jdbcTemplate, beanPropertyRowMapper)
              .delete(weightlessTraitTypeDTO);
      /*
      Read by id is called twice. Once at the start of the delete method and once at the end of
   the delete method.
      */
      Mockito.verify(jdbcTemplate, Mockito.times(2))
          .queryForStream(
              WeightlessTraitTypeRepository.READ_BY_ID_SQL, beanPropertyRowMapper,
              WEIGHTLESS_TRAIT_TYPE_ID);
      Mockito.verify(jdbcTemplate, Mockito.times(1))
          .update(WeightlessTraitTypeRepository.DELETE_BY_ID_SQL, WEIGHTLESS_TRAIT_TYPE_ID);
      assertThat(isDeletedSuccessfully).isEqualTo(true);
    }

    @Test
    void testDeleteNonExistingEntry() {
      WeightlessTraitTypeDTO weightlessTraitTypeDTO =
          WeightlessTraitTypeDTO.builder()
              .id(ID)
              .weightlessTraitTypeId(WEIGHTLESS_TRAIT_TYPE_ID)
              .weightlessTraitTypeName(NAME)
              .description(DESCRIPTION)
              .build();
      Mockito.when(
              jdbcTemplate.queryForStream(
                  WeightlessTraitTypeRepository.READ_BY_ID_SQL,
                  beanPropertyRowMapper,
                  WEIGHTLESS_TRAIT_TYPE_ID))
          .thenReturn(Stream.empty());
      boolean isDeletedSuccessfully =
          new WeightlessTraitTypeRepository(jdbcTemplate, beanPropertyRowMapper)
              .delete(weightlessTraitTypeDTO);
      /*
      Read by id is called twice. Once at the start of the delete method and once at the end of
   the delete method.
       */
      Mockito.verify(jdbcTemplate, Mockito.times(1))
          .queryForStream(
              WeightlessTraitTypeRepository.READ_BY_ID_SQL, beanPropertyRowMapper,
              WEIGHTLESS_TRAIT_TYPE_ID);
      Mockito.verify(jdbcTemplate, Mockito.times(0))
          .update(WeightlessTraitTypeRepository.DELETE_BY_ID_SQL, WEIGHTLESS_TRAIT_TYPE_ID);
      assertThat(isDeletedSuccessfully).isEqualTo(false);
    }
}
