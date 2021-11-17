package com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits;

import com.tylerfitzgerald.demo_api.sql.BaseTraitDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeightlessTraitDTO extends BaseTraitDTO {
  private String value;
  private String displayTypeValue;

  @Builder
  public WeightlessTraitDTO(
      Long id,
      Long traitId,
      Long tokenId,
      Long traitTypeId,
      String value,
      String displayTypeValue) {
    super(id, traitId, tokenId, traitTypeId);
    this.value = value;
    this.displayTypeValue = displayTypeValue;
  }
}
