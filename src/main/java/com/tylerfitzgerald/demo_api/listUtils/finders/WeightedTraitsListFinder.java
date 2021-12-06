package com.tylerfitzgerald.demo_api.listUtils.finders;

import com.tylerfitzgerald.demo_api.sql.tblTraits.WeightedTraitDTO;
import java.util.List;

public class WeightedTraitsListFinder extends AbstractListFinder {
  public WeightedTraitDTO findByTraitTypeId(
      List<WeightedTraitDTO> weightedTraitsList, Long traitTypeId) {
    return (WeightedTraitDTO) findInList(weightedTraitsList, traitTypeId, "getTraitTypeId");
  }
}
