package io.tilenft.eth.metadata;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SaleMetadataDTOTest {

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
    SaleMetadataDTO saleMetadataDTO =
        SaleMetadataDTO.builder()
            .attributes(ATTRIBUTES_1)
            .description(DESCRIPTION_1)
            .external_url(EXTERNAL_URL_1)
            .image(IMAGE_URL_1)
            .name(NAME_1)
            .build();
    assertThat(saleMetadataDTO.getAttributes().get(0)).isEqualTo(ATTRIBUTE_1);
    assertThat(saleMetadataDTO.getAttributes().get(1)).isEqualTo(ATTRIBUTE_2);
    assertThat(saleMetadataDTO.getAttributes().get(2)).isEqualTo(ATTRIBUTE_3);
    assertThat(saleMetadataDTO.getDescription()).isEqualTo(DESCRIPTION_1);
    assertThat(saleMetadataDTO.getExternal_url()).isEqualTo(EXTERNAL_URL_1);
    assertThat(saleMetadataDTO.getImage()).isEqualTo(IMAGE_URL_1);
    assertThat(saleMetadataDTO.getName()).isEqualTo(NAME_1);
  }
}
