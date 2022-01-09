package io.tilenft.etc.lists.finders;

import io.tilenft.sql.dtos.WeightedTraitTypeWeightDTO;
import java.util.List;
import java.util.stream.Collectors;

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

  public List<WeightedTraitTypeWeightDTO> findByIgnoringTraitTypeWeightIdList(
      List<WeightedTraitTypeWeightDTO> traitTypeWeightsList, int[] traitTypeWeightIdsToIgnore) {
    System.out.println(
        "DEBUG: findByIgnoringTraitTypeWeightIdList input traitTypeWeightsList: "
            + traitTypeWeightsList);
    System.out.println(
        "DEBUG: findByIgnoringTraitTypeWeightIdList input traitTypeWeightIdsToIgnore: "
            + traitTypeWeightIdsToIgnore);
    return traitTypeWeightsList.stream()
        .filter(
            traitTypeWeight -> {
              System.out.println("DEBUG: traitTypeWeight: " + traitTypeWeight);
              for (int traitTypeWeightId : traitTypeWeightIdsToIgnore) {
                if (traitTypeWeight.getTraitTypeWeightId().equals((long) traitTypeWeightId)) {
                  System.out.println(
                      "DEBUG: false: "
                          + traitTypeWeight.getTraitTypeWeightId()
                          + ", traitTypeWeightId: "
                          + traitTypeWeightId);
                  return false;
                }
              }
              System.out.println("DEBUG: true");
              return true;
            })
        .collect(Collectors.toList());
  }
}
