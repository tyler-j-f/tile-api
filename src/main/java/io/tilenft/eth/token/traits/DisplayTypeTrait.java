package io.tilenft.eth.token.traits;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisplayTypeTrait {
  private String trait_type;
  private String value;
  private String display_type;
}
