package com.tylerfitzgerald.demo_api.etc.listFinders;

import com.tylerfitzgerald.demo_api.sql.tblTraits.WeightedTraitDTO;
import java.util.List;

public class WeightedTraitsFinder extends AbstractListFinder {
  public WeightedTraitDTO findFirstByTraitTypeId(
      List<WeightedTraitDTO> weightedTraitsList, Long traitTypeId) {
    return (WeightedTraitDTO) getFirst(weightedTraitsList, traitTypeId, "getTraitTypeId");
  }
}
