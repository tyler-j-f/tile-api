package com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits;

import static org.assertj.core.api.Assertions.assertThat;

import com.tylerfitzgerald.demo_api.sql.tblToken.TokenDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WeightlessTraitDTOTest {

  // Value set 1
  private static final Long ID = 1L;
  private static final Long WEIGHTLESS_TRAIT_ID = 2L;
  private static final Long TOKEN_ID = 3L;
  private static final Long WEIGHTLESS_TRAIT_TYPE_ID = 4L;
  private static final String VALUE = "STRING_A";
  private static final String DISPLAY_TYPE_VALUE = "STRING_B";
  // Value set 2
  private static final Long ID_2 = 5L;
  private static final Long WEIGHTLESS_TRAIT_ID_2 = 6L;
  private static final Long TOKEN_ID_2 = 7L;
  private static final Long WEIGHTLESS_TRAIT_TYPE_ID_2 = 8L;
  private static final String VALUE_2 = "STRING_C";
  private static final String DISPLAY_TYPE_VALUE_2 = "STRING_D";

  @Test
  void testConstructor() {
    // Create with value set 1.
    WeightlessTraitDTO weightlessTraitDTO =
        new WeightlessTraitDTO(
            ID, WEIGHTLESS_TRAIT_ID, TOKEN_ID, WEIGHTLESS_TRAIT_TYPE_ID, VALUE, DISPLAY_TYPE_VALUE);
    // Assert that getters return value set 1.
    assertThat(weightlessTraitDTO.getId()).isEqualTo(ID);
    assertThat(weightlessTraitDTO.getWeightlessTraitId()).isEqualTo(WEIGHTLESS_TRAIT_ID);
    assertThat(weightlessTraitDTO.getTokenId()).isEqualTo(TOKEN_ID);
    assertThat(weightlessTraitDTO.getWeightlessTraitTypeId()).isEqualTo(WEIGHTLESS_TRAIT_TYPE_ID);
    assertThat(weightlessTraitDTO.getValue()).isEqualTo(VALUE);
    assertThat(weightlessTraitDTO.getDisplayTypeValue()).isEqualTo(DISPLAY_TYPE_VALUE);
  }

  //  @Test
  //  void testGetterSetters() {
  //    // Create with value set 1 initially.
  //    TokenDTO tokenDTO =
  //        new TokenDTO(ID, TOKEN_ID, SALE_ID, NAME, DESCRIPTION, EXTERNAL_URL, IMAGE_URL);
  //    // Set value set 2.
  //    tokenDTO.setId(ID_2);
  //    tokenDTO.setTokenId(TOKEN_ID_2);
  //    tokenDTO.setSaleId(SALE_ID_2);
  //    tokenDTO.setName(NAME_2);
  //    tokenDTO.setDescription(DESCRIPTION_2);
  //    tokenDTO.setExternalUrl(EXTERNAL_URL_2);
  //    tokenDTO.setImageUrl(IMAGE_URL_2);
  //    // Assert that getters return value set 2.
  //    assertThat(tokenDTO.getId()).isEqualTo(ID_2);
  //    assertThat(tokenDTO.getTokenId()).isEqualTo(TOKEN_ID_2);
  //    assertThat(tokenDTO.getSaleId()).isEqualTo(SALE_ID_2);
  //    assertThat(tokenDTO.getName()).isEqualTo(NAME_2);
  //    assertThat(tokenDTO.getDescription()).isEqualTo(DESCRIPTION_2);
  //    assertThat(tokenDTO.getExternalUrl()).isEqualTo(EXTERNAL_URL_2);
  //    assertThat(tokenDTO.getImageUrl()).isEqualTo(IMAGE_URL_2);
  //  }
  //
  //  @Test
  //  void testBuilder() {
  //    TokenDTO.TokenDTOBuilder builder =
  //        TokenDTO.builder()
  //            .id(ID)
  //            .tokenId(TOKEN_ID)
  //            .saleId(SALE_ID)
  //            .name(NAME)
  //            .description(DESCRIPTION)
  //            .externalUrl(EXTERNAL_URL)
  //            .imageUrl(IMAGE_URL);
  //    assertThat(builder).isInstanceOf(TokenDTO.TokenDTOBuilder.class);
  //    TokenDTO tokenDTO = builder.build();
  //    assertThat(tokenDTO).isInstanceOf(TokenDTO.class);
  //    // Assert that getters return value set 1.
  //    assertThat(tokenDTO.getId()).isEqualTo(ID);
  //    assertThat(tokenDTO.getTokenId()).isEqualTo(TOKEN_ID);
  //    assertThat(tokenDTO.getSaleId()).isEqualTo(SALE_ID);
  //    assertThat(tokenDTO.getName()).isEqualTo(NAME);
  //    assertThat(tokenDTO.getDescription()).isEqualTo(DESCRIPTION);
  //    assertThat(tokenDTO.getExternalUrl()).isEqualTo(EXTERNAL_URL);
  //    assertThat(tokenDTO.getImageUrl()).isEqualTo(IMAGE_URL);
  //  }
}
