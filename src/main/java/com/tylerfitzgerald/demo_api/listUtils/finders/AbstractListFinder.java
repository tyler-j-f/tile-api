package com.tylerfitzgerald.demo_api.listUtils.finders;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class AbstractListFinder<T> implements ListFinderInterface<T> {

  public T findInList(List<T> listHaystack, Long needleValue, String needleValueGetterMethodName) {
    T results;
    try {
      results =
          listHaystack.stream()
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
    } catch (NoSuchElementException e) {
      results = null;
    }
    return results;
  }
}
