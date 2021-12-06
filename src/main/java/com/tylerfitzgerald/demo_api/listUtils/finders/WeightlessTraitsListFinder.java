package com.tylerfitzgerald.demo_api.listUtils.finders;

import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits.WeightlessTraitDTO;
import java.util.List;

public class WeightlessTraitsListFinder extends AbstractListFinder {
  public WeightlessTraitDTO findByTraitTypeId(
      List<WeightlessTraitDTO> weightlessTraitsList, Long traitTypeId) {
    return (WeightlessTraitDTO) findInList(weightlessTraitsList, traitTypeId, "getTraitTypeId");
  }
}
