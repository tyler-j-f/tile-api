package com.tylerfitzgerald.demo_api.listUtils.finders;

import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.WeightedTraitTypeWeightDTO;
import java.util.List;

public class WeightedTraitTypeWeightsListHelper extends AbstractListFinder {
  public WeightedTraitTypeWeightDTO findByTraitTypeWeightId(
      List<WeightedTraitTypeWeightDTO> weightedTraitTypeWeightsList, Long traitTypeWeightId) {
    return (WeightedTraitTypeWeightDTO)
        getFirst(weightedTraitTypeWeightsList, traitTypeWeightId, "getTraitTypeWeightId");
  }

  public List<WeightedTraitTypeWeightDTO> findListByTraitTypeId(
      List<WeightedTraitTypeWeightDTO> weightedTraitTypeWeightsList, Long traitTypeId) {
    return get(weightedTraitTypeWeightsList, traitTypeId, "getTraitTypeId");
  }
}
