package com.tylerfitzgerald.demo_api.erc721.token.finders;

import com.tylerfitzgerald.demo_api.sql.BaseTraitDTO;
import java.util.List;

public abstract class AbstractTraitInListFinder<T extends BaseTraitDTO>
    implements TraitInListFinderInterface {

  protected T findInList(List<T> traitsList, Long traitTypeId) {
    return (T)
        traitsList.stream()
            .filter(traits -> traits.getTraitTypeId().equals(traitTypeId))
            .findFirst()
            .get();
  }
}
