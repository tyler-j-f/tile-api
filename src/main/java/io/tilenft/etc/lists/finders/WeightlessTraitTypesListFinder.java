package io.tilenft.etc.lists.finders;

import io.tilenft.sql.dtos.WeightlessTraitTypeDTO;
import java.util.List;
import java.util.stream.Collectors;

public class WeightlessTraitTypesListFinder extends AbstractListFinder {

  /**
   * Find a subset of the inputted list where your ignore any weightlessTraitTypeId value in
   * traitTypeIdsToIgnore array.
   *
   * @param weightlessTraitTypesList List of WeightlessTraitTypeDTO entries
   * @param traitTypeIdsToIgnore Array of weightlessTraitTypeId values to remove from the inputted
   *     list
   * @return Subset of inputted list
   */
  public List<WeightlessTraitTypeDTO> findByIgnoringTraitTypeIdList(
      List<WeightlessTraitTypeDTO> weightlessTraitTypesList, int[] traitTypeIdsToIgnore) {
    return weightlessTraitTypesList.stream()
        .filter(
            traitType -> {
              for (int typeId : traitTypeIdsToIgnore) {
                if (traitType.getWeightlessTraitTypeId().equals(Long.valueOf(typeId))) {
                  return false;
                }
              }
              return true;
            })
        .collect(Collectors.toList());
  }

  /**
   * Find the first entry for inputted list equal to the inputted weightlessTraitTypeId.
   *
   * @param weightlessTraitTypesList List of WeightlessTraitTypeDTO entries
   * @param weightlessTraitTypeId Long weightlessTraitTypeId to look for
   * @return Subset of inputted list
   */
  public WeightlessTraitTypeDTO findFirstByWeightlessTraitTypeId(
      List<WeightlessTraitTypeDTO> weightlessTraitTypesList, Long weightlessTraitTypeId) {
    return (WeightlessTraitTypeDTO)
        getFirst(weightlessTraitTypesList, weightlessTraitTypeId, "getWeightlessTraitTypeId");
  }
}
