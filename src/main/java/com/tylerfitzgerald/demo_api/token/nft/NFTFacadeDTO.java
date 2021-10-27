package com.tylerfitzgerald.demo_api.token.nft;

import com.tylerfitzgerald.demo_api.token.TokenDTO;
import com.tylerfitzgerald.demo_api.token.traitTypeWeights.TraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.token.traitTypes.TraitTypeDTO;
import com.tylerfitzgerald.demo_api.token.traits.TraitDTO;
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
  private List<TraitTypeDTO> traitTypes;
  private List<TraitTypeWeightDTO> traitTypeWeights;
  private List<TraitDTO> traits;
}
