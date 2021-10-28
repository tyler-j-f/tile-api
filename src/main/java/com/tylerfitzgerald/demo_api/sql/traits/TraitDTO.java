package com.tylerfitzgerald.demo_api.sql.traits;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TraitDTO {
  private Long id;
  private Long traitId;
  private Long tokenId;
  private Long traitTypeId;
  private Long traitTypeWeightId;
}
