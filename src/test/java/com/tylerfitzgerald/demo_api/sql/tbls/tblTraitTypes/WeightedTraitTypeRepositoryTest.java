package com.tylerfitzgerald.demo_api.sql.tbls.tblTraitTypes;

import static org.assertj.core.api.Assertions.assertThat;

import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightedTraitTypeRepository;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class WeightedTraitTypeRepositoryTest {

  private JdbcTemplate jdbcTemplate;
  private BeanPropertyRowMapper beanPropertyRowMapper;
  // Value set 1
  private static final Long ID = 1L;
  private static final Long TRAIT_TYPE_ID = 2L;
  private static final String TRAIT_TYPE_NAME = "STRING_A";
  private static final String DESCRIPTION = "STRING_B";
  // Value set 2
  private static final Long ID_2 = 3L;
  private static final Long TRAIT_TYPE_ID_2 = 4L;
  private static final String TRAIT_TYPE_NAME_2 = "STRING_C";
  private static final String DESCRIPTION_2 = "STRING_D";

  @BeforeEach
  public void setup() {
    this.jdbcTemplate = Mockito.mock(JdbcTemplate.class);
    this.beanPropertyRowMapper = new BeanPropertyRowMapper(WeightedTraitTypeDTO.class);
  }

  @Test
  void testCreateNonExisting() {
    WeightedTraitTypeDTO weightedTraitTypeDTO =
        WeightedTraitTypeDTO.builder()
            .id(ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .traitTypeName(TRAIT_TYPE_NAME)
            .description(DESCRIPTION)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightedTraitTypeRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_TYPE_ID))
        .thenReturn(Stream.empty(), Stream.of(weightedTraitTypeDTO));
    Mockito.when(
            jdbcTemplate.update(
                WeightedTraitTypeRepository.CREATE_SQL,
                TRAIT_TYPE_ID,
                TRAIT_TYPE_NAME,
                DESCRIPTION))
        .thenReturn(1);
    WeightedTraitTypeDTO weightedTraitTypeDTOResult =
        new WeightedTraitTypeRepository(jdbcTemplate, beanPropertyRowMapper)
            .create(weightedTraitTypeDTO);
    Mockito.verify(jdbcTemplate, Mockito.times(2))
        .queryForStream(
            WeightedTraitTypeRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_TYPE_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .update(
            WeightedTraitTypeRepository.CREATE_SQL, TRAIT_TYPE_ID, TRAIT_TYPE_NAME, DESCRIPTION);
    assertThat(weightedTraitTypeDTOResult).isEqualTo(weightedTraitTypeDTO);
  }

  @Test
  void testCreateExisting() {
    WeightedTraitTypeDTO weightedTraitTypeDTO =
        WeightedTraitTypeDTO.builder()
            .id(ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .traitTypeName(TRAIT_TYPE_NAME)
            .description(DESCRIPTION)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightedTraitTypeRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_TYPE_ID))
        .thenReturn(Stream.of(weightedTraitTypeDTO));
    WeightedTraitTypeDTO weightedTraitTypeDTOResult =
        new WeightedTraitTypeRepository(jdbcTemplate, beanPropertyRowMapper)
            .create(weightedTraitTypeDTO);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(
            WeightedTraitTypeRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_TYPE_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(0))
        .update(
            WeightedTraitTypeRepository.CREATE_SQL, TRAIT_TYPE_ID, TRAIT_TYPE_NAME, DESCRIPTION);
    assertThat(weightedTraitTypeDTOResult).isEqualTo(null);
  }

  @Test
  void testRead() {
    WeightedTraitTypeDTO weightedTraitTypeDTO =
        WeightedTraitTypeDTO.builder()
            .id(ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .traitTypeName(TRAIT_TYPE_NAME)
            .description(DESCRIPTION)
            .build();
    WeightedTraitTypeDTO weightedTraitTypeDTO2 =
        WeightedTraitTypeDTO.builder()
            .id(ID_2)
            .traitTypeId(TRAIT_TYPE_ID_2)
            .traitTypeName(TRAIT_TYPE_NAME_2)
            .description(DESCRIPTION_2)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightedTraitTypeRepository.READ_SQL, beanPropertyRowMapper))
        .thenReturn(Stream.of(weightedTraitTypeDTO, weightedTraitTypeDTO2));
    List<WeightedTraitTypeDTO> traitTypes =
        new WeightedTraitTypeRepository(jdbcTemplate, beanPropertyRowMapper).read();
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(WeightedTraitTypeRepository.READ_SQL, beanPropertyRowMapper);
    assertThat(traitTypes.get(0)).isEqualTo(weightedTraitTypeDTO);
    assertThat(traitTypes.get(1)).isEqualTo(weightedTraitTypeDTO2);
  }

  @Test
  void testReadEmptyTable() {
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightedTraitTypeRepository.READ_SQL, beanPropertyRowMapper))
        .thenReturn(Stream.empty());
    List<WeightedTraitTypeDTO> traitTypes =
        new WeightedTraitTypeRepository(jdbcTemplate, beanPropertyRowMapper).read();
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(WeightedTraitTypeRepository.READ_SQL, beanPropertyRowMapper);
    assertThat(traitTypes.isEmpty()).isEqualTo(true);
  }

  @Test
  void testReadExistingById() {
    WeightedTraitTypeDTO weightedTraitTypeDTO =
        WeightedTraitTypeDTO.builder()
            .id(ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .traitTypeName(TRAIT_TYPE_NAME)
            .description(DESCRIPTION)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightedTraitTypeRepository.READ_BY_ID_SQL, beanPropertyRowMapper, ID))
        .thenReturn(Stream.of(weightedTraitTypeDTO));
    WeightedTraitTypeDTO traitDTOResult =
        new WeightedTraitTypeRepository(jdbcTemplate, beanPropertyRowMapper).readById(ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(WeightedTraitTypeRepository.READ_BY_ID_SQL, beanPropertyRowMapper, ID);
    assertThat(traitDTOResult).isEqualTo(weightedTraitTypeDTO);
  }

  @Test
  void testReadNonExistingById() {
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightedTraitTypeRepository.READ_BY_ID_SQL, beanPropertyRowMapper, ID))
        .thenReturn(Stream.empty());
    WeightedTraitTypeDTO traitDTOResult =
        new WeightedTraitTypeRepository(jdbcTemplate, beanPropertyRowMapper).readById(ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(WeightedTraitTypeRepository.READ_BY_ID_SQL, beanPropertyRowMapper, ID);
    assertThat(traitDTOResult).isEqualTo(null);
  }

  @Test
  void testUpdateExistingEntry() {
    WeightedTraitTypeDTO weightedTraitTypeDTO =
        WeightedTraitTypeDTO.builder()
            .id(ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .traitTypeName(TRAIT_TYPE_NAME)
            .description(DESCRIPTION)
            .build();
    // weightedTraitTypeDTO2 Will have the same id and trait type id as weightedTraitTypeDTO
    WeightedTraitTypeDTO weightedTraitTypeDTO2 =
        WeightedTraitTypeDTO.builder()
            .id(ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .traitTypeName(TRAIT_TYPE_NAME_2)
            .description(DESCRIPTION_2)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightedTraitTypeRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_TYPE_ID))
        .thenReturn(Stream.of(weightedTraitTypeDTO), Stream.of(weightedTraitTypeDTO2));
    Mockito.when(
            jdbcTemplate.update(
                WeightedTraitTypeRepository.UPDATE_SQL,
                TRAIT_TYPE_NAME_2,
                DESCRIPTION_2,
                TRAIT_TYPE_ID))
        .thenReturn(1);
    WeightedTraitTypeDTO weightedTraitTypeDTOResults =
        new WeightedTraitTypeRepository(jdbcTemplate, beanPropertyRowMapper)
            .update(weightedTraitTypeDTO2);
    Mockito.verify(jdbcTemplate, Mockito.times(2))
        .queryForStream(
            WeightedTraitTypeRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_TYPE_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .update(
            WeightedTraitTypeRepository.UPDATE_SQL,
            TRAIT_TYPE_NAME_2,
            DESCRIPTION_2,
            TRAIT_TYPE_ID);
    assertThat(weightedTraitTypeDTOResults).isEqualTo(weightedTraitTypeDTO2);
  }

  @Test
  void testUpdateNonExistingEntry() {
    WeightedTraitTypeDTO weightedTraitTypeDTO =
        WeightedTraitTypeDTO.builder()
            .id(ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .traitTypeName(TRAIT_TYPE_NAME)
            .description(DESCRIPTION)
            .build();
    // Return a Stream.empty() from the read by id call to imitate a non-existing entry.
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightedTraitTypeRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_TYPE_ID))
        .thenReturn(Stream.empty());
    WeightedTraitTypeDTO weightedTraitTypeDTOResults =
        new WeightedTraitTypeRepository(jdbcTemplate, beanPropertyRowMapper)
            .update(weightedTraitTypeDTO);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(
            WeightedTraitTypeRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_TYPE_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(0))
        .update(
            WeightedTraitTypeRepository.UPDATE_SQL, TRAIT_TYPE_NAME, DESCRIPTION, TRAIT_TYPE_ID);
    assertThat(weightedTraitTypeDTOResults).isEqualTo(null);
  }

  @Test
  void testDeleteExistingEntry() {
    WeightedTraitTypeDTO weightedTraitTypeDTO =
        WeightedTraitTypeDTO.builder()
            .id(ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .traitTypeName(TRAIT_TYPE_NAME)
            .description(DESCRIPTION)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightedTraitTypeRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_TYPE_ID))
        .thenReturn(Stream.of(weightedTraitTypeDTO), Stream.empty());
    boolean isDeletedSuccessfully =
        new WeightedTraitTypeRepository(jdbcTemplate, beanPropertyRowMapper)
            .delete(weightedTraitTypeDTO);
    // Read by id is called twice. Once at the start of the delete method and once at the end of the
    // delete method.
    Mockito.verify(jdbcTemplate, Mockito.times(2))
        .queryForStream(
            WeightedTraitTypeRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_TYPE_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .update(WeightedTraitTypeRepository.DELETE_BY_ID_SQL, TRAIT_TYPE_ID);
    assertThat(isDeletedSuccessfully).isEqualTo(true);
  }

  @Test
  void testDeleteNonExistingEntry() {
    WeightedTraitTypeDTO weightedTraitTypeDTO =
        WeightedTraitTypeDTO.builder()
            .id(ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .traitTypeName(TRAIT_TYPE_NAME)
            .description(DESCRIPTION)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                WeightedTraitTypeRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_TYPE_ID))
        .thenReturn(Stream.empty());
    boolean isDeletedSuccessfully =
        new WeightedTraitTypeRepository(jdbcTemplate, beanPropertyRowMapper)
            .delete(weightedTraitTypeDTO);
    // Read by id is called twice. Once at the start of the delete method and once at the end of the
    // delete method.
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(
            WeightedTraitTypeRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TRAIT_TYPE_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(0))
        .update(WeightedTraitTypeRepository.DELETE_BY_ID_SQL, TRAIT_TYPE_ID);
    assertThat(isDeletedSuccessfully).isEqualTo(false);
  }
}
