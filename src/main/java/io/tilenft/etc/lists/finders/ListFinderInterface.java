package io.tilenft.etc.lists.finders;

import java.util.List;

public interface ListFinderInterface<T> {

  /**
   * Gets a list entry. Will search the list for any entries that return valueToLookFor after
   * calling valueGetterMethodName on that entry.
   *
   * @param listToSearch List that is searched
   * @param valueToLookFor Value that is searched for
   * @param valueGetterMethodName Method name to call on each list entry
   * @return List<T> One of the list entries
   */
  List<T> get(List<T> listToSearch, Long valueToLookFor, String valueGetterMethodName);

  T getFirst(List<T> listHaystack, Long needleValue, String needleGetterMethodName);
}
