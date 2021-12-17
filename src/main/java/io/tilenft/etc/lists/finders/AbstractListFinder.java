package io.tilenft.etc.lists.finders;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public abstract class AbstractListFinder<T> implements ListFinderInterface<T> {

  /**
   * Gets a sub-list from the inputted list. Will search the inputted list and return any entries
   * that return valueToLookFor after calling valueGetterMethodName getter on that entry.
   *
   * @param listToSearch List that is searched
   * @param valueToLookFor Value that is searched for
   * @param valueGetterMethodName Method name to call on each list entry
   * @return T One of the list entries
   */
  public List<T> get(List<T> listToSearch, Long valueToLookFor, String valueGetterMethodName) {
    List<T> results;
    try {
      results = getResults(listToSearch, valueToLookFor, valueGetterMethodName);
    } catch (NoSuchElementException e) {
      results = null;
    }
    return results;
  }

  private List<T> getResults(
      List<T> listToSearch, Long valueToLookFor, String valueGetterMethodName) {
    return listToSearch.stream()
        .filter(
            trait -> {
              try {
                Method method = trait.getClass().getMethod(valueGetterMethodName);
                return method.invoke(trait).equals(valueToLookFor);
              } catch (NoSuchMethodException
                  | IllegalAccessException
                  | InvocationTargetException e) {
                return false;
              }
            })
        .collect(Collectors.toList());
  }

  /**
   * Gets a list entry from the inputted list. Will search the list for the first entry that returns
   * valueToLookFor after calling valueGetterMethodName getter on that entry.
   *
   * @param listToSearch List that is searched
   * @param valueToLookFor Value that is searched for
   * @param valueGetterMethodName Method name to call on each list entry
   * @return List of found T entries
   */
  public T getFirst(List<T> listToSearch, Long valueToLookFor, String valueGetterMethodName) {
    T results;
    try {
      results = getFirstResults(listToSearch, valueToLookFor, valueGetterMethodName);
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
