package com.tylerfitzgerald.demo_api.controller.sqlControllers;

import com.tylerfitzgerald.demo_api.controller.BaseController;
import com.tylerfitzgerald.demo_api.sql.tblTraits.TraitDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraits.TraitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = {"/api/traits"})
public class TblTraitsController extends BaseController {

  @Autowired private TraitRepository traitRepository;

  @GetMapping("getAll")
  public String getAllTraits() {
    return traitRepository.read().toString();
  }

  @GetMapping("get/{traitId}")
  public String getTrait(@PathVariable Long traitId) {
    return traitRepository.readById(traitId).toString();
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
    TraitDTO traitDTO =
        traitRepository.create(
            TraitDTO.builder()
                .traitId(traitId)
                .tokenId(tokenId)
                .traitTypeId(traitTypeId)
                .traitTypeWeightId(traitTypeWeightId)
                .build());
    if (traitDTO == null) {
      return "Cannot create traitId: " + traitId;
    }
    return traitDTO.toString();
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
    TraitDTO.TraitDTOBuilder traitDTOBuilder = TraitDTO.builder().traitId(traitId);
    if (tokenId != null) {
      traitDTOBuilder = traitDTOBuilder.tokenId(tokenId);
    }
    if (traitTypeId != null) {
      traitDTOBuilder = traitDTOBuilder.traitTypeId(traitTypeId);
    }
    if (traitTypeWeightId != null) {
      traitDTOBuilder = traitDTOBuilder.traitTypeWeightId(traitTypeWeightId);
    }
    TraitDTO traitDTO = traitRepository.update(traitDTOBuilder.build());
    if (traitDTO == null) {
      return "Cannot update traitId: " + traitId;
    }
    return traitDTO.toString();
  }

  @GetMapping("delete/{traitId}")
  public String deleteTrait(@PathVariable Long traitId) {
    TraitDTO traitDTO = traitRepository.readById(traitId);
    if (!traitRepository.delete(traitDTO)) {
      return "Could not delete traitId: " + traitId;
    }
    return "Deleted traitId: " + traitId;
  }
}
