package com.tylerfitzgerald.demo_api.erc721.token.traits.creators.weighted;

import com.tylerfitzgerald.demo_api.erc721.token.initializers.SeedForTrait;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.TraitsCreatorContext;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.TraitsCreatorInterface;
import com.tylerfitzgerald.demo_api.etc.listFinders.WeightedTraitTypeWeightsFinder;
import com.tylerfitzgerald.demo_api.sql.dtos.TokenDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.repositories.TokenRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightedTraitRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.Getter;

public class AbstractWeightedTraitsCreator implements TraitsCreatorInterface {
  @Getter private List<WeightedTraitDTO> createdWeightedTraits = new ArrayList<>();
  protected TokenRepository tokenRepository;
  protected WeightedTraitRepository weightedTraitRepository;
  protected WeightedTraitTypeWeightsFinder weightedTraitTypeWeightsFinder;
  protected TokenDTO tokenDTO;
  protected Long seedForTraits;
  protected List<WeightedTraitTypeWeightDTO> weightedTraitTypeWeights = new ArrayList<>();
  protected TraitsCreatorContext context;

  @Override
  public void createTraits(TraitsCreatorContext context) {
    this.context = context;
    for (WeightedTraitTypeDTO type : context.getWeightedTraitTypes()) {
      WeightedTraitDTO trait = createWeightedTrait(type);
      if (trait != null) {
        createdWeightedTraits.add(trait);
      }
    }
    return;
  }

  protected WeightedTraitDTO createWeightedTrait(WeightedTraitTypeDTO type) {
    Long traitTypeId = type.getTraitTypeId();
    List<WeightedTraitTypeWeightDTO> weights = getTraitTypeWeightsForTraitTypeId(traitTypeId);
    WeightedTraitTypeWeightDTO traitTypeWeight =
        getRandomTraitTypeWeightFromList(weights, getSeedForTrait(seedForTraits, type));
    Long traitId = weightedTraitRepository.getCount() + 1L;
    return weightedTraitRepository.create(
        WeightedTraitDTO.builder()
            .id(null)
            .traitId(traitId)
            .tokenId(tokenDTO.getTokenId())
            .traitTypeId(traitTypeId)
            .traitTypeWeightId(traitTypeWeight.getTraitTypeWeightId())
            .build());
  }

  protected List<WeightedTraitTypeWeightDTO> getTraitTypeWeightsForTraitTypeId(Long traitTypeId) {
    return weightedTraitTypeWeightsFinder.findByTraitTypeId(weightedTraitTypeWeights, traitTypeId);
  }

  protected Long getSeedForTrait(Long seedForTraits, WeightedTraitTypeDTO weightedTraitTypeDTO) {
    return (long)
        SeedForTrait.builder()
            .seedForTraits(seedForTraits)
            .weightedTraitTypeDTO(weightedTraitTypeDTO)
            .build()
            .hashCode();
  }

  protected WeightedTraitTypeWeightDTO getRandomTraitTypeWeightFromList(
      List<WeightedTraitTypeWeightDTO> traitTypeWeights, Long randomNumberSeed) {
    int randomNumber = new Random(randomNumberSeed).nextInt(1, 100);
    Long count = 0L;
    for (WeightedTraitTypeWeightDTO traitTypeWeight : traitTypeWeights) {
      count = count + traitTypeWeight.getLikelihood();
      if (count >= randomNumber) {
        return traitTypeWeight;
      }
    }
    /**
     * We should never return null. If we get here: DB values for trait type weights (for a
     * particular trait type) are misconfigured.
     */
    return null;
  }
}
