package com.tylerfitzgerald.demo_api.sql.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeightlessTraitTypeDTO {
  private Long id;
  private Long weightlessTraitTypeId;
  private String weightlessTraitTypeName;
  private String description;
}
