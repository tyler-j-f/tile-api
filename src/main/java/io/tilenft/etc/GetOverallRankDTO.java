package io.tilenft.etc;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetOverallRankDTO {
  private Long tokenId;
  private Long rank;
  private Long totalTokens;
}
