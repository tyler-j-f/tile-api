package io.tileNft.etc.listFinders;

import io.tileNft.sql.dtos.WeightedTraitTypeDTO;
import java.util.List;
import java.util.stream.Collectors;

public class WeightedTraitTypesListFinder extends AbstractListFinder {
  public WeightedTraitTypeDTO findFirstByTraitTypeId(
      List<WeightedTraitTypeDTO> weightedTraitTypesList, Long traitTypeId) {
    return (WeightedTraitTypeDTO) getFirst(weightedTraitTypesList, traitTypeId, "getTraitTypeId");
  }

  public List<WeightedTraitTypeDTO> findByIgnoringTraitTypeIdList(
      List<WeightedTraitTypeDTO> weightedTraitTypesList, int[] traitTypeIdsToIgnore) {
    return weightedTraitTypesList.stream()
        .filter(
            traitType -> {
              for (int typeId : traitTypeIdsToIgnore) {
                if (traitType.getTraitTypeId().equals(Long.valueOf(typeId))) {
                  return false;
                }
              }
              return true;
            })
        .collect(Collectors.toList());
  }
}
