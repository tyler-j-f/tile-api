package com.tylerfitzgerald.demo_api.listUtils.finders;

import com.tylerfitzgerald.demo_api.sql.tblTraits.WeightedTraitDTO;
import java.util.List;

public class WeightedTraitsListFinder extends AbstractListFinder {
  public WeightedTraitDTO findWeightedTraitInList(
      List<WeightedTraitDTO> weightlessTraitsList, Long traitTypeId) {
    return (WeightedTraitDTO) findInList(weightlessTraitsList, traitTypeId, "getTraitTypeId");
  }
}
