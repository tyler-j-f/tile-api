package com.tylerfitzgerald.demo_api.sql.traitTypeWeights;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TraitTypeWeightDTO {
  private Long id;
  private Long traitTypeWeightId;
  private Long traitTypeId;
  private Long likelihood;
  private String value;
  private String displayTypeValue;
}
