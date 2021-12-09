package com.tylerfitzgerald.demo_api.etc;

import com.tylerfitzgerald.demo_api.sql.dtos.WeightlessTraitDTO;
import java.util.List;

public class WeightlessTraitsListFinder extends AbstractListFinder {
  public WeightlessTraitDTO findFirstByTraitTypeId(
      List<WeightlessTraitDTO> weightlessTraitsList, Long traitTypeId) {
    return (WeightlessTraitDTO) getFirst(weightlessTraitsList, traitTypeId, "getTraitTypeId");
  }
}
