package com.tylerfitzgerald.demo_api.token.traits;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TraitsRepositoryTest {

    private JdbcTemplate jdbcTemplate;
    private BeanPropertyRowMapper beanPropertyRowMapper;
    // Value set 1
    private final static Long ID = 1L;
    private final static Long TRAIT_ID = 2L;
    private final static Long TRAIT_TYPE_ID = 3L;
    private final static Long TRAIT_TYPE_WEIGHT_ID = 4L;

    @BeforeEach
    public void setup() {
        this.jdbcTemplate          = Mockito.mock(JdbcTemplate.class);
        this.beanPropertyRowMapper =  new BeanPropertyRowMapper(TraitDTO.class);
    }

    @Test
    void testConstructor() {
        assertThat(new TraitsRepository(jdbcTemplate, beanPropertyRowMapper)).isInstanceOf(TraitsRepository.class);
    }

    @Test
    void testCreateNonExisting() {
        TraitDTO traitDTO = TraitDTO.builder().id(ID).traitId(TRAIT_ID).traitTypeId(TRAIT_TYPE_ID).traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID).build();
        Mockito.when(jdbcTemplate.queryForStream(
                TraitsRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                TRAIT_ID
        )).thenReturn(Stream.empty(), Stream.of(traitDTO));
        Mockito.when(jdbcTemplate.update(
                TraitsRepository.CREATE_SQL,
                TRAIT_ID,
                TRAIT_TYPE_ID,
                TRAIT_TYPE_WEIGHT_ID
        )).thenReturn(1);
        TraitDTO traitDTOResult = new TraitsRepository(jdbcTemplate, beanPropertyRowMapper).create(
                traitDTO
        );
        Mockito.verify(jdbcTemplate, Mockito.times(2)).queryForStream(
                TraitsRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                TRAIT_ID
        );
        Mockito.verify(jdbcTemplate, Mockito.times(1)).update(
                TraitsRepository.CREATE_SQL,
                TRAIT_ID,
                TRAIT_TYPE_ID,
                TRAIT_TYPE_WEIGHT_ID
        );
        assertThat(traitDTOResult).isEqualTo(traitDTO);
    }


    @Test
    void testCreateExisting() {
        TraitDTO traitDTO = TraitDTO.builder().id(ID).traitId(TRAIT_ID).traitTypeId(TRAIT_TYPE_ID).traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID).build();
        Mockito.when(jdbcTemplate.queryForStream(
                TraitsRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                TRAIT_ID
        )).thenReturn(Stream.of(traitDTO));
        TraitDTO traitDTOResult = new TraitsRepository(jdbcTemplate, beanPropertyRowMapper).create(
                traitDTO
        );
        Mockito.verify(jdbcTemplate, Mockito.times(1)).queryForStream(
                TraitsRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                TRAIT_ID
        );
        Mockito.verify(jdbcTemplate, Mockito.times(0)).update(
                TraitsRepository.CREATE_SQL,
                TRAIT_ID,
                TRAIT_TYPE_ID,
                TRAIT_TYPE_WEIGHT_ID
        );
        assertThat(traitDTOResult).isEqualTo(null);
    }

    @Test
    void testRead() {
        TraitDTO traitDTO = TraitDTO.builder().id(ID).traitId(TRAIT_ID).traitTypeId(TRAIT_TYPE_ID).traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID).build();
        Mockito.when(jdbcTemplate.queryForStream(
                TraitsRepository.READ_SQL,
                beanPropertyRowMapper
        )).thenReturn(Stream.of(traitDTO));
        List<TraitDTO> traits = new TraitsRepository(jdbcTemplate, beanPropertyRowMapper).read();
        Mockito.verify(jdbcTemplate, Mockito.times(1)).queryForStream(
                TraitsRepository.READ_SQL,
                beanPropertyRowMapper
        );
        assertThat(traits.get(0)).isEqualTo(traitDTO);
    }

    @Test
    void testUpdateExistingEntry() {
        TraitDTO traitDTO = TraitDTO.builder().id(ID).traitId(TRAIT_ID).traitTypeId(TRAIT_TYPE_ID).traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID).build();
        Mockito.when(jdbcTemplate.queryForStream(
                TraitsRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                TRAIT_ID
        )).thenReturn(Stream.of(traitDTO));
        new TraitsRepository(jdbcTemplate, beanPropertyRowMapper).update(traitDTO);
        Mockito.verify(jdbcTemplate, Mockito.times(1)).queryForStream(
                TraitsRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                TRAIT_ID
        );
        Mockito.verify(jdbcTemplate, Mockito.times(1)).update(
                TraitsRepository.UPDATE_SQL,
                TRAIT_TYPE_ID,
                TRAIT_TYPE_WEIGHT_ID
        );
    }

    @Test
    void testUpdateNonExistingEntry() {
        TraitDTO traitDTO = TraitDTO.builder().id(ID).traitId(TRAIT_ID).traitTypeId(TRAIT_TYPE_ID).traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID).build();
        // Return a Stream.empty() from the read by id call to imitate a non-existing entry.
        Mockito.when(jdbcTemplate.queryForStream(
                TraitsRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                TRAIT_ID
        )).thenReturn(Stream.empty());
        new TraitsRepository(jdbcTemplate, beanPropertyRowMapper).update(traitDTO);
        Mockito.verify(jdbcTemplate, Mockito.times(1)).queryForStream(
                TraitsRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                TRAIT_ID
        );
        Mockito.verify(jdbcTemplate, Mockito.times(0)).update(
                TraitsRepository.UPDATE_SQL,
                TRAIT_TYPE_ID,
                TRAIT_TYPE_WEIGHT_ID
        );
    }

    @Test
    void testDeleteExistingEntry() {
        TraitDTO traitDTO = TraitDTO.builder().id(ID).traitId(TRAIT_ID).traitTypeId(TRAIT_TYPE_ID).traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID).build();
        Mockito.when(jdbcTemplate.queryForStream(
                TraitsRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                TRAIT_ID
        )).thenReturn(Stream.of(traitDTO), Stream.empty());
        boolean isDeletedSuccessfully = new TraitsRepository(jdbcTemplate, beanPropertyRowMapper).delete(traitDTO);
        // Read by id is called twice. Once at the start of the delete method and once at the end of the delete method.
        Mockito.verify(jdbcTemplate, Mockito.times(2)).queryForStream(
                TraitsRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                TRAIT_ID
        );
        Mockito.verify(jdbcTemplate, Mockito.times(1)).update(
                TraitsRepository.DELETE_BY_ID_SQL,
                TRAIT_ID
        );
        assertThat(isDeletedSuccessfully).isEqualTo(true);
    }

    @Test
    void testDeleteNonExistingEntry() {
        TraitDTO traitDTO = TraitDTO.builder().id(ID).traitId(TRAIT_ID).traitTypeId(TRAIT_TYPE_ID).traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID).build();
        Mockito.when(jdbcTemplate.queryForStream(
                TraitsRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                TRAIT_ID
        )).thenReturn(Stream.empty());
        boolean isDeletedSuccessfully = new TraitsRepository(jdbcTemplate, beanPropertyRowMapper).delete(traitDTO);
        Mockito.verify(jdbcTemplate, Mockito.times(1)).queryForStream(
                TraitsRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                TRAIT_ID
        );
        assertThat(isDeletedSuccessfully).isEqualTo(false);
    }

}
