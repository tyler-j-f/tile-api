package com.tylerfitzgerald.demo_api.sql.nft;

import com.tylerfitzgerald.demo_api.sql.tblToken.TokenDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.TraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypes.TraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraits.TraitDTO;
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
