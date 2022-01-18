package io.tilenft.sql.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TotalTokensDTO {
  private Long totalUnburntTokens;
  private Long totalTokens;
}
