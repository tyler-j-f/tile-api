package com.tylerfitzgerald.demo_api.controller;

import com.tylerfitzgerald.demo_api.token.traitTypes.TraitTypeDTO;
import com.tylerfitzgerald.demo_api.token.traitTypes.TraitTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api/traitTypes"})
public class TraitTypesController {

    @Autowired
    private TraitTypeRepository traitTypeRepository;

    @GetMapping("getAll")
    public String getAllTraitTypes() {
        return traitTypeRepository.read().toString();
    }

    @GetMapping("get/{traitTypeId}")
    public String getTraitType(@PathVariable Long traitTypeId) {
        return traitTypeRepository.readById(traitTypeId).toString();
    }

    @GetMapping("insert/{traitTypeId}")
    public String insertTraitType(@PathVariable Long traitTypeId) {
        TraitTypeDTO traitTypeDTO = traitTypeRepository.create(
                TraitTypeDTO.builder().
                        traitTypeId(traitTypeId).
                        traitTypeName("Personality").
                        description("Personality of the NFT").
                        build()
        );
        if (traitTypeDTO == null) {
            return "Cannot create traitTypeId: " + traitTypeId;
        }
        return traitTypeDTO.toString();
    }

    @GetMapping("update/{traitTypeId}")
    public String updateTraitType(@PathVariable Long traitTypeId) {
        TraitTypeDTO traitTypeDTO = traitTypeRepository.update(
                TraitTypeDTO.builder().
                        traitTypeId(traitTypeId).
                        traitTypeName("Mood").
                        description("Mood of the NFT").
                        build()
        );
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
