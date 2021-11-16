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
  private Long weightlessTraitId;
  private Long tokenId;
  private Long weightlessTraitTypeId;
  private String value;
  private String displayTypeValue;
}
