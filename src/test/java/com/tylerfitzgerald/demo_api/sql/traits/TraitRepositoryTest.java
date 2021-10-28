package com.tylerfitzgerald.demo_api.sql.traits;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TraitRepositoryTest {

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
    this.beanPropertyRowMapper = new BeanPropertyRowMapper(TraitDTO.class);
  }

  @Test
  void testConstructor() {
    assertThat(new TraitRepository(jdbcTemplate, beanPropertyRowMapper))
        .isInstanceOf(TraitRepository.class);
  }

  @Test
  void testCreateNonExisting() {
    TraitDTO traitDTO =
        TraitDTO.builder()
            .id(ID)
            .traitId(TRAIT_ID)
            .tokenId(TOKEN_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                TraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_ID))
        .thenReturn(Stream.empty(), Stream.of(traitDTO));
    Mockito.when(
            jdbcTemplate.update(
                TraitRepository.CREATE_SQL,
                TRAIT_ID,
                TOKEN_ID,
                TRAIT_TYPE_ID,
                TRAIT_TYPE_WEIGHT_ID))
        .thenReturn(1);
    TraitDTO traitDTOResult =
        new TraitRepository(jdbcTemplate, beanPropertyRowMapper).create(traitDTO);
    Mockito.verify(jdbcTemplate, Mockito.times(2))
        .queryForStream(TraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .update(
            TraitRepository.CREATE_SQL, TRAIT_ID, TOKEN_ID, TRAIT_TYPE_ID, TRAIT_TYPE_WEIGHT_ID);
    assertThat(traitDTOResult).isEqualTo(traitDTO);
  }

  @Test
  void testCreateExisting() {
    TraitDTO traitDTO =
        TraitDTO.builder()
            .id(ID)
            .traitId(TRAIT_ID)
            .tokenId(TOKEN_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                TraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_ID))
        .thenReturn(Stream.of(traitDTO));
    TraitDTO traitDTOResult =
        new TraitRepository(jdbcTemplate, beanPropertyRowMapper).create(traitDTO);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(TraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(0))
        .update(
            TraitRepository.CREATE_SQL, TRAIT_ID, TOKEN_ID, TRAIT_TYPE_ID, TRAIT_TYPE_WEIGHT_ID);
    assertThat(traitDTOResult).isEqualTo(null);
  }

  @Test
  void testRead() {
    TraitDTO traitDTO =
        TraitDTO.builder()
            .id(ID)
            .traitId(TRAIT_ID)
            .tokenId(TOKEN_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .build();
    TraitDTO traitDTO2 =
        TraitDTO.builder()
            .id(ID_2)
            .traitId(TRAIT_ID_2)
            .tokenId(TOKEN_ID)
            .traitTypeId(TRAIT_TYPE_ID_2)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID_2)
            .build();
    Mockito.when(jdbcTemplate.queryForStream(TraitRepository.READ_SQL, beanPropertyRowMapper))
        .thenReturn(Stream.of(traitDTO, traitDTO2));
    List<TraitDTO> traits = new TraitRepository(jdbcTemplate, beanPropertyRowMapper).read();
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(TraitRepository.READ_SQL, beanPropertyRowMapper);
    assertThat(traits.get(0)).isEqualTo(traitDTO);
    assertThat(traits.get(1)).isEqualTo(traitDTO2);
  }

  @Test
  void testReadEmptyTable() {
    Mockito.when(jdbcTemplate.queryForStream(TraitRepository.READ_SQL, beanPropertyRowMapper))
        .thenReturn(Stream.empty());
    List<TraitDTO> traits = new TraitRepository(jdbcTemplate, beanPropertyRowMapper).read();
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(TraitRepository.READ_SQL, beanPropertyRowMapper);
    assertThat(traits.isEmpty()).isEqualTo(true);
  }

  @Test
  void testReadExistingById() {
    TraitDTO traitDTO =
        TraitDTO.builder()
            .id(ID)
            .traitId(TRAIT_ID)
            .tokenId(TOKEN_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(TraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, ID))
        .thenReturn(Stream.of(traitDTO));
    TraitDTO traitDTOResult = new TraitRepository(jdbcTemplate, beanPropertyRowMapper).readById(ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(TraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, ID);
    assertThat(traitDTOResult).isEqualTo(traitDTO);
  }

  @Test
  void testReadNonExistingById() {
    Mockito.when(
            jdbcTemplate.queryForStream(TraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, ID))
        .thenReturn(Stream.empty());
    TraitDTO traitDTOResult = new TraitRepository(jdbcTemplate, beanPropertyRowMapper).readById(ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(TraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, ID);
    assertThat(traitDTOResult).isEqualTo(null);
  }

  @Test
  void testUpdateExistingEntry() {
    TraitDTO traitDTO =
        TraitDTO.builder()
            .id(ID)
            .traitId(TRAIT_ID)
            .tokenId(TOKEN_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .build();
    // traitDTO2 Will have the same id and trait id as traitDTO
    TraitDTO traitDTO2 =
        TraitDTO.builder()
            .id(ID)
            .traitId(TRAIT_ID)
            .tokenId(TOKEN_ID_2)
            .traitTypeId(TRAIT_TYPE_ID_2)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID_2)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                TraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_ID))
        .thenReturn(Stream.of(traitDTO), Stream.of(traitDTO2));
    Mockito.when(
            jdbcTemplate.update(
                TraitRepository.UPDATE_SQL,
                TOKEN_ID_2,
                TRAIT_TYPE_ID_2,
                TRAIT_TYPE_WEIGHT_ID_2,
                TRAIT_ID))
        .thenReturn(1);
    TraitDTO traitDTOResults =
        new TraitRepository(jdbcTemplate, beanPropertyRowMapper).update(traitDTO2);
    Mockito.verify(jdbcTemplate, Mockito.times(2))
        .queryForStream(TraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .update(
            TraitRepository.UPDATE_SQL,
            TOKEN_ID_2,
            TRAIT_TYPE_ID_2,
            TRAIT_TYPE_WEIGHT_ID_2,
            TRAIT_ID);
    assertThat(traitDTOResults).isEqualTo(traitDTO2);
  }

  @Test
  void testUpdateNonExistingEntry() {
    TraitDTO traitDTO =
        TraitDTO.builder()
            .id(ID)
            .traitId(TRAIT_ID)
            .tokenId(TOKEN_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .build();
    // Return a Stream.empty() from the read by id call to imitate a non-existing entry.
    Mockito.when(
            jdbcTemplate.queryForStream(
                TraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_ID))
        .thenReturn(Stream.empty());
    TraitDTO traitDTOResults =
        new TraitRepository(jdbcTemplate, beanPropertyRowMapper).update(traitDTO);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(TraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(0))
        .update(TraitRepository.UPDATE_SQL, TRAIT_TYPE_ID, TRAIT_TYPE_WEIGHT_ID);
    assertThat(traitDTOResults).isEqualTo(null);
  }

  @Test
  void testDeleteExistingEntry() {
    TraitDTO traitDTO =
        TraitDTO.builder()
            .id(ID)
            .traitId(TRAIT_ID)
            .tokenId(TOKEN_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                TraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_ID))
        .thenReturn(Stream.of(traitDTO), Stream.empty());
    boolean isDeletedSuccessfully =
        new TraitRepository(jdbcTemplate, beanPropertyRowMapper).delete(traitDTO);
    // Read by id is called twice. Once at the start of the delete method and once at the end of the
    // delete method.
    Mockito.verify(jdbcTemplate, Mockito.times(2))
        .queryForStream(TraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .update(TraitRepository.DELETE_BY_ID_SQL, TRAIT_ID);
    assertThat(isDeletedSuccessfully).isEqualTo(true);
  }

  @Test
  void testDeleteNonExistingEntry() {
    TraitDTO traitDTO =
        TraitDTO.builder()
            .id(ID)
            .traitId(TRAIT_ID)
            .tokenId(TOKEN_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                TraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_ID))
        .thenReturn(Stream.empty());
    boolean isDeletedSuccessfully =
        new TraitRepository(jdbcTemplate, beanPropertyRowMapper).delete(traitDTO);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(TraitRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(0))
        .update(TraitRepository.DELETE_BY_ID_SQL, TRAIT_ID);
    assertThat(isDeletedSuccessfully).isEqualTo(false);
  }
}