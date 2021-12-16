package io.tilenft.sql.tbls.tblTraits;

import static org.assertj.core.api.Assertions.assertThat;

import io.tilenft.sql.dtos.WeightedTraitDTO;
import io.tilenft.sql.repositories.WeightedTraitRepository;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class WeightedTraitRepositoryTest {

  private JdbcTemplate jdbcTemplate;
  private BeanPropertyRowMapper beanPropertyRowMapper;
  // Value set 1
  private static final Long ID = 1L;
  private static final Long TRAIT_ID = 2L;
  private static final Long TOKEN_ID = 3L;
  private static final Long TRAIT_TYPE_ID = 4L;
  private static final Long TRAIT_TYPE_WEIGHT_ID = 5L;
  // Value set 2
  private static final Long ID_2 = 6L;
  private static final Long TRAIT_ID_2 = 7L;
  private static final Long TOKEN_ID_2 = 8L;
  private static final Long TRAIT_TYPE_ID_2 = 9L;
  private static final Long TRAIT_TYPE_WEIGHT_ID_2 = 10L;

  @BeforeEach
  public void setup() {
    this.jdbcTemplate = Mockito.mock(JdbcTemplate.class);
    this.beanPropertyRowMapper = new BeanPropertyRowMapper(WeightedTraitDTO.class);
  }

  @Test
  void testCreateNonExisting() {
    WeightedTraitDTO weightedTraitDTO =
        WeightedTraitDTO.builder()
            .id(ID)
            .traitId(TRAIT_ID)
            .tokenId(TOKEN_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightedTraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_ID))
        .thenReturn(Stream.empty(), Stream.of(weightedTraitDTO));
    Mockito.when(
            jdbcTemplate.update(
                WeightedTraitRepository.CREATE_SQL,
                TRAIT_ID,
                TOKEN_ID,
                TRAIT_TYPE_ID,
                TRAIT_TYPE_WEIGHT_ID))
        .thenReturn(1);
    WeightedTraitDTO weightedTraitDTOResult =
        new WeightedTraitRepository(jdbcTemplate, beanPropertyRowMapper).create(weightedTraitDTO);
    Mockito.verify(jdbcTemplate, Mockito.times(2))
        .queryForStream(WeightedTraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .update(
            WeightedTraitRepository.CREATE_SQL,
            TRAIT_ID,
            TOKEN_ID,
            TRAIT_TYPE_ID,
            TRAIT_TYPE_WEIGHT_ID);
    assertThat(weightedTraitDTOResult).isEqualTo(weightedTraitDTO);
  }

  @Test
  void testCreateExisting() {
    WeightedTraitDTO weightedTraitDTO =
        WeightedTraitDTO.builder()
            .id(ID)
            .traitId(TRAIT_ID)
            .tokenId(TOKEN_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightedTraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_ID))
        .thenReturn(Stream.of(weightedTraitDTO));
    WeightedTraitDTO weightedTraitDTOResult =
        new WeightedTraitRepository(jdbcTemplate, beanPropertyRowMapper).create(weightedTraitDTO);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(WeightedTraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(0))
        .update(
            WeightedTraitRepository.CREATE_SQL,
            TRAIT_ID,
            TOKEN_ID,
            TRAIT_TYPE_ID,
            TRAIT_TYPE_WEIGHT_ID);
    assertThat(weightedTraitDTOResult).isEqualTo(null);
  }

  @Test
  void testRead() {
    WeightedTraitDTO weightedTraitDTO =
        WeightedTraitDTO.builder()
            .id(ID)
            .traitId(TRAIT_ID)
            .tokenId(TOKEN_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .build();
    WeightedTraitDTO weightedTraitDTO2 =
        WeightedTraitDTO.builder()
            .id(ID_2)
            .traitId(TRAIT_ID_2)
            .tokenId(TOKEN_ID)
            .traitTypeId(TRAIT_TYPE_ID_2)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID_2)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(WeightedTraitRepository.READ_SQL, beanPropertyRowMapper))
        .thenReturn(Stream.of(weightedTraitDTO, weightedTraitDTO2));
    List<WeightedTraitDTO> traits =
        new WeightedTraitRepository(jdbcTemplate, beanPropertyRowMapper).read();
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(WeightedTraitRepository.READ_SQL, beanPropertyRowMapper);
    assertThat(traits.get(0)).isEqualTo(weightedTraitDTO);
    assertThat(traits.get(1)).isEqualTo(weightedTraitDTO2);
  }

  @Test
  void testReadEmptyTable() {
    Mockito.when(
            jdbcTemplate.queryForStream(WeightedTraitRepository.READ_SQL, beanPropertyRowMapper))
        .thenReturn(Stream.empty());
    List<WeightedTraitDTO> traits =
        new WeightedTraitRepository(jdbcTemplate, beanPropertyRowMapper).read();
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(WeightedTraitRepository.READ_SQL, beanPropertyRowMapper);
    assertThat(traits.isEmpty()).isEqualTo(true);
  }

  @Test
  void testReadExistingById() {
    WeightedTraitDTO weightedTraitDTO =
        WeightedTraitDTO.builder()
            .id(ID)
            .traitId(TRAIT_ID)
            .tokenId(TOKEN_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightedTraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, ID))
        .thenReturn(Stream.of(weightedTraitDTO));
    WeightedTraitDTO weightedTraitDTOResult =
        new WeightedTraitRepository(jdbcTemplate, beanPropertyRowMapper).readById(ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(WeightedTraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, ID);
    assertThat(weightedTraitDTOResult).isEqualTo(weightedTraitDTO);
  }

  @Test
  void testReadNonExistingById() {
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightedTraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, ID))
        .thenReturn(Stream.empty());
    WeightedTraitDTO weightedTraitDTOResult =
        new WeightedTraitRepository(jdbcTemplate, beanPropertyRowMapper).readById(ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(WeightedTraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, ID);
    assertThat(weightedTraitDTOResult).isEqualTo(null);
  }

  @Test
  void testUpdateExistingEntry() {
    WeightedTraitDTO weightedTraitDTO =
        WeightedTraitDTO.builder()
            .id(ID)
            .traitId(TRAIT_ID)
            .tokenId(TOKEN_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .build();
    // traitDTO2 Will have the same id and trait id as traitDTO
    WeightedTraitDTO weightedTraitDTO2 =
        WeightedTraitDTO.builder()
            .id(ID)
            .traitId(TRAIT_ID)
            .tokenId(TOKEN_ID_2)
            .traitTypeId(TRAIT_TYPE_ID_2)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID_2)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightedTraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_ID))
        .thenReturn(Stream.of(weightedTraitDTO), Stream.of(weightedTraitDTO2));
    Mockito.when(
            jdbcTemplate.update(
                WeightedTraitRepository.UPDATE_SQL,
                TOKEN_ID_2,
                TRAIT_TYPE_ID_2,
                TRAIT_TYPE_WEIGHT_ID_2,
                TRAIT_ID))
        .thenReturn(1);
    WeightedTraitDTO weightedTraitDTOResults =
        new WeightedTraitRepository(jdbcTemplate, beanPropertyRowMapper).update(weightedTraitDTO2);
    Mockito.verify(jdbcTemplate, Mockito.times(2))
        .queryForStream(WeightedTraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .update(
            WeightedTraitRepository.UPDATE_SQL,
            TOKEN_ID_2,
            TRAIT_TYPE_ID_2,
            TRAIT_TYPE_WEIGHT_ID_2,
            TRAIT_ID);
    assertThat(weightedTraitDTOResults).isEqualTo(weightedTraitDTO2);
  }

  @Test
  void testUpdateNonExistingEntry() {
    WeightedTraitDTO weightedTraitDTO =
        WeightedTraitDTO.builder()
            .id(ID)
            .traitId(TRAIT_ID)
            .tokenId(TOKEN_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .build();
    // Return a Stream.empty() from the read by id call to imitate a non-existing entry.
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightedTraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_ID))
        .thenReturn(Stream.empty());
    WeightedTraitDTO weightedTraitDTOResults =
        new WeightedTraitRepository(jdbcTemplate, beanPropertyRowMapper).update(weightedTraitDTO);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(WeightedTraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(0))
        .update(WeightedTraitRepository.UPDATE_SQL, TRAIT_TYPE_ID, TRAIT_TYPE_WEIGHT_ID);
    assertThat(weightedTraitDTOResults).isEqualTo(null);
  }

  @Test
  void testDeleteExistingEntry() {
    WeightedTraitDTO weightedTraitDTO =
        WeightedTraitDTO.builder()
            .id(ID)
            .traitId(TRAIT_ID)
            .tokenId(TOKEN_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightedTraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_ID))
        .thenReturn(Stream.of(weightedTraitDTO), Stream.empty());
    boolean isDeletedSuccessfully =
        new WeightedTraitRepository(jdbcTemplate, beanPropertyRowMapper).delete(weightedTraitDTO);
    // Read by id is called twice. Once at the start of the delete method and once at the end of the
    // delete method.
    Mockito.verify(jdbcTemplate, Mockito.times(2))
        .queryForStream(WeightedTraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .update(WeightedTraitRepository.DELETE_BY_ID_SQL, TRAIT_ID);
    assertThat(isDeletedSuccessfully).isEqualTo(true);
  }

  @Test
  void testDeleteNonExistingEntry() {
    WeightedTraitDTO weightedTraitDTO =
        WeightedTraitDTO.builder()
            .id(ID)
            .traitId(TRAIT_ID)
            .tokenId(TOKEN_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightedTraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_ID))
        .thenReturn(Stream.empty());
    boolean isDeletedSuccessfully =
        new WeightedTraitRepository(jdbcTemplate, beanPropertyRowMapper).delete(weightedTraitDTO);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(WeightedTraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(0))
        .update(WeightedTraitRepository.DELETE_BY_ID_SQL, TRAIT_ID);
    assertThat(isDeletedSuccessfully).isEqualTo(false);
  }
}
