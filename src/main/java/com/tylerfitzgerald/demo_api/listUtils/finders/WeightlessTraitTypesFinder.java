package com.tylerfitzgerald.demo_api.listUtils.finders;

import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraitTypes.WeightlessTraitTypeDTO;
import java.util.List;

public class WeightlessTraitTypesFinder extends AbstractListFinder {
  public WeightlessTraitTypeDTO findFirstByWeightlessTraitTypeId(
      List<WeightlessTraitTypeDTO> weightlessTraitTypesList, Long traitTypeId) {
    return (WeightlessTraitTypeDTO)
        getFirst(weightlessTraitTypesList, traitTypeId, "getWeightlessTraitTypeId");
  }
}
