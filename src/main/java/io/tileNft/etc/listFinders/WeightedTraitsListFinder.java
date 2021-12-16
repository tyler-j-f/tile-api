package io.tileNft.etc.listFinders;

import io.tileNft.sql.dtos.WeightedTraitDTO;
import java.util.List;

public class WeightedTraitsListFinder extends AbstractListFinder {
  public WeightedTraitDTO findFirstByTraitTypeId(
      List<WeightedTraitDTO> weightedTraitsList, Long traitTypeId) {
    return (WeightedTraitDTO) getFirst(weightedTraitsList, traitTypeId, "getTraitTypeId");
  }
}
