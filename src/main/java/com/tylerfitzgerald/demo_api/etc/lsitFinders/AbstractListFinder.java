package com.tylerfitzgerald.demo_api.etc.lsitFinders;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public abstract class AbstractListFinder<T> implements ListFinderInterface<T> {

  public List<T> get(List<T> listHaystack, Long needleValue, String needleValueGetterMethodName) {
    List<T> results;
    try {
      results = getResults(listHaystack, needleValue, needleValueGetterMethodName);
    } catch (NoSuchElementException e) {
      results = null;
    }
    return results;
  }

  private List<T> getResults(
      List<T> listHaystack, Long needleValue, String needleValueGetterMethodName) {
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
        .collect(Collectors.toList());
  }

  public T getFirst(List<T> listHaystack, Long needleValue, String needleValueGetterMethodName) {
    T results;
    try {
      results = getFirstResults(listHaystack, needleValue, needleValueGetterMethodName);
    } catch (NoSuchElementException e) {
      results = null;
    }
    return results;
  }

  private T getFirstResults(
      List<T> listHaystack, Long needleValue, String needleValueGetterMethodName) {
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
