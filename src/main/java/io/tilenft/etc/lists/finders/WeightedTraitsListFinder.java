package io.tilenft.etc.lists.finders;

import io.tilenft.sql.dtos.WeightedTraitDTO;
import java.util.List;

public class WeightedTraitsListFinder extends AbstractListFinder {
  public WeightedTraitDTO findFirstByTraitTypeId(
      List<WeightedTraitDTO> weightedTraitsList, Long traitTypeId) {
    return (WeightedTraitDTO) getFirst(weightedTraitsList, traitTypeId, "getTraitTypeId");
  }
}
