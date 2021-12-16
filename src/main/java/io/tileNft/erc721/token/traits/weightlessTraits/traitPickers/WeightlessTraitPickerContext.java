package io.tileNft.erc721.token.traits.weightlessTraits.traitPickers;

import io.tileNft.erc721.token.TokenFacadeDTO;
import io.tileNft.sql.dtos.WeightedTraitDTO;
import io.tileNft.sql.dtos.WeightedTraitTypeWeightDTO;
import io.tileNft.sql.dtos.WeightlessTraitDTO;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WeightlessTraitPickerContext {
  private Long seedForTrait;
  private List<WeightedTraitDTO> weightedTraits = new ArrayList<>();;
  private List<WeightedTraitTypeWeightDTO> weightedTraitTypeWeights = new ArrayList<>();;
  private List<WeightlessTraitDTO> weightlessTraits = new ArrayList<>();
  private int traitTypeId;
  private TokenFacadeDTO burnedNft1;
  private TokenFacadeDTO burnedNft2;
}
