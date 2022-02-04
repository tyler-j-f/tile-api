package io.tilenft.eth.token.traits.creators.weightless;

import io.tilenft.eth.token.initializers.TokenInitializeException;
import io.tilenft.eth.token.traits.creators.TraitsCreatorContext;
import io.tilenft.eth.token.traits.creators.TraitsCreatorInterface;
import io.tilenft.eth.token.traits.weightless.pickers.OverallRarityTraitPicker;
import io.tilenft.eth.token.traits.weightless.pickers.WeightlessTraitPickerException;
import io.tilenft.sql.dtos.WeightlessTraitDTO;
import io.tilenft.sql.dtos.WeightlessTraitTypeDTO;
import io.tilenft.sql.repositories.WeightlessTraitRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractWeightlessTraitsCreator implements TraitsCreatorInterface {

  @Autowired private WeightlessTraitRepository weightlessTraitRepository;
  @Autowired protected OverallRarityTraitPicker overallRarityTraitPicker;
  @Getter private List<WeightlessTraitDTO> createdWeightlessTraits = new ArrayList<>();
  protected TraitsCreatorContext context;

  protected abstract String getWeightlessTraitValue(WeightlessTraitTypeDTO weightlessTraitType)
      throws WeightlessTraitPickerException, TokenInitializeException;

  @Override
  public void createTraits(TraitsCreatorContext context) throws TokenInitializeException {
    this.context = context;
    createdWeightlessTraits = new ArrayList<>();
    WeightlessTraitDTO weightlessTraitDTO;
    for (WeightlessTraitTypeDTO weightlessTraitType : context.getWeightlessTraitTypes()) {
      try {
        weightlessTraitDTO = createWeightlessTrait(context.getTokenId(), weightlessTraitType);
      } catch (WeightlessTraitPickerException e) {
        throw new TokenInitializeException(e.getMessage(), e.getCause());
      }
      if (weightlessTraitDTO != null) {
        createdWeightlessTraits.add(weightlessTraitDTO);
      }
    }
    return;
  }

  private WeightlessTraitDTO createWeightlessTrait(
      Long tokenId, WeightlessTraitTypeDTO weightlessTraitType)
      throws TokenInitializeException, WeightlessTraitPickerException {
    Long weightTraitId = weightlessTraitRepository.getCount() + 1L;
    String traitValue = getWeightlessTraitValue(weightlessTraitType);
    if (traitValue == null || traitValue.equals("")) {
      return null;
    }
    WeightlessTraitDTO trait =
        WeightlessTraitDTO.builder()
            .id(null)
            .traitId(weightTraitId)
            .tokenId(tokenId)
            .traitTypeId(weightlessTraitType.getWeightlessTraitTypeId())
            .value(traitValue)
            .displayTypeValue(getWeightlessTraitDisplayTypeValue())
            .build();
    if (context.isDryRun()) {
      trait.setId(weightTraitId);
    }
    return context.isDryRun() ? trait : weightlessTraitRepository.create(trait);
  }

  public List<WeightlessTraitTypeDTO> getWeightlessTraitTypes() {
    return context.getWeightlessTraitTypes();
  }

  private String getWeightlessTraitDisplayTypeValue() {
    return "";
  }
}
