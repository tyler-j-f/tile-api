package com.tylerfitzgerald.demo_api.listUtils.finders;

import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.WeightedTraitTypeWeightDTO;
import java.util.List;

public class WeightedTraitTypeWeightsListFinder extends AbstractListFinder {
  public WeightedTraitTypeWeightDTO findWeightedTraitTypeWeightInList(
      List<WeightedTraitTypeWeightDTO> weightlessTraitsList, Long traitTypeWeightId) {
    return (WeightedTraitTypeWeightDTO)
        findInList(weightlessTraitsList, traitTypeWeightId, "getTraitTypeWeightId");
  }
}
