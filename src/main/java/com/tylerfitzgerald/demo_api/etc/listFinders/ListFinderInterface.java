package com.tylerfitzgerald.demo_api.etc.listFinders;

import java.util.List;

public interface ListFinderInterface<T> {
  List<T> get(List<T> listHaystack, Long needleValue, String needleGetterMethodName);

  T getFirst(List<T> listHaystack, Long needleValue, String needleGetterMethodName);
}
