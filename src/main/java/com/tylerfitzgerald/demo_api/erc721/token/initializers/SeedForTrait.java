package com.tylerfitzgerald.demo_api.erc721.token.initializers;

import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightlessTraitTypeDTO;
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
