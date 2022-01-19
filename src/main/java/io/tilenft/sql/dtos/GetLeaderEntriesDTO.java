package io.tilenft.sql.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetLeaderEntriesDTO {
  private long rankCount;
  private String prevValue;
}
