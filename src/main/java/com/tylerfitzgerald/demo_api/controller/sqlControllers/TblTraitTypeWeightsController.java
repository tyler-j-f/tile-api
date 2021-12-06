package com.tylerfitzgerald.demo_api.controller.sqlControllers;

import com.tylerfitzgerald.demo_api.controller.BaseController;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.WeightedTraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.WeightedTraitTypeWeightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = {"/api/traitTypeWeights"})
public class TblTraitTypeWeightsController extends BaseController {

  private static final String EMPTY_STRING = "";

  @Autowired private WeightedTraitTypeWeightRepository weightedTraitTypeWeightRepository;

  @GetMapping("getAll")
  public String getAllTraitTypeWeights() {
    return weightedTraitTypeWeightRepository.read().toString();
  }

  @GetMapping("get/{traitTypeWeightId}")
  public String getTraitTypeWeight(@PathVariable Long traitTypeWeightId) {
    return weightedTraitTypeWeightRepository.readById(traitTypeWeightId).toString();
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
    WeightedTraitTypeWeightDTO traitWeightTypeDTO =
        weightedTraitTypeWeightRepository.create(
            WeightedTraitTypeWeightDTO.builder()
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
    WeightedTraitTypeWeightDTO.WeightedTraitTypeWeightDTOBuilder traitTypeWeightDTOBuilder =
        WeightedTraitTypeWeightDTO.builder().traitTypeWeightId(traitTypeWeightId);
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
    WeightedTraitTypeWeightDTO traitWeightTypeDTO =
        weightedTraitTypeWeightRepository.update(traitTypeWeightDTOBuilder.build());
    if (traitWeightTypeDTO == null) {
      return "Cannot update traitTypeWeightId: " + traitTypeWeightId;
    }
    return traitWeightTypeDTO.toString();
  }

  @GetMapping("delete/{traitTypeWeightId}")
  public String deleteTraitTypeWeight(@PathVariable Long traitTypeWeightId) {
    WeightedTraitTypeWeightDTO traitWeightTypeDTO =
        weightedTraitTypeWeightRepository.readById(traitTypeWeightId);
    if (!weightedTraitTypeWeightRepository.delete(traitWeightTypeDTO)) {
      return "Could not delete traitTypeWeightId: " + traitTypeWeightId;
    }
    return "Deleted traitTypeWeightId: " + traitTypeWeightId;
  }
}
