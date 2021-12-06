package com.tylerfitzgerald.demo_api.listUtils.finders;

import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.TraitTypeWeightDTO;
import java.util.List;

public class WeightedTraitTypeWeightsListFinder extends AbstractListFinder {
  public TraitTypeWeightDTO findWeightedTraitTypeWeightInList(
      List<TraitTypeWeightDTO> weightlessTraitsList, Long traitTypeWeightId) {
    return (TraitTypeWeightDTO)
        findInList(weightlessTraitsList, traitTypeWeightId, "getTraitTypeWeightId");
  }
}
