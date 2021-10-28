package com.tylerfitzgerald.demo_api.token.traitTypeWeights;

import com.tylerfitzgerald.demo_api.sql.traitTypeWeights.TraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.traitTypeWeights.TraitTypeWeightRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TraitTypeWeightRepositoryTest {

  private JdbcTemplate jdbcTemplate;
  private BeanPropertyRowMapper beanPropertyRowMapper;
  // Value set 1
  private static final Long ID = 1L;
  private static final Long TRAIT_TYPE_WEIGHT_ID = 2L;
  private static final Long TRAIT_TYPE_ID = 3L;
  private static final Long LIKELIHOOD = 4L;
  private static final String VALUE = "STRING_A";
  private static final String DISPLAY_TYPE_VALUE = "STRING_B";
  // Value set 2
  private static final Long ID_2 = 5L;
  private static final Long TRAIT_TYPE_WEIGHT_ID_2 = 6L;
  private static final Long TRAIT_TYPE_ID_2 = 7L;
  private static final Long LIKELIHOOD_2 = 8L;
  private static final String VALUE_2 = "STRING_C";
  private static final String DISPLAY_TYPE_VALUE_2 = "STRING_D";

  @BeforeEach
  public void setup() {
    this.jdbcTemplate = Mockito.mock(JdbcTemplate.class);
    this.beanPropertyRowMapper = new BeanPropertyRowMapper(TraitTypeWeightDTO.class);
  }

  @Test
  void testConstructor() {
    assertThat(new TraitTypeWeightRepository(jdbcTemplate, beanPropertyRowMapper))
        .isInstanceOf(TraitTypeWeightRepository.class);
  }

  @Test
  void testCreateNonExisting() {
    TraitTypeWeightDTO traitWeightTypeDTO =
        TraitTypeWeightDTO.builder()
            .id(ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .likelihood(LIKELIHOOD)
            .value(VALUE)
            .displayTypeValue(DISPLAY_TYPE_VALUE)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                TraitTypeWeightRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                TRAIT_TYPE_WEIGHT_ID))
        .thenReturn(Stream.empty(), Stream.of(traitWeightTypeDTO));
    Mockito.when(
            jdbcTemplate.update(
                TraitTypeWeightRepository.CREATE_SQL,
                TRAIT_TYPE_WEIGHT_ID,
                TRAIT_TYPE_ID,
                LIKELIHOOD,
                VALUE,
                DISPLAY_TYPE_VALUE))
        .thenReturn(1);
    TraitTypeWeightDTO traitTypeDTOResult =
        new TraitTypeWeightRepository(jdbcTemplate, beanPropertyRowMapper)
            .create(traitWeightTypeDTO);
    Mockito.verify(jdbcTemplate, Mockito.times(2))
        .queryForStream(
            TraitTypeWeightRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_TYPE_WEIGHT_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .update(
            TraitTypeWeightRepository.CREATE_SQL,
            TRAIT_TYPE_WEIGHT_ID,
            TRAIT_TYPE_ID,
            LIKELIHOOD,
            VALUE,
            DISPLAY_TYPE_VALUE);
    assertThat(traitTypeDTOResult).isEqualTo(traitWeightTypeDTO);
  }

  @Test
  void testCreateExisting() {
    TraitTypeWeightDTO traitWeightTypeDTO =
        TraitTypeWeightDTO.builder()
            .id(ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .likelihood(LIKELIHOOD)
            .value(VALUE)
            .displayTypeValue(DISPLAY_TYPE_VALUE)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                TraitTypeWeightRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                TRAIT_TYPE_WEIGHT_ID))
        .thenReturn(Stream.of(traitWeightTypeDTO));
    TraitTypeWeightDTO traitTypeWeightDTOResult =
        new TraitTypeWeightRepository(jdbcTemplate, beanPropertyRowMapper)
            .create(traitWeightTypeDTO);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(
            TraitTypeWeightRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_TYPE_WEIGHT_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(0))
        .update(
            TraitTypeWeightRepository.CREATE_SQL,
            TRAIT_TYPE_WEIGHT_ID,
            TRAIT_TYPE_ID,
            LIKELIHOOD,
            VALUE,
            DISPLAY_TYPE_VALUE);
    assertThat(traitTypeWeightDTOResult).isEqualTo(null);
  }

  @Test
  void testRead() {
    TraitTypeWeightDTO traitTypeWeightDTO =
        TraitTypeWeightDTO.builder()
            .id(ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .likelihood(LIKELIHOOD)
            .value(VALUE)
            .displayTypeValue(DISPLAY_TYPE_VALUE)
            .build();
    TraitTypeWeightDTO traitTypeWeightDTO2 =
        TraitTypeWeightDTO.builder()
            .id(ID_2)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID_2)
            .traitTypeId(TRAIT_TYPE_ID_2)
            .likelihood(LIKELIHOOD_2)
            .value(VALUE_2)
            .displayTypeValue(DISPLAY_TYPE_VALUE_2)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(TraitTypeWeightRepository.READ_SQL, beanPropertyRowMapper))
        .thenReturn(Stream.of(traitTypeWeightDTO, traitTypeWeightDTO2));
    List<TraitTypeWeightDTO> traitTypeWeights =
        new TraitTypeWeightRepository(jdbcTemplate, beanPropertyRowMapper).read();
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(TraitTypeWeightRepository.READ_SQL, beanPropertyRowMapper);
    assertThat(traitTypeWeights.get(0)).isEqualTo(traitTypeWeightDTO);
    assertThat(traitTypeWeights.get(1)).isEqualTo(traitTypeWeightDTO2);
  }

  @Test
  void testReadEmptyTable() {
    Mockito.when(
            jdbcTemplate.queryForStream(TraitTypeWeightRepository.READ_SQL, beanPropertyRowMapper))
        .thenReturn(Stream.empty());
    List<TraitTypeWeightDTO> traitTypeWeights =
        new TraitTypeWeightRepository(jdbcTemplate, beanPropertyRowMapper).read();
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(TraitTypeWeightRepository.READ_SQL, beanPropertyRowMapper);
    assertThat(traitTypeWeights.isEmpty()).isEqualTo(true);
  }

  @Test
  void testReadExistingById() {
    TraitTypeWeightDTO traitTypeWeightDTO =
        TraitTypeWeightDTO.builder()
            .id(ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .likelihood(LIKELIHOOD)
            .value(VALUE)
            .displayTypeValue(DISPLAY_TYPE_VALUE)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                TraitTypeWeightRepository.READ_BY_ID_SQL, beanPropertyRowMapper, ID))
        .thenReturn(Stream.of(traitTypeWeightDTO));
    TraitTypeWeightDTO traitTypeWeightDTOResult =
        new TraitTypeWeightRepository(jdbcTemplate, beanPropertyRowMapper).readById(ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(TraitTypeWeightRepository.READ_BY_ID_SQL, beanPropertyRowMapper, ID);
    assertThat(traitTypeWeightDTOResult).isEqualTo(traitTypeWeightDTO);
  }

  @Test
  void testReadNonExistingById() {
    Mockito.when(
            jdbcTemplate.queryForStream(
                TraitTypeWeightRepository.READ_BY_ID_SQL, beanPropertyRowMapper, ID))
        .thenReturn(Stream.empty());
    TraitTypeWeightDTO traitDTOResult =
        new TraitTypeWeightRepository(jdbcTemplate, beanPropertyRowMapper).readById(ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(TraitTypeWeightRepository.READ_BY_ID_SQL, beanPropertyRowMapper, ID);
    assertThat(traitDTOResult).isEqualTo(null);
  }

  @Test
  void testUpdateExistingEntry() {
    TraitTypeWeightDTO traitTypeWeightDTO =
        TraitTypeWeightDTO.builder()
            .id(ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .likelihood(LIKELIHOOD)
            .value(VALUE)
            .displayTypeValue(DISPLAY_TYPE_VALUE)
            .build();
    // traitTypeWeightDTO2 Will have the same id and trait type weight id as traitTypeWeightDTO
    TraitTypeWeightDTO traitTypeWeightDTO2 =
        TraitTypeWeightDTO.builder()
            .id(ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .traitTypeId(TRAIT_TYPE_ID_2)
            .likelihood(LIKELIHOOD_2)
            .value(VALUE_2)
            .displayTypeValue(DISPLAY_TYPE_VALUE_2)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                TraitTypeWeightRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                TRAIT_TYPE_WEIGHT_ID))
        .thenReturn(Stream.of(traitTypeWeightDTO), Stream.of(traitTypeWeightDTO2));
    Mockito.when(
            jdbcTemplate.update(
                TraitTypeWeightRepository.UPDATE_SQL,
                TRAIT_TYPE_ID_2,
                LIKELIHOOD_2,
                VALUE_2,
                DISPLAY_TYPE_VALUE_2,
                TRAIT_TYPE_WEIGHT_ID))
        .thenReturn(1);
    TraitTypeWeightDTO traitTypeDTOResults =
        new TraitTypeWeightRepository(jdbcTemplate, beanPropertyRowMapper)
            .update(traitTypeWeightDTO2);
    Mockito.verify(jdbcTemplate, Mockito.times(2))
        .queryForStream(
            TraitTypeWeightRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_TYPE_WEIGHT_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .update(
            TraitTypeWeightRepository.UPDATE_SQL,
            TRAIT_TYPE_ID_2,
            LIKELIHOOD_2,
            VALUE_2,
            DISPLAY_TYPE_VALUE_2,
            TRAIT_TYPE_WEIGHT_ID);
    assertThat(traitTypeDTOResults).isEqualTo(traitTypeWeightDTO2);
  }

  @Test
  void testUpdateNonExistingEntry() {
    TraitTypeWeightDTO traitTypeWeightDTO =
        TraitTypeWeightDTO.builder()
            .id(ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .likelihood(LIKELIHOOD)
            .value(VALUE)
            .displayTypeValue(DISPLAY_TYPE_VALUE)
            .build();
    // Return a Stream.empty() from the read by id call to imitate a non-existing entry.
    Mockito.when(
            jdbcTemplate.queryForStream(
                TraitTypeWeightRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                TRAIT_TYPE_WEIGHT_ID))
        .thenReturn(Stream.empty());
    TraitTypeWeightDTO traitTypeDTOResults =
        new TraitTypeWeightRepository(jdbcTemplate, beanPropertyRowMapper)
            .update(traitTypeWeightDTO);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(
            TraitTypeWeightRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_TYPE_WEIGHT_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(0))
        .update(
            TraitTypeWeightRepository.UPDATE_SQL,
            TRAIT_TYPE_ID_2,
            LIKELIHOOD_2,
            VALUE_2,
            DISPLAY_TYPE_VALUE_2,
            TRAIT_TYPE_WEIGHT_ID);
    assertThat(traitTypeDTOResults).isEqualTo(null);
  }

  @Test
  void testDeleteExistingEntry() {
    TraitTypeWeightDTO traitTypeWeightDTO =
        TraitTypeWeightDTO.builder()
            .id(ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .likelihood(LIKELIHOOD)
            .value(VALUE)
            .displayTypeValue(DISPLAY_TYPE_VALUE)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                TraitTypeWeightRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                TRAIT_TYPE_WEIGHT_ID))
        .thenReturn(Stream.of(traitTypeWeightDTO), Stream.empty());
    boolean isDeletedSuccessfully =
        new TraitTypeWeightRepository(jdbcTemplate, beanPropertyRowMapper)
            .delete(traitTypeWeightDTO);
    // Read by id is called twice. Once at the start of the delete method and once at the end of the
    // delete method.
    Mockito.verify(jdbcTemplate, Mockito.times(2))
        .queryForStream(
            TraitTypeWeightRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_TYPE_WEIGHT_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .update(TraitTypeWeightRepository.DELETE_BY_ID_SQL, TRAIT_TYPE_WEIGHT_ID);
    assertThat(isDeletedSuccessfully).isEqualTo(true);
  }

  @Test
  void testDeleteNonExistingEntry() {
    TraitTypeWeightDTO traitTypeWeightDTO =
        TraitTypeWeightDTO.builder()
            .id(ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .likelihood(LIKELIHOOD)
            .value(VALUE)
            .displayTypeValue(DISPLAY_TYPE_VALUE)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                TraitTypeWeightRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                TRAIT_TYPE_WEIGHT_ID))
        .thenReturn(Stream.empty());
    boolean isDeletedSuccessfully =
        new TraitTypeWeightRepository(jdbcTemplate, beanPropertyRowMapper)
            .delete(traitTypeWeightDTO);
    // Read by id is called twice. Once at the start of the delete method and once at the end of the
    // delete method.
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(
            TraitTypeWeightRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_TYPE_WEIGHT_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(0))
        .update(TraitTypeWeightRepository.DELETE_BY_ID_SQL, TRAIT_TYPE_WEIGHT_ID);
    assertThat(isDeletedSuccessfully).isEqualTo(false);
  }
}
