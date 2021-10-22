package com.tylerfitzgerald.demo_api.controller;

import com.tylerfitzgerald.demo_api.token.traits.TraitDTO;
import com.tylerfitzgerald.demo_api.token.traits.TraitsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = {"/api/traits"})
public class TraitsController {

    @Autowired
    private TraitsRepository traitsRepository;

    @GetMapping("getAll")
    public String getAllTraits() {
        return traitsRepository.read().toString();
    }

    @GetMapping("get/{traitId}")
    public String getTrait(@PathVariable Long traitId) {
        return traitsRepository.readById(traitId).toString();
    }

    @GetMapping("insert/{traitId}")
    public String insertTrait(
            @PathVariable Long traitId,
            @RequestParam Long traitTypeId,
            @RequestParam Long traitTypeWeightId
    ) {
        if (traitTypeId == null ||  traitTypeWeightId == null) {
            return "Please pass a 'traitTypeId' and 'traitTypeWeightId' to create a trait";
        }
         TraitDTO traitDTO = traitsRepository.create(
                 TraitDTO.
                         builder().
                         traitId(traitId).
                         traitTypeId(traitTypeId).
                         traitTypeWeightId(traitTypeWeightId).
                         build()
         );
        if (traitDTO == null) {
            return "Cannot create traitId: " + traitId;
        }
        return traitDTO.toString();
    }

    @GetMapping({"update/{traitId}/{traitTypeId}/{displayTypeValue}", "update/{traitId}/{traitTypeId}/{value}/{displayTypeValue}"})
    public String updateTrait(
            @PathVariable Long traitId,
            @RequestParam(required = false) Long traitTypeId,
            @RequestParam(required = false) Long traitTypeWeightId
    ) {
        if (traitTypeId == null &&  traitTypeWeightId == null) {
            return "Please pass a 'traitTypeId' or 'traitTypeWeightId' to update a trait";
        }
        TraitDTO.TraitDTOBuilder traitDTOBuilder = TraitDTO.builder().traitId(traitId);
        if (traitTypeId != null) {
            traitDTOBuilder = traitDTOBuilder.traitTypeId(traitTypeId);
        }
        if (traitTypeWeightId != null) {
            traitDTOBuilder = traitDTOBuilder.traitTypeWeightId(traitTypeWeightId);
        }
        TraitDTO traitDTO = traitsRepository.update(
                traitDTOBuilder.build()
        );
        if (traitDTO == null) {
            return "Cannot update traitId: " + traitId;
        }
        return traitDTO.toString();
    }

    @GetMapping("delete/{traitId}")
    public String deleteTrait(@PathVariable Long traitId) {
        TraitDTO traitDTO = traitsRepository.readById(traitId);
        if (!traitsRepository.delete(traitDTO)) {
            return "Could not delete traitId: " + traitId;
        }
        return "Deleted traitId: " + traitId;
    }

}
