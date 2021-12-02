package com.tylerfitzgerald.demo_api.erc721.token.finders;

import com.tylerfitzgerald.demo_api.sql.BaseTraitDTO;
import java.util.List;

public interface ListFinderInterface<T extends BaseTraitDTO> {
  T findInList(List<T> listHaystack, Long needleValue, String needleGetterMethodName);
}
