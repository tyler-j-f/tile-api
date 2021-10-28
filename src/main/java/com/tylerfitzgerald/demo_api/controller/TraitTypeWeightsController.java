package com.tylerfitzgerald.demo_api.controller;

import com.tylerfitzgerald.demo_api.sql.traitTypeWeights.TraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.traitTypeWeights.TraitTypeWeightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = {"/api/traitTypeWeights"})
public class TraitTypeWeightsController extends BaseController {

  private static final String EMPTY_STRING = "";

  @Autowired private TraitTypeWeightRepository traitTypeWeightRepository;

  @GetMapping("getAll")
  public String getAllTraitTypeWeights() {
    return traitTypeWeightRepository.read().toString();
  }

  @GetMapping("get/{traitTypeWeightId}")
  public String getTraitTypeWeight(@PathVariable Long traitTypeWeightId) {
    return traitTypeWeightRepository.readById(traitTypeWeightId).toString();
  }

  @GetMapping("insert/{traitTypeWeightId}")
  public String insertTraitTypeWeight(
      @PathVariable Long traitTypeWeightId,
      @RequestParam Long traitTypeId,
      @RequestParam Long likelihood,
      @RequestParam String value,
      @RequestParam String displayTypeValue) {
    if (traitTypeId == null || likelihood == null || value == null || displayTypeValue == null) {
      return "Please pass a 'traitTypeId', 'likelihood', 'value', AND 'displayTypeValue' to create a trait type weight";
    }
    TraitTypeWeightDTO traitWeightTypeDTO =
        traitTypeWeightRepository.create(
            TraitTypeWeightDTO.builder()
                .traitTypeWeightId(traitTypeWeightId)
                .traitTypeId(traitTypeId)
                .likelihood(likelihood)
                .value(value)
                .displayTypeValue(displayTypeValue)
                .build());
    if (traitWeightTypeDTO == null) {
      return "Cannot create traitTypeWeightId: " + traitTypeWeightId;
    }
    return traitWeightTypeDTO.toString();
  }

  @GetMapping("update/{traitTypeWeightId}")
  public String updateTraitTypeWeight(
      @PathVariable Long traitTypeWeightId,
      @RequestParam(required = false) Long traitTypeId,
      @RequestParam(required = false) Long likelihood,
      @RequestParam(required = false) String value,
      @RequestParam(required = false) String displayTypeValue) {
    if (traitTypeId == null && likelihood == null && value == null && displayTypeValue == null) {
      return "Please pass a 'traitTypeId', 'likelihood', 'value', OR 'displayTypeValue' to update a trait type weight";
    }
    TraitTypeWeightDTO.TraitTypeWeightDTOBuilder traitTypeWeightDTOBuilder =
        TraitTypeWeightDTO.builder().traitTypeWeightId(traitTypeWeightId);
    if (traitTypeId != null) {
      traitTypeWeightDTOBuilder = traitTypeWeightDTOBuilder.traitTypeId(traitTypeId);
    }
    if (likelihood != null) {
      traitTypeWeightDTOBuilder = traitTypeWeightDTOBuilder.likelihood(likelihood);
    }
    if (value != null) {
      traitTypeWeightDTOBuilder = traitTypeWeightDTOBuilder.value(value);
    }
    if (displayTypeValue != null) {
      traitTypeWeightDTOBuilder = traitTypeWeightDTOBuilder.displayTypeValue(displayTypeValue);
    }
    TraitTypeWeightDTO traitWeightTypeDTO =
        traitTypeWeightRepository.update(traitTypeWeightDTOBuilder.build());
    if (traitWeightTypeDTO == null) {
      return "Cannot update traitTypeWeightId: " + traitTypeWeightId;
    }
    return traitWeightTypeDTO.toString();
  }

  @GetMapping("delete/{traitTypeWeightId}")
  public String deleteTraitTypeWeight(@PathVariable Long traitTypeWeightId) {
    TraitTypeWeightDTO traitWeightTypeDTO = traitTypeWeightRepository.readById(traitTypeWeightId);
    if (!traitTypeWeightRepository.delete(traitWeightTypeDTO)) {
      return "Could not delete traitTypeWeightId: " + traitTypeWeightId;
    }
    return "Deleted traitTypeWeightId: " + traitTypeWeightId;
  }
}
