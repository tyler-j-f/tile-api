package io.tileNft.erc721.token;

import io.tileNft.sql.dtos.TokenDTO;
import io.tileNft.sql.dtos.WeightedTraitDTO;
import io.tileNft.sql.dtos.WeightedTraitTypeDTO;
import io.tileNft.sql.dtos.WeightedTraitTypeWeightDTO;
import io.tileNft.sql.dtos.WeightlessTraitDTO;
import io.tileNft.sql.dtos.WeightlessTraitTypeDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenFacadeDTO {
  private TokenDTO tokenDTO;
  private List<WeightedTraitDTO> weightedTraits;
  private List<WeightedTraitTypeDTO> weightedTraitTypes;
  private List<WeightedTraitTypeWeightDTO> weightedTraitTypeWeights;
  private List<WeightlessTraitDTO> weightlessTraits;
  private List<WeightlessTraitTypeDTO> weightlessTraitTypes;
}
