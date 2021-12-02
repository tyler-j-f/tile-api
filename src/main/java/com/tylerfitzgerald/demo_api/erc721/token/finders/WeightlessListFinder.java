package com.tylerfitzgerald.demo_api.erc721.token.finders;

import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits.WeightlessTraitDTO;
import java.util.List;

public class WeightlessListFinder extends AbstractListFinder {
  public WeightlessTraitDTO findWeightlessTraitInList(
      List<WeightlessTraitDTO> weightlessTraitsList, Long traitTypeId) {
    return (WeightlessTraitDTO) findInList(weightlessTraitsList, traitTypeId, "getTraitTypeId");
  }
}
