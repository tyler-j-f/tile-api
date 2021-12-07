package com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits;

import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.WeightedTraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraits.WeightedTraitDTO;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits.WeightlessTraitDTO;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WeightlessTraitContext {
  private Long seedForTrait;
  private List<WeightedTraitDTO> weightedTraits = new ArrayList<>();;
  private List<WeightedTraitTypeWeightDTO> weightedTraitTypeWeights = new ArrayList<>();;
  private List<WeightlessTraitDTO> weightlessTraits = new ArrayList<>();
  private int traitTypeId;
  private TokenFacadeDTO burnedNft1;
  private TokenFacadeDTO burnedNft2;
}
