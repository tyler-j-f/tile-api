package com.tylerfitzgerald.demo_api.sql.tblTraitTypes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeightedTraitTypeDTO {
  private Long id;
  private Long traitTypeId;
  private String traitTypeName;
  private String description;
}
