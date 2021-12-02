package com.tylerfitzgerald.demo_api.erc721.token.finders;

import com.tylerfitzgerald.demo_api.sql.tblTraits.TraitDTO;
import java.util.List;

public class WeightedTraitInListFinder extends AbstractTraitInListFinder {
  public TraitDTO findTraitInList(List<TraitDTO> weightlessTraitsList, Long traitTypeId) {
    return (TraitDTO) findInList(weightlessTraitsList, traitTypeId);
  }
}
