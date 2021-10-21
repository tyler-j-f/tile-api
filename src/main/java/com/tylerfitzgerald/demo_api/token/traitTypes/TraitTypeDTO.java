package com.tylerfitzgerald.demo_api.token.traitTypes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TraitTypeDTO {
    private Long id;
    private Long traitTypeId;
    private String traitTypeName;
    private String description;
}
