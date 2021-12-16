package io.tileNft.erc721.token.traits.creators;

import io.tileNft.erc721.token.TokenFacadeDTO;
import io.tileNft.sql.dtos.WeightedTraitDTO;
import io.tileNft.sql.dtos.WeightedTraitTypeDTO;
import io.tileNft.sql.dtos.WeightedTraitTypeWeightDTO;
import io.tileNft.sql.dtos.WeightlessTraitTypeDTO;
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
