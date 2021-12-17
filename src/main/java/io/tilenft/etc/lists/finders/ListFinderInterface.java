package io.tilenft.etc.lists.finders;

import java.util.List;

public interface ListFinderInterface<T> {

  /**
   * Gets a sub-list from the inputted list. Will search the inputted list and return any entries
   * that return valueToLookFor after calling valueGetterMethodName getter on that entry.
   *
   * @param listToSearch List that is searched
   * @param valueToLookFor Value that is searched for
   * @param valueGetterMethodName Method name to call on each list entry
   * @return T One of the list entries
   */
  List<T> get(List<T> listToSearch, Long valueToLookFor, String valueGetterMethodName);

  /**
   * Gets a list entry from the inputted list. Will search the list for the first entry that returns
   * valueToLookFor after calling valueGetterMethodName getter on that entry.
   *
   * @param listToSearch List that is searched
   * @param valueToLookFor Value that is searched for
   * @param valueGetterMethodName Method name to call on each list entry
   * @return List of found T entries
   */
  T getFirst(List<T> listToSearch, Long valueToLookFor, String valueGetterMethodName);
}
