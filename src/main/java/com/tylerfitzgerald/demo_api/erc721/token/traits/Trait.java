package com.tylerfitzgerald.demo_api.erc721.token.traits;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trait {
  private String trait_type;
  private String value;
}