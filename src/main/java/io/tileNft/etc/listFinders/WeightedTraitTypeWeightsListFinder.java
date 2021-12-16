package io.tileNft.etc.listFinders;

import io.tileNft.sql.dtos.WeightedTraitTypeWeightDTO;
import java.util.List;

public class WeightedTraitTypeWeightsListFinder extends AbstractListFinder {
  public WeightedTraitTypeWeightDTO findFirstByTraitTypeWeightId(
      List<WeightedTraitTypeWeightDTO> weightedTraitTypeWeightsList, Long traitTypeWeightId) {
    return (WeightedTraitTypeWeightDTO)
        getFirst(weightedTraitTypeWeightsList, traitTypeWeightId, "getTraitTypeWeightId");
  }

  public List<WeightedTraitTypeWeightDTO> findByTraitTypeId(
      List<WeightedTraitTypeWeightDTO> weightedTraitTypeWeightsList, Long traitTypeId) {
    return get(weightedTraitTypeWeightsList, traitTypeId, "getTraitTypeId");
  }
}
