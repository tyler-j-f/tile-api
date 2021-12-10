package com.tylerfitzgerald.demo_api.erc721.metadata;

import static org.assertj.core.api.Assertions.assertThat;

import com.tylerfitzgerald.demo_api.erc721.TokenMetadataDTO;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TokenMetadataDTOTest {

  // Value set 1
  private static final ArrayList<Object> ATTRIBUTES_1 = new ArrayList<>();
  private static final String ATTRIBUTE_1 = "A";
  private static final String ATTRIBUTE_2 = "B";
  private static final String ATTRIBUTE_3 = "C";
  private static final String DESCRIPTION_1 = "D";
  private static final String EXTERNAL_URL_1 = "E";
  private static final String IMAGE_URL_1 = "F";
  private static final String NAME_1 = "G";

  @BeforeEach
  public void setup() {
    ATTRIBUTES_1.add(ATTRIBUTE_1);
    ATTRIBUTES_1.add(ATTRIBUTE_2);
    ATTRIBUTES_1.add(ATTRIBUTE_3);
  }

  @Test
  void testBuilder() {
    TokenMetadataDTO tokenMetadataDTO =
        TokenMetadataDTO.builder()
            .attributes(ATTRIBUTES_1)
            .description(DESCRIPTION_1)
            .external_url(EXTERNAL_URL_1)
            .image(IMAGE_URL_1)
            .name(NAME_1)
            .build();
    assertThat(tokenMetadataDTO.getAttributes().get(0)).isEqualTo(ATTRIBUTE_1);
    assertThat(tokenMetadataDTO.getAttributes().get(1)).isEqualTo(ATTRIBUTE_2);
    assertThat(tokenMetadataDTO.getAttributes().get(2)).isEqualTo(ATTRIBUTE_3);
    assertThat(tokenMetadataDTO.getDescription()).isEqualTo(DESCRIPTION_1);
    assertThat(tokenMetadataDTO.getExternal_url()).isEqualTo(EXTERNAL_URL_1);
    assertThat(tokenMetadataDTO.getImage()).isEqualTo(IMAGE_URL_1);
    assertThat(tokenMetadataDTO.getName()).isEqualTo(NAME_1);
  }
}
