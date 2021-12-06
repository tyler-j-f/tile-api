package com.tylerfitzgerald.demo_api.listUtils.finders;

import com.tylerfitzgerald.demo_api.sql.tblTraitTypes.WeightedTraitTypeDTO;
import java.util.List;

public class WeightedTraitTypesFinder extends AbstractListFinder {
  public WeightedTraitTypeDTO findFirstByTraitTypeId(
      List<WeightedTraitTypeDTO> weightedTraitTypesList, Long traitTypeId) {
    return (WeightedTraitTypeDTO) getFirst(weightedTraitTypesList, traitTypeId, "getTraitTypeId");
  }
}
