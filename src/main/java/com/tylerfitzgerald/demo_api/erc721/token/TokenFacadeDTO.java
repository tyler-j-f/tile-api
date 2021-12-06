package com.tylerfitzgerald.demo_api.erc721.token;

import com.tylerfitzgerald.demo_api.sql.tblToken.TokenDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.WeightedTraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypes.WeightedTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraits.WeightedTraitDTO;
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
  private List<WeightedTraitDTO> weightedTraits;
  private List<WeightedTraitTypeDTO> weightedTraitTypes;
  private List<WeightedTraitTypeWeightDTO> weightedTraitTypeWeights;
  private List<WeightlessTraitDTO> weightlessTraits;
  private List<WeightlessTraitTypeDTO> weightlessTraitTypes;
}
