package com.tylerfitzgerald.demo_api.erc721.contract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractDTO {
  private String name;
  private String description;
  private String image;
  private String external_link;
  private String seller_fee_basis_points;
  private String fee_recipient;
}
