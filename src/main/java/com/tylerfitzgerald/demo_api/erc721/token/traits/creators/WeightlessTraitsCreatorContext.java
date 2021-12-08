package com.tylerfitzgerald.demo_api.erc721.token.traits.creators;

import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeWeightDTO;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeightlessTraitsCreatorContext {
  private Long tokenId;
  private Long seedForTraits;
  private List<WeightedTraitDTO> weightedTraits;
  private List<WeightedTraitTypeDTO> weightedTraitTypes;
  private List<WeightedTraitTypeWeightDTO> weightedTraitTypeWeights;
  private TokenFacadeDTO burnedNft1;
  private TokenFacadeDTO burnedNft2;
}
