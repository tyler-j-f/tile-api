package com.tylerfitzgerald.demo_api.erc721.token;

import com.tylerfitzgerald.demo_api.config.TokenConfig;
import com.tylerfitzgerald.demo_api.config.TraitsConfig;
import com.tylerfitzgerald.demo_api.erc721.traits.WeightedTraitTypeConstants;
import com.tylerfitzgerald.demo_api.erc721.traits.WeightlessTraitTypeConstants;
import com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.traitPickers.ColorTraitPicker;
import com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.traitPickers.EmojiTraitPicker;
import com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.traitPickers.RarityTraitPicker;
import com.tylerfitzgerald.demo_api.sql.tblToken.TokenDTO;
import com.tylerfitzgerald.demo_api.sql.tblToken.TokenRepository;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.TraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.TraitTypeWeightRepository;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypes.TraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypes.TraitTypeRepository;
import com.tylerfitzgerald.demo_api.sql.tblTraits.TraitDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraits.TraitRepository;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraitTypes.WeightlessTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraitTypes.WeightlessTraitTypeRepository;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits.WeightlessTraitDTO;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits.WeightlessTraitRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

public class MergeTokenInitializer {

  @Autowired private TokenRepository tokenRepository;
  @Autowired private TraitRepository traitRepository;
  @Autowired private TraitTypeRepository traitTypeRepository;
  @Autowired private TraitTypeWeightRepository traitTypeWeightRepository;
  @Autowired private WeightlessTraitRepository weightlessTraitRepository;
  @Autowired private WeightlessTraitTypeRepository weightlessTraitTypeRepository;
  @Autowired private TokenConfig tokenConfig;
  @Autowired private TraitsConfig traitsConfig;
  @Autowired private EmojiTraitPicker emojiTraitPicker;
  @Autowired private ColorTraitPicker colorTraitPicker;
  @Autowired private RarityTraitPicker rarityTraitPicker;

  private static final int[] WEIGHTED_TRAIT_TYPES_TO_IGNORE = {
      WeightedTraitTypeConstants.TILE_1_RARITY,
      WeightedTraitTypeConstants.TILE_2_RARITY,
      WeightedTraitTypeConstants.TILE_3_RARITY,
      WeightedTraitTypeConstants.TILE_4_RARITY,
      WeightedTraitTypeConstants.TILE_1_MULTIPLIER,
      WeightedTraitTypeConstants.TILE_2_MULTIPLIER,
      WeightedTraitTypeConstants.TILE_3_MULTIPLIER,
      WeightedTraitTypeConstants.TILE_4_MULTIPLIER
  };

  private List<TraitTypeDTO> availableTraitTypes = new ArrayList<>();
  private List<TraitTypeWeightDTO> availableTraitTypeWeights = new ArrayList<>();
  private List<TraitDTO> weightedTraits = new ArrayList<>();
  private List<WeightlessTraitDTO> weightlessTraits = new ArrayList<>();
  private List<WeightlessTraitTypeDTO> weightlessTraitTypes = new ArrayList<>();
  private TokenDTO tokenDTO, burnedNft1, burnedNft2;
  private Long seedForTraits;

  public TokenFacadeDTO initialize(
      Long tokenId, Long burnedNft1Id, Long burnedNft2Id, Long seedForTraits)
      throws TokenInitializeException {
    this.seedForTraits = seedForTraits;
    burnedNft1 = getToken(burnedNft1Id);
    burnedNft2 = getToken(burnedNft2Id);
    if (burnedNft1 == null) {
      System.out.println(
          "TokenInitializer failed to load burned token 1. burnedNft1: " + burnedNft1);
      return null;
    }
    if (burnedNft2 == null) {
      System.out.println(
          "TokenInitializer failed to load burned token 2. burnedNft2: " + burnedNft2);
      return null;
    }
    tokenDTO = createToken(tokenId);
    if (tokenDTO == null) {
      System.out.println(
          "TokenInitializer failed to initialize the token with tokenId: " + tokenId);
      return null;
    }
    availableTraitTypes = filterOutWeightedTraitTypesToIgnore(traitTypeRepository.read());
    availableTraitTypeWeights = traitTypeWeightRepository.read();
    weightlessTraitTypes = weightlessTraitTypeRepository.read();
    weightedTraits = createWeightedTraits(seedForTraits);
    weightlessTraits = createWeightlessTraits();
    return buildNFTFacade();
  }

