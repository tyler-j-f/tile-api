package com.tylerfitzgerald.demo_api.token.nft;

import com.tylerfitzgerald.demo_api.token.TokenDTO;
import com.tylerfitzgerald.demo_api.token.traitTypeWeights.TraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.token.traitTypes.TraitTypeDTO;
import com.tylerfitzgerald.demo_api.token.traits.TraitDTO;
import java.util.List;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
public class NFTFacade {

  private NFTFacadeDTO nftFacadeDTO;

  public NFTFacade(NFTFacadeDTO nftFacadeDTO) {
    this.nftFacadeDTO = nftFacadeDTO;
  }

  public TokenDTO getTokenDTO() {
    return nftFacadeDTO.getTokenDTO();
  }

  public List<TraitDTO> getTokenTraits() {
    return nftFacadeDTO.getTokenTraits();
  }

  public List<TraitTypeDTO> getAvailableTraitTypes() {
    return nftFacadeDTO.getAvailableTraitTypes();
  }

  public List<TraitTypeWeightDTO> getAvailableTraitTypeWeights() {
    return nftFacadeDTO.getAvailableTraitTypeWeights();
  }

}
