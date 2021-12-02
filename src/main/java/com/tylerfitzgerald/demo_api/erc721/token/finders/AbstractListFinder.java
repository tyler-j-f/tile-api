package com.tylerfitzgerald.demo_api.erc721.token.finders;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public abstract class AbstractListFinder<T> implements ListFinderInterface<T> {

  public T findInList(List<T> listHaystack, Long needleValue, String needleValueGetterMethodName) {
    return listHaystack.stream()
        .filter(
            trait -> {
              try {
                Method method = trait.getClass().getMethod(needleValueGetterMethodName);
                return method.invoke(trait).equals(needleValue);
              } catch (NoSuchMethodException
                  | IllegalAccessException
                  | InvocationTargetException e) {
                return false;
              }
            })
        .findFirst()
        .get();
  }
}
