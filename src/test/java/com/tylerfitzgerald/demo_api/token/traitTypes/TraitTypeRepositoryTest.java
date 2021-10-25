package com.tylerfitzgerald.demo_api.token.traitTypes;

import com.tylerfitzgerald.demo_api.token.traits.TraitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TraitTypeRepositoryTest {

    private JdbcTemplate jdbcTemplate;
    private BeanPropertyRowMapper beanPropertyRowMapper;
    // Value set 1
    private final static Long ID                = 1L;
    private final static Long TRAIT_TYPE_ID     = 2L;
    private final static String TRAIT_TYPE_NAME = "STRING_A";
    private final static String DESCRIPTION     = "STRING_B";

    @BeforeEach
    public void setup() {
        this.jdbcTemplate          = Mockito.mock(JdbcTemplate.class);
        this.beanPropertyRowMapper =  new BeanPropertyRowMapper(TraitTypeDTO.class);
    }

    @Test
    void testConstructor() {
        assertThat(new TraitTypeRepository(jdbcTemplate, beanPropertyRowMapper)).isInstanceOf(TraitTypeRepository.class);
    }

    @Test
    void testCreateNonExisting() {
        TraitTypeDTO traitTypeDTO = TraitTypeDTO.builder().id(ID).traitTypeId(TRAIT_TYPE_ID).traitTypeName(TRAIT_TYPE_NAME).description(DESCRIPTION).build();
        Mockito.when(jdbcTemplate.queryForStream(
                TraitTypeRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                TRAIT_TYPE_ID
        )).thenReturn(Stream.empty(), Stream.of(traitTypeDTO));
        Mockito.when(jdbcTemplate.update(
                TraitTypeRepository.CREATE_SQL,
                TRAIT_TYPE_ID,
                TRAIT_TYPE_NAME,
                DESCRIPTION
        )).thenReturn(1);
        TraitTypeDTO traitTypeDTOResult = new TraitTypeRepository(jdbcTemplate, beanPropertyRowMapper).create(
                traitTypeDTO
        );
        Mockito.verify(jdbcTemplate, Mockito.times(2)).queryForStream(
                TraitTypeRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                TRAIT_TYPE_ID
        );
        Mockito.verify(jdbcTemplate, Mockito.times(1)).update(
                TraitTypeRepository.CREATE_SQL,
                TRAIT_TYPE_ID,
                TRAIT_TYPE_NAME,
                DESCRIPTION
        );
        assertThat(traitTypeDTOResult).isEqualTo(traitTypeDTO);
    }

    @Test
    void testCreateExisting() {
        TraitTypeDTO traitTypeDTO = TraitTypeDTO.builder().id(ID).traitTypeId(TRAIT_TYPE_ID).traitTypeName(TRAIT_TYPE_NAME).description(DESCRIPTION).build();
        Mockito.when(jdbcTemplate.queryForStream(
                TraitTypeRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                TRAIT_TYPE_ID
        )).thenReturn(Stream.of(traitTypeDTO));
        TraitTypeDTO traitTypeDTOResult = new TraitTypeRepository(jdbcTemplate, beanPropertyRowMapper).create(
                traitTypeDTO
        );
        Mockito.verify(jdbcTemplate, Mockito.times(1)).queryForStream(
                TraitTypeRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                TRAIT_TYPE_ID
        );
        Mockito.verify(jdbcTemplate, Mockito.times(0)).update(
                TraitTypeRepository.CREATE_SQL,
                TRAIT_TYPE_ID,
                TRAIT_TYPE_NAME,
                DESCRIPTION
        );
        assertThat(traitTypeDTOResult).isEqualTo(null);
    }

}
