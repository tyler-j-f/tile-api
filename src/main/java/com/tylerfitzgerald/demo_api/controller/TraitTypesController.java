package com.tylerfitzgerald.demo_api.controller;

import com.tylerfitzgerald.demo_api.sql.TraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.TraitTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = {"/api/traitTypes"})
public class TraitTypesController extends BaseController {

  private static final String EMPTY_STRING = "";

  @Autowired private TraitTypeRepository traitTypeRepository;

  @GetMapping("getAll")
  public String getAllTraitTypes() {
    return traitTypeRepository.read().toString();
  }

  @GetMapping("get/{traitTypeId}")
  public String getTraitType(@PathVariable Long traitTypeId) {
    return traitTypeRepository.readById(traitTypeId).toString();
  }

  @GetMapping("insert/{traitTypeId}")
  public String insertTraitType(
      @PathVariable Long traitTypeId,
      @RequestParam(required = false) String traitTypeName,
      @RequestParam(required = false) String description) {
    if (traitTypeName == null || description == null) {
      return "Please pass a 'traitTypeName' AND 'description' value to create a trait type";
    }
    TraitTypeDTO traitTypeDTO =
        traitTypeRepository.create(
            TraitTypeDTO.builder()
                .traitTypeId(traitTypeId)
                .traitTypeName(traitTypeName)
                .description(description)
                .build());
    if (traitTypeDTO == null) {
      return "Cannot create traitTypeId: " + traitTypeId;
    }
    return traitTypeDTO.toString();
  }

  @GetMapping("update/{traitTypeId}")
  public String updateTraitType(
      @PathVariable Long traitTypeId,
      @RequestParam(required = false) String traitTypeName,
      @RequestParam(required = false) String description) {
    TraitTypeDTO.TraitTypeDTOBuilder traitTypeDTOBuilder =
        TraitTypeDTO.builder().traitTypeId(traitTypeId);
    if (traitTypeName == null && description == null) {
      return "Please pass a 'traitTypeName' OR 'description' value to update a trait type";
    }
    if (traitTypeName != null) {
      traitTypeDTOBuilder = traitTypeDTOBuilder.traitTypeName(traitTypeName);
    }
    if (description != null) {
      traitTypeDTOBuilder = traitTypeDTOBuilder.description(description);
    }
    TraitTypeDTO traitTypeDTO = traitTypeRepository.update(traitTypeDTOBuilder.build());
    if (traitTypeDTO == null) {
      return "Cannot update traitTypeId: " + traitTypeId;
    }
    return traitTypeDTO.toString();
  }

  @GetMapping("delete/{traitTypeId}")
  public String deleteTraitType(@PathVariable Long traitTypeId) {
    TraitTypeDTO traitTypeDTO = traitTypeRepository.readById(traitTypeId);
    if (!traitTypeRepository.delete(traitTypeDTO)) {
      return "Could not delete traitTypeId: " + traitTypeId;
    }
    return "Deleted traitTypeId: " + traitTypeId;
  }
}
