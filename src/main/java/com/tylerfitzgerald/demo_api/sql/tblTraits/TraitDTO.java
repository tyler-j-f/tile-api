package com.tylerfitzgerald.demo_api.sql.tblTraits;

import com.tylerfitzgerald.demo_api.sql.BaseTraitDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TraitDTO extends BaseTraitDTO {
  private Long traitTypeWeightId;

  @Builder
  public TraitDTO(Long id, Long traitId, Long tokenId, Long traitTypeId, Long traitTypeWeightId) {
    super(id, traitId, tokenId, traitTypeId);
    this.traitTypeWeightId = traitTypeWeightId;
    return;
  }
}
