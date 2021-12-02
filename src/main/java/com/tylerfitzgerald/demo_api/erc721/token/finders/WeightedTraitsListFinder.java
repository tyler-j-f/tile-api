package com.tylerfitzgerald.demo_api.erc721.token.finders;

import com.tylerfitzgerald.demo_api.sql.tblTraits.TraitDTO;
import java.util.List;

public class WeightedTraitsListFinder extends AbstractListFinder {
  public TraitDTO findWeightedTraitInList(List<TraitDTO> weightlessTraitsList, Long traitTypeId) {
    return (TraitDTO) findInList(weightlessTraitsList, traitTypeId, "getTraitTypeId");
  }
}
