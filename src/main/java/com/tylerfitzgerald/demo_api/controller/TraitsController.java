package com.tylerfitzgerald.demo_api.controller;

import com.tylerfitzgerald.demo_api.token.traits.TraitDTO;
import com.tylerfitzgerald.demo_api.token.traits.TraitsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping({"insert/{traitId}/{traitTypeId}/{value}", "insert/{traitId}/{traitTypeId}/{value}/{displayTypeValue}"})
    public String insertTrait(
            @PathVariable Long traitId,
            @PathVariable Long traitTypeId,
            @PathVariable String value,
            @PathVariable(required = false) String displayTypeValue
    ) {
         TraitDTO traitDTO = traitsRepository.create(
                 displayTypeValue != null ?
                         getNewDisplayTypeTraitDTO(traitId, traitTypeId, value, displayTypeValue) :
                         getNewTraitDTO(traitId, traitTypeId, value)
         );
        if (traitDTO == null) {
            return "Cannot create traitId: " + traitId;
        }
        return traitDTO.toString();
    }

    @GetMapping({"update/{traitId}/{traitTypeId}/{displayTypeValue}", "update/{traitId}/{traitTypeId}/{value}/{displayTypeValue}"})
    public String updateTrait(
            @PathVariable Long traitId,
            @PathVariable Long traitTypeId,
            @PathVariable String value,
            @PathVariable(required = false) String displayTypeValue
    ) {
        TraitDTO traitDTO = traitsRepository.update(
                displayTypeValue != null ?
                        getNewDisplayTypeTraitDTO(traitId, traitTypeId, value, displayTypeValue) :
                        getNewTraitDTO(traitId, traitTypeId, value)
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

    private TraitDTO getNewDisplayTypeTraitDTO(
            Long traitId,
            Long traitTypeId,
            String value,
            String displayTypeValue
    ) {
        return TraitDTO.
                builder().
                traitId(traitId).
                traitTypeId(traitTypeId).
                value(value).
                displayTypeValue(displayTypeValue).
                build();
    }

    private TraitDTO getNewTraitDTO(
            Long traitId,
            Long traitTypeId,
            String value
    ) {
        return TraitDTO.
                builder().
                traitId(traitId).
                traitTypeId(traitTypeId).
                value(value).
                build();
    }

}
