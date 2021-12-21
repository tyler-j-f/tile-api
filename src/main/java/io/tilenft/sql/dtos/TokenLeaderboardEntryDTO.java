package io.tilenft.sql.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenLeaderboardEntryDTO {
  private Long weightlessId;
  private Long weightlessTraitId;
  private Long weightlessTokenId;
  private Long weightlessTraitTypeId;
  private String weightlessValue;
  private String weightlessDisplayTypeValue;
  private Long weightedId;
  private Long weightedTraitId;
  private Long weightedTokenId;
  private Long weightedTraitTypeId;
  private Long weightedTraitTypeWeightId;
}
