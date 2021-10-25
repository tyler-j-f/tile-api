package com.tylerfitzgerald.demo_api.token.traits;

import com.tylerfitzgerald.demo_api.token.traitTypes.TraitTypeDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TraitDTOTest {

    // Value set 1
    private final static Long ID = 1L;
    private final static Long TRAIT_ID = 2L;
    private final static Long TRAIT_TYPE_ID = 3L;
    private final static Long TRAIT_TYPE_WEIGHT_ID = 4L;
    // Value set 2
    private final static Long ID_2 = 5L;
    private final static Long TRAIT_ID_2 = 6L;
    private final static Long TRAIT_TYPE_ID_2 = 7L;
    private final static Long TRAIT_TYPE_WEIGHT_ID_2 = 8L;

    @Test
    void testConstructor() {
        // Create with value set 1.
        TraitDTO traitDTO = new TraitDTO(
                ID,
                TRAIT_ID,
                TRAIT_TYPE_ID,
                TRAIT_TYPE_WEIGHT_ID
        );
        // Assert that getters return value set 1.
        assertThat(traitDTO.getId()).isEqualTo(ID);
        assertThat(traitDTO.getTraitId()).isEqualTo(TRAIT_ID);
        assertThat(traitDTO.getTraitTypeId()).isEqualTo(TRAIT_TYPE_ID);
        assertThat(traitDTO.getTraitTypeWeightId()).isEqualTo(TRAIT_TYPE_WEIGHT_ID);
    }

    @Test
    void testGetterSetters() {
        // Create with value set 1 initially.
        TraitDTO traitDTO = new TraitDTO(
                ID,
                TRAIT_ID,
                TRAIT_TYPE_ID,
                TRAIT_TYPE_WEIGHT_ID
        );
        // Set value set 2.
        traitDTO.setId(ID_2);
        traitDTO.setTraitId(TRAIT_ID_2);
        traitDTO.setTraitTypeId(TRAIT_TYPE_ID_2);
        traitDTO.setTraitTypeWeightId(TRAIT_TYPE_WEIGHT_ID_2);
        // Assert that getters return value set 2.
        assertThat(traitDTO.getId()).isEqualTo(ID_2);
        assertThat(traitDTO.getTraitId()).isEqualTo(TRAIT_ID_2);
        assertThat(traitDTO.getTraitTypeId()).isEqualTo(TRAIT_TYPE_ID_2);
        assertThat(traitDTO.getTraitTypeWeightId()).isEqualTo(TRAIT_TYPE_WEIGHT_ID_2);
    }

    @Test
    void testBuilder() {
        TraitDTO.TraitDTOBuilder builder = TraitDTO.
                builder().
                id(ID).
                traitId(TRAIT_ID).
                traitTypeId(TRAIT_TYPE_ID).
                traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID);
        assertThat(builder).isInstanceOf(TraitDTO.TraitDTOBuilder.class);
        TraitDTO traitDTO = builder.build();
        assertThat(traitDTO).isInstanceOf(TraitDTO.class);
        // Assert that getters return value set 1.
        assertThat(traitDTO.getId()).isEqualTo(ID);
        assertThat(traitDTO.getTraitId()).isEqualTo(TRAIT_ID);
        assertThat(traitDTO.getTraitTypeId()).isEqualTo(TRAIT_TYPE_ID);
        assertThat(traitDTO.getTraitTypeWeightId()).isEqualTo(TRAIT_TYPE_WEIGHT_ID);
    }

}
