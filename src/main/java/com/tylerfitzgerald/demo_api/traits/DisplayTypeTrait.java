package com.tylerfitzgerald.demo_api.traits;

public class DisplayTypeTrait extends Trait {

    public String display_type;

    public DisplayTypeTrait(String trait_type, String value, String display_type) {
        super(trait_type, value);
        this.display_type = display_type;
    }
}
