package com.tylerfitzgerald.demo_api.erc721.token.finders;

import java.util.List;

public interface ListFinderInterface<T> {
  T findInList(List<T> listHaystack, Long needleValue, String needleGetterMethodName);
}
