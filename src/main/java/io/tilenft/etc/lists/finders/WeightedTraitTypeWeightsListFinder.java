package io.tilenft.etc.lists.finders;

import io.tilenft.sql.dtos.WeightedTraitTypeWeightDTO;
import java.util.List;

public class WeightedTraitTypeWeightsListFinder extends AbstractListFinder {

  /**
   * Gets the first WeightedTraitTypeWeightDTO entry in the inputted list which matches the inputted
   * traitTypeWeightId
   *
   * @param weightedTraitTypeWeightsList List<WeightedTraitTypeWeightDTO>
   * @param traitTypeWeightId Long
   * @return List One of the list entries
   */
  public WeightedTraitTypeWeightDTO findFirstByTraitTypeWeightId(
      List<WeightedTraitTypeWeightDTO> weightedTraitTypeWeightsList, Long traitTypeWeightId) {
    return (WeightedTraitTypeWeightDTO)
        getFirst(weightedTraitTypeWeightsList, traitTypeWeightId, "getTraitTypeWeightId");
  }

  /**
   * Gets a WeightedTraitTypeWeightDTO entry by traitTypeId for the inputted list
   *
   * @param weightedTraitTypeWeightsList List<WeightedTraitTypeWeightDTO>
   * @param traitTypeId Long
   * @return List One of the list entries
   */
  public List<WeightedTraitTypeWeightDTO> findByTraitTypeId(
      List<WeightedTraitTypeWeightDTO> weightedTraitTypeWeightsList, Long traitTypeId) {
    return get(weightedTraitTypeWeightsList, traitTypeId, "getTraitTypeId");
  }
}
