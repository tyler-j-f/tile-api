package com.tylerfitzgerald.demo_api.controller.sqlControllers;

import com.tylerfitzgerald.demo_api.controller.BaseController;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitDTO;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightedTraitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = {"/api/traits"})
public class TblTraitsController extends BaseController {

  @Autowired private WeightedTraitRepository weightedTraitRepository;

  @GetMapping("getAll")
  public String getAllTraits() {
    return weightedTraitRepository.read().toString();
  }

  @GetMapping("get/{traitId}")
  public String getTrait(@PathVariable Long traitId) {
    return weightedTraitRepository.readById(traitId).toString();
  }

  @GetMapping("insert/{traitId}")
  public String insertTrait(
      @PathVariable Long traitId,
      @RequestParam Long tokenId,
      @RequestParam Long traitTypeId,
      @RequestParam Long traitTypeWeightId) {
    if (tokenId == null || traitTypeId == null || traitTypeWeightId == null) {
      return "Please pass a 'tokenId', 'traitTypeId', AND 'traitTypeWeightId' to create a trait";
    }
    WeightedTraitDTO weightedTraitDTO =
        weightedTraitRepository.create(
            WeightedTraitDTO.builder()
                .traitId(traitId)
                .tokenId(tokenId)
                .traitTypeId(traitTypeId)
                .traitTypeWeightId(traitTypeWeightId)
                .build());
    if (weightedTraitDTO == null) {
      return "Cannot create traitId: " + traitId;
    }
    return weightedTraitDTO.toString();
  }

  @GetMapping("update/{traitId}")
  public String updateTrait(
      @PathVariable Long traitId,
      @RequestParam(required = false) Long tokenId,
      @RequestParam(required = false) Long traitTypeId,
      @RequestParam(required = false) Long traitTypeWeightId) {
    if (traitTypeId == null && traitTypeWeightId == null) {
      return "Please pass a 'tokenId', 'traitTypeId', OR 'traitTypeWeightId' to update a trait";
    }
    WeightedTraitDTO.WeightedTraitDTOBuilder traitDTOBuilder =
        WeightedTraitDTO.builder().traitId(traitId);
    if (tokenId != null) {
      traitDTOBuilder = traitDTOBuilder.tokenId(tokenId);
    }
    if (traitTypeId != null) {
      traitDTOBuilder = traitDTOBuilder.traitTypeId(traitTypeId);
    }
    if (traitTypeWeightId != null) {
      traitDTOBuilder = traitDTOBuilder.traitTypeWeightId(traitTypeWeightId);
    }
    WeightedTraitDTO weightedTraitDTO = weightedTraitRepository.update(traitDTOBuilder.build());
    if (weightedTraitDTO == null) {
      return "Cannot update traitId: " + traitId;
    }
    return weightedTraitDTO.toString();
  }

  @GetMapping("delete/{traitId}")
  public String deleteTrait(@PathVariable Long traitId) {
    WeightedTraitDTO weightedTraitDTO = weightedTraitRepository.readById(traitId);
    if (!weightedTraitRepository.delete(weightedTraitDTO)) {
      return "Could not delete traitId: " + traitId;
    }
    return "Deleted traitId: " + traitId;
  }
}
