package io.tilenft.controller.sqlControllers;

import io.tilenft.controller.BaseController;
import io.tilenft.sql.dtos.WeightlessTraitDTO;
import io.tilenft.sql.repositories.WeightlessTraitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api/weightlessTraits"})
public class TblWeightlessTraitsController extends BaseController {

  @Autowired private WeightlessTraitRepository weightlessTraitRepository;

  @GetMapping("getAll")
  public String getAllTraitTypeWeights() {
    return weightlessTraitRepository.read().toString();
  }

  @GetMapping("get/{weightlessTraitId}")
  public String getTraitTypeWeight(@PathVariable Long weightlessTraitId) {
    return weightlessTraitRepository.readById(weightlessTraitId).toString();
  }

  @GetMapping("insert/{weightlessTraitId}")
  public String insertTraitTypeWeight(
      @PathVariable Long weightlessTraitId,
      @RequestParam Long tokenId,
      @RequestParam Long weightlessTraitTypeId,
      @RequestParam String value,
      @RequestParam String displayTypeValue) {
    if (weightlessTraitId == null
        || tokenId == null
        || weightlessTraitTypeId == null
        || value == null
        || displayTypeValue == null) {
      return "Please pass a 'weightlessTraitId', 'tokenId', 'weightlessTraitTypeId', 'value', AND 'displayTypeValue' to create a weightless trait";
    }
    WeightlessTraitDTO weightlessTraitDTO =
        weightlessTraitRepository.create(
            WeightlessTraitDTO.builder()
                .traitId(weightlessTraitId)
                .tokenId(tokenId)
                .traitTypeId(weightlessTraitTypeId)
                .value(value)
                .displayTypeValue(displayTypeValue)
                .build());
    if (weightlessTraitDTO == null) {
      return "Cannot create weightlessTraitId: " + weightlessTraitId;
    }
    return weightlessTraitDTO.toString();
  }

  @GetMapping("update/{weightlessTraitId}")
  public String updateTraitTypeWeight(
      @PathVariable Long weightlessTraitId,
      @RequestParam Long tokenId,
      @RequestParam Long weightlessTraitTypeId,
      @RequestParam String value,
      @RequestParam String displayTypeValue) {
    if (weightlessTraitId == null
        && tokenId == null
        && weightlessTraitTypeId == null
        && value == null
        && displayTypeValue == null) {
      return "Please pass a 'weightlessTraitId', 'tokenId', 'weightlessTraitTypeId', 'value', OR 'displayTypeValue' to update a weightless trait";
    }
    WeightlessTraitDTO.WeightlessTraitDTOBuilder weightlessTraitDTOBuilder =
        WeightlessTraitDTO.builder().traitId(weightlessTraitId);
    if (tokenId != null) {
      weightlessTraitDTOBuilder = weightlessTraitDTOBuilder.tokenId(tokenId);
    }
    if (weightlessTraitTypeId != null) {
      weightlessTraitDTOBuilder = weightlessTraitDTOBuilder.traitTypeId(weightlessTraitTypeId);
    }
    if (value != null) {
      weightlessTraitDTOBuilder = weightlessTraitDTOBuilder.value(value);
    }
    if (displayTypeValue != null) {
      weightlessTraitDTOBuilder = weightlessTraitDTOBuilder.displayTypeValue(displayTypeValue);
    }
    WeightlessTraitDTO weightlessTraitDTO =
        weightlessTraitRepository.update(weightlessTraitDTOBuilder.build());
    if (weightlessTraitDTO == null) {
      return "Cannot update weightlessTraitId: " + weightlessTraitId;
    }
    return weightlessTraitDTO.toString();
  }

  @GetMapping("delete/{weightlessTraitId}")
  public String deleteTraitTypeWeight(@PathVariable Long weightlessTraitId) {
    WeightlessTraitDTO weightlessTraitDTO = weightlessTraitRepository.readById(weightlessTraitId);
    if (!weightlessTraitRepository.delete(weightlessTraitDTO)) {
      return "Could not delete weightlessTraitId: " + weightlessTraitId;
    }
    return "Deleted weightlessTraitId: " + weightlessTraitId;
  }
}
