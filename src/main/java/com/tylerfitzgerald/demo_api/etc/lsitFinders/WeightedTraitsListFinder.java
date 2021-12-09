package com.tylerfitzgerald.demo_api.etc.lsitFinders;

import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitDTO;
import java.util.List;

public class WeightedTraitsListFinder extends AbstractListFinder {
  public WeightedTraitDTO findFirstByTraitTypeId(
      List<WeightedTraitDTO> weightedTraitsList, Long traitTypeId) {
    return (WeightedTraitDTO) getFirst(weightedTraitsList, traitTypeId, "getTraitTypeId");
  }
}
