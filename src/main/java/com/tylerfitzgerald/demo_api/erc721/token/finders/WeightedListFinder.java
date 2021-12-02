package com.tylerfitzgerald.demo_api.erc721.token.finders;

import com.tylerfitzgerald.demo_api.sql.tblTraits.TraitDTO;
import java.util.List;

public class WeightedListFinder extends AbstractListFinder {
  public TraitDTO findTraitInList(List<TraitDTO> weightlessTraitsList, Long traitTypeId) {
    return (TraitDTO) findInList(weightlessTraitsList, traitTypeId, "getTraitTypeId");
  }
}
