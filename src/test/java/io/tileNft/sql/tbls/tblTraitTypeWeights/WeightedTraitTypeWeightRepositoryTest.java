package io.tileNft.sql.tbls.tblTraitTypeWeights;

import static org.assertj.core.api.Assertions.assertThat;

import io.tileNft.sql.dtos.WeightedTraitTypeWeightDTO;
import io.tileNft.sql.repositories.WeightedTraitTypeWeightRepository;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class WeightedTraitTypeWeightRepositoryTest {

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
    this.beanPropertyRowMapper = new BeanPropertyRowMapper(WeightedTraitTypeWeightDTO.class);
  }

  @Test
  void testCreateNonExisting() {
    WeightedTraitTypeWeightDTO traitWeightTypeDTO =
        WeightedTraitTypeWeightDTO.builder()
            .id(ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .likelihood(LIKELIHOOD)
            .value(VALUE)
            .displayTypeValue(DISPLAY_TYPE_VALUE)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightedTraitTypeWeightRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                TRAIT_TYPE_WEIGHT_ID))
        .thenReturn(Stream.empty(), Stream.of(traitWeightTypeDTO));
    Mockito.when(
            jdbcTemplate.update(
                WeightedTraitTypeWeightRepository.CREATE_SQL,
                TRAIT_TYPE_WEIGHT_ID,
                TRAIT_TYPE_ID,
                LIKELIHOOD,
                VALUE,
                DISPLAY_TYPE_VALUE))
        .thenReturn(1);
    WeightedTraitTypeWeightDTO traitTypeDTOResult =
        new WeightedTraitTypeWeightRepository(jdbcTemplate, beanPropertyRowMapper)
            .create(traitWeightTypeDTO);
    Mockito.verify(jdbcTemplate, Mockito.times(2))
        .queryForStream(
            WeightedTraitTypeWeightRepository.READ_BY_ID_SQL,
            beanPropertyRowMapper,
            TRAIT_TYPE_WEIGHT_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .update(
            WeightedTraitTypeWeightRepository.CREATE_SQL,
            TRAIT_TYPE_WEIGHT_ID,
            TRAIT_TYPE_ID,
            LIKELIHOOD,
            VALUE,
            DISPLAY_TYPE_VALUE);
    assertThat(traitTypeDTOResult).isEqualTo(traitWeightTypeDTO);
  }

  @Test
  void testCreateExisting() {
    WeightedTraitTypeWeightDTO traitWeightTypeDTO =
        WeightedTraitTypeWeightDTO.builder()
            .id(ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .likelihood(LIKELIHOOD)
            .value(VALUE)
            .displayTypeValue(DISPLAY_TYPE_VALUE)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightedTraitTypeWeightRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                TRAIT_TYPE_WEIGHT_ID))
        .thenReturn(Stream.of(traitWeightTypeDTO));
    WeightedTraitTypeWeightDTO weightedTraitTypeWeightDTOResult =
        new WeightedTraitTypeWeightRepository(jdbcTemplate, beanPropertyRowMapper)
            .create(traitWeightTypeDTO);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(
            WeightedTraitTypeWeightRepository.READ_BY_ID_SQL,
            beanPropertyRowMapper,
            TRAIT_TYPE_WEIGHT_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(0))
        .update(
            WeightedTraitTypeWeightRepository.CREATE_SQL,
            TRAIT_TYPE_WEIGHT_ID,
            TRAIT_TYPE_ID,
            LIKELIHOOD,
            VALUE,
            DISPLAY_TYPE_VALUE);
    assertThat(weightedTraitTypeWeightDTOResult).isEqualTo(null);
  }

  @Test
  void testRead() {
    WeightedTraitTypeWeightDTO weightedTraitTypeWeightDTO =
        WeightedTraitTypeWeightDTO.builder()
            .id(ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .likelihood(LIKELIHOOD)
            .value(VALUE)
            .displayTypeValue(DISPLAY_TYPE_VALUE)
            .build();
    WeightedTraitTypeWeightDTO weightedTraitTypeWeightDTO2 =
        WeightedTraitTypeWeightDTO.builder()
            .id(ID_2)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID_2)
            .traitTypeId(TRAIT_TYPE_ID_2)
            .likelihood(LIKELIHOOD_2)
            .value(VALUE_2)
            .displayTypeValue(DISPLAY_TYPE_VALUE_2)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightedTraitTypeWeightRepository.READ_SQL, beanPropertyRowMapper))
        .thenReturn(Stream.of(weightedTraitTypeWeightDTO, weightedTraitTypeWeightDTO2));
    List<WeightedTraitTypeWeightDTO> traitTypeWeights =
        new WeightedTraitTypeWeightRepository(jdbcTemplate, beanPropertyRowMapper).read();
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(WeightedTraitTypeWeightRepository.READ_SQL, beanPropertyRowMapper);
    assertThat(traitTypeWeights.get(0)).isEqualTo(weightedTraitTypeWeightDTO);
    assertThat(traitTypeWeights.get(1)).isEqualTo(weightedTraitTypeWeightDTO2);
  }

  @Test
  void testReadEmptyTable() {
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightedTraitTypeWeightRepository.READ_SQL, beanPropertyRowMapper))
        .thenReturn(Stream.empty());
    List<WeightedTraitTypeWeightDTO> traitTypeWeights =
        new WeightedTraitTypeWeightRepository(jdbcTemplate, beanPropertyRowMapper).read();
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(WeightedTraitTypeWeightRepository.READ_SQL, beanPropertyRowMapper);
    assertThat(traitTypeWeights.isEmpty()).isEqualTo(true);
  }

  @Test
  void testReadExistingById() {
    WeightedTraitTypeWeightDTO weightedTraitTypeWeightDTO =
        WeightedTraitTypeWeightDTO.builder()
            .id(ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .likelihood(LIKELIHOOD)
            .value(VALUE)
            .displayTypeValue(DISPLAY_TYPE_VALUE)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightedTraitTypeWeightRepository.READ_BY_ID_SQL, beanPropertyRowMapper, ID))
        .thenReturn(Stream.of(weightedTraitTypeWeightDTO));
    WeightedTraitTypeWeightDTO weightedTraitTypeWeightDTOResult =
        new WeightedTraitTypeWeightRepository(jdbcTemplate, beanPropertyRowMapper).readById(ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(
            WeightedTraitTypeWeightRepository.READ_BY_ID_SQL, beanPropertyRowMapper, ID);
    assertThat(weightedTraitTypeWeightDTOResult).isEqualTo(weightedTraitTypeWeightDTO);
  }

  @Test
  void testReadNonExistingById() {
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightedTraitTypeWeightRepository.READ_BY_ID_SQL, beanPropertyRowMapper, ID))
        .thenReturn(Stream.empty());
    WeightedTraitTypeWeightDTO traitDTOResult =
        new WeightedTraitTypeWeightRepository(jdbcTemplate, beanPropertyRowMapper).readById(ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(
            WeightedTraitTypeWeightRepository.READ_BY_ID_SQL, beanPropertyRowMapper, ID);
    assertThat(traitDTOResult).isEqualTo(null);
  }

  @Test
  void testUpdateExistingEntry() {
    WeightedTraitTypeWeightDTO weightedTraitTypeWeightDTO =
        WeightedTraitTypeWeightDTO.builder()
            .id(ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .likelihood(LIKELIHOOD)
            .value(VALUE)
            .displayTypeValue(DISPLAY_TYPE_VALUE)
            .build();
    // traitTypeWeightDTO2 Will have the same id and trait type weight id as traitTypeWeightDTO
    WeightedTraitTypeWeightDTO weightedTraitTypeWeightDTO2 =
        WeightedTraitTypeWeightDTO.builder()
            .id(ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .traitTypeId(TRAIT_TYPE_ID_2)
            .likelihood(LIKELIHOOD_2)
            .value(VALUE_2)
            .displayTypeValue(DISPLAY_TYPE_VALUE_2)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightedTraitTypeWeightRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                TRAIT_TYPE_WEIGHT_ID))
        .thenReturn(Stream.of(weightedTraitTypeWeightDTO), Stream.of(weightedTraitTypeWeightDTO2));
    Mockito.when(
            jdbcTemplate.update(
                WeightedTraitTypeWeightRepository.UPDATE_SQL,
                TRAIT_TYPE_ID_2,
                LIKELIHOOD_2,
                VALUE_2,
                DISPLAY_TYPE_VALUE_2,
                TRAIT_TYPE_WEIGHT_ID))
        .thenReturn(1);
    WeightedTraitTypeWeightDTO traitTypeDTOResults =
        new WeightedTraitTypeWeightRepository(jdbcTemplate, beanPropertyRowMapper)
            .update(weightedTraitTypeWeightDTO2);
    Mockito.verify(jdbcTemplate, Mockito.times(2))
        .queryForStream(
            WeightedTraitTypeWeightRepository.READ_BY_ID_SQL,
            beanPropertyRowMapper,
            TRAIT_TYPE_WEIGHT_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .update(
            WeightedTraitTypeWeightRepository.UPDATE_SQL,
            TRAIT_TYPE_ID_2,
            LIKELIHOOD_2,
            VALUE_2,
            DISPLAY_TYPE_VALUE_2,
            TRAIT_TYPE_WEIGHT_ID);
    assertThat(traitTypeDTOResults).isEqualTo(weightedTraitTypeWeightDTO2);
  }

  @Test
  void testUpdateNonExistingEntry() {
    WeightedTraitTypeWeightDTO weightedTraitTypeWeightDTO =
        WeightedTraitTypeWeightDTO.builder()
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
                WeightedTraitTypeWeightRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                TRAIT_TYPE_WEIGHT_ID))
        .thenReturn(Stream.empty());
    WeightedTraitTypeWeightDTO traitTypeDTOResults =
        new WeightedTraitTypeWeightRepository(jdbcTemplate, beanPropertyRowMapper)
            .update(weightedTraitTypeWeightDTO);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(
            WeightedTraitTypeWeightRepository.READ_BY_ID_SQL,
            beanPropertyRowMapper,
            TRAIT_TYPE_WEIGHT_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(0))
        .update(
            WeightedTraitTypeWeightRepository.UPDATE_SQL,
            TRAIT_TYPE_ID_2,
            LIKELIHOOD_2,
            VALUE_2,
            DISPLAY_TYPE_VALUE_2,
            TRAIT_TYPE_WEIGHT_ID);
    assertThat(traitTypeDTOResults).isEqualTo(null);
  }

  @Test
  void testDeleteExistingEntry() {
    WeightedTraitTypeWeightDTO weightedTraitTypeWeightDTO =
        WeightedTraitTypeWeightDTO.builder()
            .id(ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .likelihood(LIKELIHOOD)
            .value(VALUE)
            .displayTypeValue(DISPLAY_TYPE_VALUE)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightedTraitTypeWeightRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                TRAIT_TYPE_WEIGHT_ID))
        .thenReturn(Stream.of(weightedTraitTypeWeightDTO), Stream.empty());
    boolean isDeletedSuccessfully =
        new WeightedTraitTypeWeightRepository(jdbcTemplate, beanPropertyRowMapper)
            .delete(weightedTraitTypeWeightDTO);
    // Read by id is called twice. Once at the start of the delete method and once at the end of the
    // delete method.
    Mockito.verify(jdbcTemplate, Mockito.times(2))
        .queryForStream(
            WeightedTraitTypeWeightRepository.READ_BY_ID_SQL,
            beanPropertyRowMapper,
            TRAIT_TYPE_WEIGHT_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .update(WeightedTraitTypeWeightRepository.DELETE_BY_ID_SQL, TRAIT_TYPE_WEIGHT_ID);
    assertThat(isDeletedSuccessfully).isEqualTo(true);
  }

  @Test
  void testDeleteNonExistingEntry() {
    WeightedTraitTypeWeightDTO weightedTraitTypeWeightDTO =
        WeightedTraitTypeWeightDTO.builder()
            .id(ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .likelihood(LIKELIHOOD)
            .value(VALUE)
            .displayTypeValue(DISPLAY_TYPE_VALUE)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightedTraitTypeWeightRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                TRAIT_TYPE_WEIGHT_ID))
        .thenReturn(Stream.empty());
    boolean isDeletedSuccessfully =
        new WeightedTraitTypeWeightRepository(jdbcTemplate, beanPropertyRowMapper)
            .delete(weightedTraitTypeWeightDTO);
    // Read by id is called twice. Once at the start of the delete method and once at the end of the
    // delete method.
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(
            WeightedTraitTypeWeightRepository.READ_BY_ID_SQL,
            beanPropertyRowMapper,
            TRAIT_TYPE_WEIGHT_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(0))
        .update(WeightedTraitTypeWeightRepository.DELETE_BY_ID_SQL, TRAIT_TYPE_WEIGHT_ID);
    assertThat(isDeletedSuccessfully).isEqualTo(false);
  }
}
