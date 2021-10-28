package com.tylerfitzgerald.demo_api.sql.nft;

import com.tylerfitzgerald.demo_api.sql.tokens.TokenDTO;
import com.tylerfitzgerald.demo_api.sql.traitTypeWeights.TraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.traitTypes.TraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.traits.TraitDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NFTFacadeDTO {
  private TokenDTO tokenDTO;
  private List<TraitDTO> tokenTraits;
  private List<TraitTypeDTO> availableTraitTypes;
  private List<TraitTypeWeightDTO> availableTraitTypeWeights;
}
