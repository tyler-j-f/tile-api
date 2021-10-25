package com.tylerfitzgerald.demo_api.token.traitTypes;

import com.tylerfitzgerald.demo_api.token.traitTypeWeights.TraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.token.traitTypeWeights.TraitTypeWeightRepository;
import com.tylerfitzgerald.demo_api.token.traits.TraitRepository;
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
public class TraitTypeWeightRepositoryTest {

    private JdbcTemplate jdbcTemplate;
    private BeanPropertyRowMapper beanPropertyRowMapper;
    // Value set 1
    private final static Long ID                     = 1L;
    private final static Long TRAIT_TYPE_WEIGHT_ID   = 2L;
    private final static Long TRAIT_TYPE_ID          = 3L;
    private final static Long LIKELIHOOD             = 4L;
    private final static String VALUE                = "STRING_A";
    private final static String DISPLAY_TYPE_VALUE   = "STRING_B";
    // Value set 2
    private final static Long ID_2                   = 5L;
    private final static Long TRAIT_TYPE_WEIGHT_ID_2 = 6L;
    private final static Long TRAIT_TYPE_ID_2        = 7L;
    private final static Long LIKELIHOOD_2           = 8L;
    private final static String VALUE_2              = "STRING_C";
    private final static String DISPLAY_TYPE_VALUE_2 = "STRING_D";

    @BeforeEach
    public void setup() {
        this.jdbcTemplate          = Mockito.mock(JdbcTemplate.class);
        this.beanPropertyRowMapper =  new BeanPropertyRowMapper(TraitTypeWeightDTO.class);
    }

    @Test
    void testConstructor() {
        assertThat(new TraitTypeWeightRepository(jdbcTemplate, beanPropertyRowMapper)).isInstanceOf(TraitTypeWeightRepository.class);
    }

    @Test
    void testCreateNonExisting() {
        TraitTypeWeightDTO traitWeightTypeDTO = TraitTypeWeightDTO.builder().
                id(ID).
                traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID).
                traitTypeId(TRAIT_TYPE_ID).
                likelihood(LIKELIHOOD).
                value(VALUE).
                displayTypeValue(DISPLAY_TYPE_VALUE).
                build();
        Mockito.when(jdbcTemplate.queryForStream(
                TraitTypeWeightRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                TRAIT_TYPE_WEIGHT_ID
        )).thenReturn(Stream.empty(), Stream.of(traitWeightTypeDTO));
        Mockito.when(jdbcTemplate.update(
                TraitTypeWeightRepository.CREATE_SQL,
                TRAIT_TYPE_WEIGHT_ID,
                TRAIT_TYPE_ID,
                LIKELIHOOD,
                VALUE,
                DISPLAY_TYPE_VALUE
        )).thenReturn(1);
        TraitTypeWeightDTO traitTypeDTOResult = new TraitTypeWeightRepository(jdbcTemplate, beanPropertyRowMapper).create(
                traitWeightTypeDTO
        );
        Mockito.verify(jdbcTemplate, Mockito.times(2)).queryForStream(
                TraitTypeWeightRepository.READ_BY_ID_SQL,
                beanPropertyRowMapper,
                TRAIT_TYPE_WEIGHT_ID
        );
        Mockito.verify(jdbcTemplate, Mockito.times(1)).update(
                TraitTypeWeightRepository.CREATE_SQL,
                TRAIT_TYPE_WEIGHT_ID,
                TRAIT_TYPE_ID,
                LIKELIHOOD,
                VALUE,
                DISPLAY_TYPE_VALUE
        );
        assertThat(traitTypeDTOResult).isEqualTo(traitWeightTypeDTO);
    }

}
