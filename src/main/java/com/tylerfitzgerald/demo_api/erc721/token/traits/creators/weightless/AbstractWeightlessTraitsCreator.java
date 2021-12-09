package com.tylerfitzgerald.demo_api.erc721.token.traits.creators.weightless;

import com.tylerfitzgerald.demo_api.erc721.token.initializers.TokenInitializeException;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.TraitsCreatorContext;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.TraitsCreatorInterface;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.WeightlessTraitException;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.traitPickers.ColorTraitPicker;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.traitPickers.EmojiTraitPicker;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.traitPickers.MergeRarityTraitPicker;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.traitPickers.OverallRarityTraitPicker;
import com.tylerfitzgerald.demo_api.etc.listFinders.WeightlessTraitsListFinder;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightlessTraitDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightlessTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightlessTraitRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractWeightlessTraitsCreator implements TraitsCreatorInterface {

  @Autowired private WeightlessTraitRepository weightlessTraitRepository;
  @Autowired protected WeightlessTraitsListFinder weightlessTraitInListFinder;
  @Autowired protected MergeRarityTraitPicker mergeRarityTraitPicker;
  @Autowired protected EmojiTraitPicker emojiTraitPicker;
  @Autowired protected ColorTraitPicker colorTraitPicker;
  @Autowired protected OverallRarityTraitPicker overallRarityTraitPicker;
  @Getter private List<WeightlessTraitDTO> createdWeightlessTraits = new ArrayList<>();
  protected TraitsCreatorContext context;

  protected abstract String getWeightlessTraitValue(WeightlessTraitTypeDTO weightlessTraitType)
      throws WeightlessTraitException, TokenInitializeException;

  @Override
  public void createTraits(TraitsCreatorContext context) throws TokenInitializeException {
    this.context = context;
    WeightlessTraitDTO weightlessTraitDTO;
    for (WeightlessTraitTypeDTO weightlessTraitType : context.getWeightlessTraitTypes()) {
      try {
        weightlessTraitDTO = createWeightlessTrait(context.getTokenId(), weightlessTraitType);
      } catch (WeightlessTraitException e) {
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
      throws TokenInitializeException, WeightlessTraitException {
    Long weightTraitId = weightlessTraitRepository.getCount() + 1L;
    String traitValue = getWeightlessTraitValue(weightlessTraitType);
    if (traitValue == null || traitValue.equals("")) {
      return null;
    }
    return weightlessTraitRepository.create(
        WeightlessTraitDTO.builder()
            .id(null)
            .traitId(weightTraitId)
            .tokenId(tokenId)
            .traitTypeId(weightlessTraitType.getWeightlessTraitTypeId())
            .value(traitValue)
            .displayTypeValue(getWeightlessTraitDisplayTypeValue())
            .build());
  }

  public List<WeightlessTraitTypeDTO> getWeightlessTraitTypes() {
    return context.getWeightlessTraitTypes();
  }

  private String getWeightlessTraitDisplayTypeValue() {
    return "";
  }
}
