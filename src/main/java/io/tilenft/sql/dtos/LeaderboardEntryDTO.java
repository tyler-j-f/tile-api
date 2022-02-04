package io.tilenft.sql.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LeaderboardEntryDTO {
  private Long tokenId;
  private Long rankCount;
  private String overallRankPoints;
}
