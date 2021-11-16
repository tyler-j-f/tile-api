package com.tylerfitzgerald.demo_api.controller.sqlControllers;

import com.tylerfitzgerald.demo_api.controller.BaseController;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraitTypes.WeightlessTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraitTypes.WeightlessTraitTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api/weightlessTraitTypes"})
public class TblWeightlessTraitTypesController extends BaseController {

  @Autowired private WeightlessTraitTypeRepository weightlessTraitTypeRepository;

  @GetMapping("getAll")
  public String getAllTraitTypeWeights() {
    return weightlessTraitTypeRepository.read().toString();
  }

  @GetMapping("get/{weightlessTraitTypeId}")
  public String getTraitTypeWeight(@PathVariable Long weightlessTraitTypeId) {
    return weightlessTraitTypeRepository.readById(weightlessTraitTypeId).toString();
  }

  @GetMapping("insert/{weightlessTraitTypeId}")
  public String insertTraitTypeWeight(
      @PathVariable Long weightlessTraitTypeId,
      @RequestParam String weightlessTraitTypeName,
      @RequestParam String description) {
    if (weightlessTraitTypeId == null || weightlessTraitTypeName == null || description == null) {
      return "Please pass a 'weightlessTraitTypeId', 'weightlessTraitTypeName', AND 'description' to create a weightless trait type";
    }
    WeightlessTraitTypeDTO weightlessTraitTypeDTO =
        weightlessTraitTypeRepository.create(
            WeightlessTraitTypeDTO.builder()
                .weightlessTraitTypeId(weightlessTraitTypeId)
                .weightlessTraitTypeName(weightlessTraitTypeName)
                .description(description)
                .build());
    if (weightlessTraitTypeDTO == null) {
      return "Cannot create weightlessTraitTypeId: " + weightlessTraitTypeId;
    }
    return weightlessTraitTypeDTO.toString();
  }

  @GetMapping("update/{weightlessTraitTypeId}")
  public String updateTraitTypeWeight(
      @PathVariable Long weightlessTraitTypeId,
      @RequestParam String weightlessTraitTypeName,
      @RequestParam String description) {
    if (weightlessTraitTypeId == null && weightlessTraitTypeName == null && description == null) {
      return "Please pass a 'weightlessTraitTypeId', 'weightlessTraitTypeName', OR 'description' to update a weightless trait type";
    }
    WeightlessTraitTypeDTO.WeightlessTraitTypeDTOBuilder weightlessTraitTypeDTOBuilder =
        WeightlessTraitTypeDTO.builder().weightlessTraitTypeId(weightlessTraitTypeId);
    if (weightlessTraitTypeName != null) {
      weightlessTraitTypeDTOBuilder =
          weightlessTraitTypeDTOBuilder.weightlessTraitTypeName(weightlessTraitTypeName);
    }
    if (description != null) {
      weightlessTraitTypeDTOBuilder = weightlessTraitTypeDTOBuilder.description(description);
    }
    WeightlessTraitTypeDTO WeightlessTraitTypeDTO =
        weightlessTraitTypeRepository.update(weightlessTraitTypeDTOBuilder.build());
    if (WeightlessTraitTypeDTO == null) {
      return "Cannot update weightlessTraitTypeId: " + weightlessTraitTypeId;
    }
    return WeightlessTraitTypeDTO.toString();
  }

  @GetMapping("delete/{weightlessTraitTypeId}")
  public String deleteTraitTypeWeight(@PathVariable Long weightlessTraitTypeId) {
    WeightlessTraitTypeDTO WeightlessTraitTypeDTO =
        weightlessTraitTypeRepository.readById(weightlessTraitTypeId);
    if (!weightlessTraitTypeRepository.delete(WeightlessTraitTypeDTO)) {
      return "Could not delete weightlessTraitTypeId: " + weightlessTraitTypeId;
    }
    return "Deleted weightlessTraitTypeId: " + weightlessTraitTypeId;
  }
}
