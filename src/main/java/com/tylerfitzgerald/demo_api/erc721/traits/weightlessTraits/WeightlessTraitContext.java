package com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits;

import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.TraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraits.TraitDTO;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits.WeightlessTraitDTO;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WeightlessTraitContext {
  private Long seedForTrait;
  private List<TraitDTO> weightedTraits = new ArrayList<>();;
  private List<TraitTypeWeightDTO> weightedTraitTypeWeights = new ArrayList<>();;
  private List<WeightlessTraitDTO> weightlessTraits = new ArrayList<>();
}
