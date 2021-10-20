package com.tylerfitzgerald.demo_api.traits;

import lombok.Data;

@Data
public class Trait {
    private String trait_type;
    private String value;

    public Trait(String trait_type, String value) {
        this.trait_type = trait_type;
        this.value      = value;
    }
}