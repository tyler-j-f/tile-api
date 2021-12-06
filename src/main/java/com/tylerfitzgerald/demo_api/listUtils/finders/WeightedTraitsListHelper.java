package com.tylerfitzgerald.demo_api.listUtils.finders;

import com.tylerfitzgerald.demo_api.sql.tblTraits.WeightedTraitDTO;
import java.util.List;

public class WeightedTraitsListHelper extends AbstractListFinder {
  public WeightedTraitDTO findByTraitTypeId(
      List<WeightedTraitDTO> weightedTraitsList, Long traitTypeId) {
    return (WeightedTraitDTO) getFirst(weightedTraitsList, traitTypeId, "getTraitTypeId");
  }

  public List<WeightedTraitDTO> findListByTraitTypeId(
      List<WeightedTraitDTO> weightedTraitsList, Long traitTypeId) {
    return get(weightedTraitsList, traitTypeId, "getTraitTypeId");
  }
}
