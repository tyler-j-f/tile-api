package com.tylerfitzgerald.demo_api.nft.traits;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Trait {
  private String trait_type;
  private String value;

  public Trait(String trait_type, String value) {
    this.trait_type = trait_type;
    this.value = value;
  }
}
