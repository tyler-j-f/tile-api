package io.tilenft.erc721.token.initializers;

import io.tilenft.sql.dtos.WeightedTraitTypeDTO;
import io.tilenft.sql.dtos.WeightlessTraitTypeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeedForTrait {
  private WeightlessTraitTypeDTO weightlessTraitTypeDTO;
  private WeightedTraitTypeDTO weightedTraitTypeDTO;
  private Long seedForTraits;
}
