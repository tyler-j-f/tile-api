package com.tylerfitzgerald.demo_api.token.traits;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TraitDTO {
    private Long id;
    private Long traitId;
    private Long traitTypeId;
    private String value;
    private String displayTypeValue;
}
