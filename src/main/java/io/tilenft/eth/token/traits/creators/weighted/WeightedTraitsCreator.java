package io.tilenft.eth.token.traits.creators.weighted;

import io.tilenft.etc.lists.finders.WeightedTraitTypeWeightsListFinder;
import io.tilenft.eth.token.initializers.SeedForTrait;
import io.tilenft.eth.token.traits.creators.TraitsCreatorContext;
import io.tilenft.eth.token.traits.creators.TraitsCreatorInterface;
import io.tilenft.sql.dtos.WeightedTraitDTO;
import io.tilenft.sql.dtos.WeightedTraitTypeDTO;
import io.tilenft.sql.dtos.WeightedTraitTypeWeightDTO;
import io.tilenft.sql.repositories.WeightedTraitRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

public class WeightedTraitsCreator implements TraitsCreatorInterface {
  @Getter private List<WeightedTraitDTO> createdWeightedTraits = new ArrayList<>();
  @Autowired protected WeightedTraitRepository weightedTraitRepository;
  @Autowired protected WeightedTraitTypeWeightsListFinder weightedTraitTypeWeightsListFinder;
  protected TraitsCreatorContext context;

  @Override
  public void createTraits(TraitsCreatorContext context) {
    this.context = context;
    createdWeightedTraits = new ArrayList<>();
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
        getRandomTraitTypeWeightFromList(
            weights, getSeedForTrait(context.getSeedForTraits(), type));
    Long traitId = weightedTraitRepository.getCount() + 1L;
    WeightedTraitDTO weightedTraitDTO =
        WeightedTraitDTO.builder()
            .id(null)
            .traitId(traitId)
            .tokenId(context.getTokenId())
            .traitTypeId(traitTypeId)
            .traitTypeWeightId(traitTypeWeight.getTraitTypeWeightId())
            .build();
    if (context.isDryRun()) {
      weightedTraitDTO.setId(traitId);
    }
    return context.isDryRun() ? weightedTraitDTO : weightedTraitRepository.create(weightedTraitDTO);
  }

  protected List<WeightedTraitTypeWeightDTO> getTraitTypeWeightsForTraitTypeId(Long traitTypeId) {
    return weightedTraitTypeWeightsListFinder.findByTraitTypeId(
        context.getWeightedTraitTypeWeights(), traitTypeId);
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
