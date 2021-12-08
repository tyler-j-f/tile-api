package com.tylerfitzgerald.demo_api.etc.listFinders;

import com.tylerfitzgerald.demo_api.sql.dtos.WeightlessTraitTypeDTO;
import java.util.List;
import java.util.stream.Collectors;

public class WeightlessTraitTypesFinder extends AbstractListFinder {
  public WeightlessTraitTypeDTO findFirstByWeightlessTraitTypeId(
      List<WeightlessTraitTypeDTO> weightlessTraitTypesList, Long traitTypeId) {
    return (WeightlessTraitTypeDTO)
        getFirst(weightlessTraitTypesList, traitTypeId, "getWeightlessTraitTypeId");
  }

  public List<WeightlessTraitTypeDTO> findByIgnoringTraitTypeIdList(
      List<WeightlessTraitTypeDTO> weightedTraitTypesList, int[] traitTypeIdsToIgnore) {
    return weightedTraitTypesList.stream()
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
}
