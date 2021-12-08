package com.tylerfitzgerald.demo_api.sql.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeightedTraitTypeWeightDTO {
  private Long id;
  private Long traitTypeWeightId;
  private Long traitTypeId;
  private Long likelihood;
  private String value;
  private String displayTypeValue;
}
