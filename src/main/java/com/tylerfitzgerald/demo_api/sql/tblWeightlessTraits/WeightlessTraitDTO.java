package com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeightlessTraitDTO {
  private Long id;
  private Long traitId;
  private Long tokenId;
  private Long traitTypeId;
  private String value;
  private String displayTypeValue;
}
