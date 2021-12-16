package io.tileNft.etc.listFinders;

import io.tileNft.sql.dtos.WeightlessTraitDTO;
import java.util.List;

public class WeightlessTraitsListFinder extends AbstractListFinder {
  public WeightlessTraitDTO findFirstByTraitTypeId(
      List<WeightlessTraitDTO> weightlessTraitsList, Long traitTypeId) {
    return (WeightlessTraitDTO) getFirst(weightlessTraitsList, traitTypeId, "getTraitTypeId");
  }
}
