package com.tylerfitzgerald.demo_api.sql.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDTO {
  private Long id;
  private Long tokenId;
  private Long saleId;
  private String name;
  private String description;
  private String externalUrl;
  private String imageUrl;
}
