package io.tilenft.sql.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseTraitDTO {
  private Long id;
  private Long traitId;
  private Long tokenId;
  private Long traitTypeId;

  @Override
  public String toString() {
    return "BaseTraitDTO{"
        + "id="
        + id
        + ", traitId="
        + traitId
        + ", tokenId="
        + tokenId
        + ", traitTypeId="
        + traitTypeId
        + '}';
  }
}
