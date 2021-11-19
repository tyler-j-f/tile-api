package com.tylerfitzgerald.demo_api.erc721.token;

import com.tylerfitzgerald.demo_api.sql.tblToken.TokenDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.TraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypes.TraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraits.TraitDTO;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraitTypes.WeightlessTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits.WeightlessTraitDTO;
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
  private List<TraitDTO> tokenTraits;
  private List<TraitTypeDTO> availableTraitTypes;
  private List<TraitTypeWeightDTO> availableTraitTypeWeights;
  private List<WeightlessTraitDTO> weightlessTraits;
  private List<WeightlessTraitTypeDTO> weightlessTraitTypes;
}
