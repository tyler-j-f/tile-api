package com.tylerfitzgerald.demo_api.erc721.metadata;

import static org.assertj.core.api.Assertions.assertThat;

import com.tylerfitzgerald.demo_api.erc721.ContractMetadataDTO;
import org.junit.jupiter.api.Test;

public class ContractMetadataDTOTest {

  // Value set 1
  private static final String NAME_1 = "A";
  private static final String DESCRIPTION_1 = "B";
  private static final String IMAGE_URL_1 = "C";
  private static final String EXTERNAL_LINK_1 = "D";
  private static final String SELLER_FEE_BASIS_POINTS_1 = "E";
  private static final String FEE_RECIPIENT_1 = "F";

  @Test
  void testBuilder() {
    ContractMetadataDTO contractMetadataDTO =
        ContractMetadataDTO.builder()
            .name(NAME_1)
            .description(DESCRIPTION_1)
            .image(IMAGE_URL_1)
            .external_link(EXTERNAL_LINK_1)
            .seller_fee_basis_points(SELLER_FEE_BASIS_POINTS_1)
            .fee_recipient(FEE_RECIPIENT_1)
            .build();
    assertThat(contractMetadataDTO.getName()).isEqualTo(NAME_1);
    assertThat(contractMetadataDTO.getDescription()).isEqualTo(DESCRIPTION_1);
    assertThat(contractMetadataDTO.getImage()).isEqualTo(IMAGE_URL_1);
    assertThat(contractMetadataDTO.getExternal_link()).isEqualTo(EXTERNAL_LINK_1);
    assertThat(contractMetadataDTO.getSeller_fee_basis_points())
        .isEqualTo(SELLER_FEE_BASIS_POINTS_1);
    assertThat(contractMetadataDTO.getFee_recipient()).isEqualTo(FEE_RECIPIENT_1);
  }
}
