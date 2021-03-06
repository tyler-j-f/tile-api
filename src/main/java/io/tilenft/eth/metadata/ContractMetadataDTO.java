package io.tilenft.eth.metadata;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContractMetadataDTO {
  private String name;
  private String description;
  private String image;
  private String external_link;
  private String seller_fee_basis_points;
  private String fee_recipient;
  private String discord_url;
}