  private TokenFacadeDTO buildNFTFacade() {
    return TokenFacadeDTO.builder()
        .tokenDTO(tokenDTO)
        .tokenTraits(weightedTraits)
        .availableTraitTypes(availableTraitTypes)
        .availableTraitTypeWeights(availableTraitTypeWeights)
        .weightlessTraits(weightlessTraits)
        .weightlessTraitTypes(weightlessTraitTypes)
        .build();
  }

  private TokenDTO getToken(Long tokenId) {
    return tokenRepository.readById(tokenId);
  }

  private TokenDTO createToken(Long tokenId) {
    return tokenRepository.create(
        TokenDTO.builder()
            .tokenId(tokenId)
            .saleId(1L)
            .name(tokenConfig.getBase_name() + " " + tokenId.toString())
            .description(tokenConfig.getDescription())
            .externalUrl(tokenConfig.getBase_external_url() + tokenId)
            .imageUrl(tokenConfig.getBase_image_url() + tokenId)
            .build());
  }

  private List<WeightlessTraitDTO> createWeightlessTraits() throws TokenInitializeException {
    List<WeightlessTraitDTO> weightlessTraits = new ArrayList<>();
    for (WeightlessTraitTypeDTO weightlessTraitType : weightlessTraitTypes) {
      // Increment the seed so that we use a unique random value for each trait
      WeightlessTraitDTO weightlessTraitDTO = createWeightlessTrait(weightlessTraitType);
      if (weightlessTraitDTO != null) {
        weightlessTraits.add(weightlessTraitDTO);
      }
    }
    return weightlessTraits;
  }

  private WeightlessTraitDTO createWeightlessTrait(WeightlessTraitTypeDTO weightlessTraitType)
      throws TokenInitializeException {
    Long weightTraitId = weightlessTraitRepository.read().size() + 1L;
    return weightlessTraitRepository.create(
        WeightlessTraitDTO.builder()
            .id(null)
            .traitId(weightTraitId)
            .tokenId(tokenDTO.getTokenId())
            .traitTypeId(weightlessTraitType.getWeightlessTraitTypeId())
            .value(getWeightlessTraitValue(weightlessTraitType))
            .displayTypeValue(getWeightlessTraitDisplayTypeValue(weightlessTraitType))
            .build()
    );
  }

  private String getWeightlessTraitValue(WeightlessTraitTypeDTO weightlessTraitType) {
    Long traitTypeId = weightlessTraitType.getWeightlessTraitTypeId();
    if (traitTypeId == WeightlessTraitTypeConstants.TILE_1_RARITY) {

      return "1";
    } else if (traitTypeId == WeightlessTraitTypeConstants.TILE_2_RARITY) {
      return "2";
    } else if (traitTypeId == WeightlessTraitTypeConstants.TILE_3_RARITY) {
      return "3";
    } else if (traitTypeId == WeightlessTraitTypeConstants.TILE_4_RARITY) {
      return "3";
    } else if (traitTypeId == WeightlessTraitTypeConstants.TILE_1_MULTIPLIER) {
      return "3";
    } else if (traitTypeId == WeightlessTraitTypeConstants.TILE_2_MULTIPLIER) {
      return "3";
    } else if (traitTypeId == WeightlessTraitTypeConstants.TILE_3_MULTIPLIER) {
      return "3";
    } else if (traitTypeId == WeightlessTraitTypeConstants.TILE_4_MULTIPLIER) {
      return "3";
    } else if (traitTypeId == WeightlessTraitTypeConstants.OVERALL_RARITY) {
      return "3";
    }  else if (traitTypeId == WeightlessTraitTypeConstants.TILE_1_EMOJI) {
      return "3";
    } else if (traitTypeId == WeightlessTraitTypeConstants.TILE_2_EMOJI) {
      return "3";
    } else if (traitTypeId == WeightlessTraitTypeConstants.TILE_3_EMOJI) {
      return "3";
    } else if (traitTypeId == WeightlessTraitTypeConstants.TILE_4_EMOJI) {
      return "3";
    } else if (traitTypeId == WeightlessTraitTypeConstants.TILE_1_COLOR) {
      return "3";
    } else if (traitTypeId == WeightlessTraitTypeConstants.TILE_2_COLOR) {
      return "3";
    } else if (traitTypeId == WeightlessTraitTypeConstants.TILE_3_COLOR) {
      return "3";
    } else if (traitTypeId == WeightlessTraitTypeConstants.TILE_4_COLOR) {
      return "3";
    } else {
      return "invalid mergeWeightlessTraitValue";
    }
  }

