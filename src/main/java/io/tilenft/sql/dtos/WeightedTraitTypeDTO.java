package io.tilenft.sql.dtos;

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

  @Override
  public String toString() {
    return "WeightedTraitTypeDTO{"
        + "id="
        + getId()
        + ", traitTypeId="
        + getTraitTypeId()
        + ", traitTypeName="
        + getTraitTypeName()
        + ", description="
        + getDescription()
        + '}';
  }
}
