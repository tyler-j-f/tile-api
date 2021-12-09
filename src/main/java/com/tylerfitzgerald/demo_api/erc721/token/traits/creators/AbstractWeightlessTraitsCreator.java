package com.tylerfitzgerald.demo_api.erc721.token.traits.creators;

import com.tylerfitzgerald.demo_api.erc721.token.initializers.TokenInitializeException;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.WeightlessTraitException;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.traitPickers.ColorTraitPicker;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.traitPickers.EmojiTraitPicker;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.traitPickers.MergeRarityTraitPicker;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.traitPickers.OverallRarityTraitPicker;
import com.tylerfitzgerald.demo_api.etc.listFinders.WeightlessTraitsFinder;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightlessTraitDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightlessTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightlessTraitRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractWeightlessTraitsCreator {

  @Autowired private WeightlessTraitRepository weightlessTraitRepository;
  @Autowired protected WeightlessTraitsFinder weightlessTraitInListFinder;
  @Autowired protected MergeRarityTraitPicker mergeRarityTraitPicker;
  @Autowired protected EmojiTraitPicker emojiTraitPicker;
  @Autowired protected ColorTraitPicker colorTraitPicker;
  @Autowired protected OverallRarityTraitPicker overallRarityTraitPicker;
  protected WeightlessTraitsCreatorContext context;
  protected Long seedForTraits;
  protected List<WeightlessTraitDTO> createdWeightlessTraits = new ArrayList<>();

  public void createTraits(WeightlessTraitsCreatorContext context) throws TokenInitializeException {
    this.context = context;
    this.seedForTraits = context.getSeedForTraits();
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

  public List<WeightlessTraitDTO> getCreatedTraits() {
    return createdWeightlessTraits;
  }

  public List<WeightlessTraitTypeDTO> getTraitTypes() {
    return context.getWeightlessTraitTypes();
  }

  protected abstract String getWeightlessTraitValue(WeightlessTraitTypeDTO weightlessTraitType)
      throws WeightlessTraitException, TokenInitializeException;

  private String getWeightlessTraitDisplayTypeValue() {
    return "";
  }
}