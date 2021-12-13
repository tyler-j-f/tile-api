package com.tylerfitzgerald.demo_api.etc.lsitFinders;

import java.util.List;

public interface ListFinderInterface<T> {
  List<T> get(List<T> listHaystack, Long needleValue, String needleGetterMethodName);

  T getFirst(List<T> listHaystack, Long needleValue, String needleGetterMethodName);
}