package io.tilenft.controllers.sql;

import io.tilenft.controllers.BaseController;
import io.tilenft.sql.dtos.WeightedTraitTypeDTO;
import io.tilenft.sql.repositories.WeightedTraitTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = {"/api/traitTypes"})
public class TblTraitTypesController extends BaseController {

  private static final String EMPTY_STRING = "";

  @Autowired private WeightedTraitTypeRepository weightedTraitTypeRepository;

  @GetMapping("getAll")
  public String getAllTraitTypes() {
    return weightedTraitTypeRepository.read().toString();
  }

  @GetMapping("get/{traitTypeId}")
  public String getTraitType(@PathVariable Long traitTypeId) {
    return weightedTraitTypeRepository.readById(traitTypeId).toString();
  }

  @GetMapping("insert/{traitTypeId}")
  public String insertTraitType(
      @PathVariable Long traitTypeId,
      @RequestParam(required = false) String traitTypeName,
      @RequestParam(required = false) String description) {
    if (traitTypeName == null || description == null) {
      return "Please pass a 'traitTypeName' AND 'description' value to create a trait type";
    }
    WeightedTraitTypeDTO weightedTraitTypeDTO =
        weightedTraitTypeRepository.create(
            WeightedTraitTypeDTO.builder()
                .traitTypeId(traitTypeId)
                .traitTypeName(traitTypeName)
                .description(description)
                .build());
    if (weightedTraitTypeDTO == null) {
      return "Cannot create traitTypeId: " + traitTypeId;
    }
    return weightedTraitTypeDTO.toString();
  }

  @GetMapping("update/{traitTypeId}")
  public String updateTraitType(
      @PathVariable Long traitTypeId,
      @RequestParam(required = false) String traitTypeName,
      @RequestParam(required = false) String description) {
    WeightedTraitTypeDTO.WeightedTraitTypeDTOBuilder traitTypeDTOBuilder =
        WeightedTraitTypeDTO.builder().traitTypeId(traitTypeId);
    if (traitTypeName == null && description == null) {
      return "Please pass a 'traitTypeName' OR 'description' value to update a trait type";
    }
    if (traitTypeName != null) {
      traitTypeDTOBuilder = traitTypeDTOBuilder.traitTypeName(traitTypeName);
    }
    if (description != null) {
      traitTypeDTOBuilder = traitTypeDTOBuilder.description(description);
    }
    WeightedTraitTypeDTO weightedTraitTypeDTO =
        weightedTraitTypeRepository.update(traitTypeDTOBuilder.build());
    if (weightedTraitTypeDTO == null) {
      return "Cannot update traitTypeId: " + traitTypeId;
    }
    return weightedTraitTypeDTO.toString();
  }

  @GetMapping("delete/{traitTypeId}")
  public String deleteTraitType(@PathVariable Long traitTypeId) {
    WeightedTraitTypeDTO weightedTraitTypeDTO = weightedTraitTypeRepository.readById(traitTypeId);
    if (!weightedTraitTypeRepository.delete(weightedTraitTypeDTO)) {
      return "Could not delete traitTypeId: " + traitTypeId;
    }
    return "Deleted traitTypeId: " + traitTypeId;
  }
}
