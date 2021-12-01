package com.tylerfitzgerald.demo_api.sql.tblTraits;

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
public class TraitDTO extends BaseTraitDTO {
  private Long traitTypeWeightId;

  @Builder
  public TraitDTO(Long id, Long traitId, Long tokenId, Long traitTypeId, Long traitTypeWeightId) {
    super(id, traitId, tokenId, traitTypeId);
    this.traitTypeWeightId = traitTypeWeightId;
    return;
  }

  @Override
  public String toString() {
    return "TraitDTO{"
        + "id="
        + getId()
        + ", traitTypeWeightId='"
        + getTraitTypeWeightId()
        + ", traitId="
        + getTraitId()
        + ", tokenId="
        + getTokenId()
        + ", traitTypeId="
        + getTraitTypeId()
        + '}';
  }
}
