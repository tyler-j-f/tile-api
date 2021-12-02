package com.tylerfitzgerald.demo_api.erc721.token.finders;

import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.TraitTypeWeightDTO;
import java.util.List;

public class WeightedTraitTypeListFinder extends AbstractListFinder {
  public TraitTypeWeightDTO findTraitTypeWeightInList(
      List<TraitTypeWeightDTO> weightlessTraitsList, Long traitTypeWeightId) {
    return (TraitTypeWeightDTO)
        findInList(weightlessTraitsList, traitTypeWeightId, "getTraitTypeWeightId");
  }
}