  private String getWeightlessTraitDisplayTypeValue(WeightlessTraitTypeDTO weightlessTraitType) {
    return null;
  }

  private List<TraitDTO> createWeightedTraits(Long seedForTraits) {
    List<TraitDTO> traits = new ArrayList<>();
    for (TraitTypeDTO type : availableTraitTypes) {
      // Increment the seed so that we use a unique random value for each trait
      TraitDTO trait = createWeightedTrait(type, seedForTraits++);
      if (trait != null) {
        traits.add(trait);
      }
    }
    return traits;
  }

  private TraitDTO createWeightedTrait(TraitTypeDTO type, Long seedForTrait) {
    Long traitTypeId = type.getTraitTypeId();
    List<TraitTypeWeightDTO> weights = getTraitTypeWeightsForTraitTypeId(traitTypeId);
    TraitTypeWeightDTO traitTypeWeight = getRandomTraitTypeWeightFromList(weights, seedForTrait);
    Long traitId = traitRepository.read().size() + 1L;
    return traitRepository.create(
        TraitDTO.builder()
            .id(null)
            .traitId(traitId)
            .tokenId(tokenDTO.getTokenId())
            .traitTypeId(traitTypeId)
            .traitTypeWeightId(traitTypeWeight.getTraitTypeWeightId())
            .build());
  }

  private List<TraitTypeWeightDTO> getTraitTypeWeightsForTraitTypeId(Long traitTypeId) {
    return availableTraitTypeWeights.stream()
        .filter(typeWeight -> typeWeight.getTraitTypeId().equals(traitTypeId))
        .collect(Collectors.toList());
  }

  private TraitTypeWeightDTO getRandomTraitTypeWeightFromList(List<TraitTypeWeightDTO> traitTypeWeights, Long randomNumberSeed) {
    int randomNumber = new Random(randomNumberSeed).nextInt(1, 100);
    Long count = 0L;
    for (TraitTypeWeightDTO traitTypeWeight : traitTypeWeights) {
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

  private List<TraitTypeDTO> filterOutWeightedTraitTypesToIgnore(
      List<TraitTypeDTO> traitTypes) {
    return traitTypes.stream()
        .filter(
            traitType -> {
              for (int typeId : WEIGHTED_TRAIT_TYPES_TO_IGNORE) {
                if (traitType.getTraitTypeId().equals(Long.valueOf(typeId))) {
                  return false;
                }
              }
              return true;
            })
        .collect(Collectors.toList());
  }

  private List<TraitTypeDTO> filterOutWeightLessTraitTypesToIgnore(
      List<TraitTypeDTO> traitTypes) {
    return traitTypes.stream()
        .filter(
            traitType -> {
              for (int typeId : WEIGHTED_TRAIT_TYPES_TO_IGNORE) {
                if (traitType.getTraitTypeId().equals(Long.valueOf(typeId))) {
                  return false;
                }
              }
              return true;
            })
        .collect(Collectors.toList());
  }
}
