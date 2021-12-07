package com.tylerfitzgerald.demo_api.etc.listFinders;

import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits.WeightlessTraitDTO;
import java.util.List;

public class WeightlessTraitsFinder extends AbstractListFinder {
  public WeightlessTraitDTO findFirstByTraitTypeId(
      List<WeightlessTraitDTO> weightlessTraitsList, Long traitTypeId) {
    return (WeightlessTraitDTO) getFirst(weightlessTraitsList, traitTypeId, "getTraitTypeId");
  }
}
