package com.tylerfitzgerald.demo_api.erc721.token.finders;

import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits.WeightlessTraitDTO;
import java.util.List;

public class WeightlessTraitInListFinder extends AbstractTraitInListFinder {
  public WeightlessTraitDTO findTraitInList(
      List<WeightlessTraitDTO> weightlessTraitsList, Long traitTypeId) {
    return (WeightlessTraitDTO) findInList(weightlessTraitsList, traitTypeId);
  }
}
