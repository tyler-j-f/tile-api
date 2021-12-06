package com.tylerfitzgerald.demo_api.listUtils.finders;

import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraitTypes.WeightlessTraitTypeDTO;
import java.util.List;

public class WeightlessTraitTypesListFinder extends AbstractListFinder {
  public WeightlessTraitTypeDTO findByWeightlessTraitTypeId(
      List<WeightlessTraitTypeDTO> weightlessTraitTypesList, Long traitTypeId) {
    return (WeightlessTraitTypeDTO)
        findInList(weightlessTraitTypesList, traitTypeId, "getWeightlessTraitTypeId");
  }
}
