package io.tilenft.eth.token.traits.creators;

import io.tilenft.eth.token.TokenFacadeDTO;
import io.tilenft.sql.dtos.WeightedTraitDTO;
import io.tilenft.sql.dtos.WeightedTraitTypeDTO;
import io.tilenft.sql.dtos.WeightedTraitTypeWeightDTO;
import io.tilenft.sql.dtos.WeightlessTraitTypeDTO;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TraitsCreatorContext {
  private Long tokenId;
  private Long seedForTraits;
  private List<WeightlessTraitTypeDTO> weightlessTraitTypes;
  private List<WeightedTraitDTO> weightedTraits;
  private List<WeightedTraitTypeDTO> weightedTraitTypes;
  private List<WeightedTraitTypeWeightDTO> weightedTraitTypeWeights;
  private TokenFacadeDTO burnedNft1;
  private TokenFacadeDTO burnedNft2;
}
