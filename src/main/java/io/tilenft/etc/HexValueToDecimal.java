package io.tilenft.etc;

public class HexValueToDecimal {

  public static final String ZERO_X = "0x";
  public static final int BASE_16 = 16;

  /**
   * By deafult take the 10 least significant bits and convert them to a decimal value.
   *
   * @param hexString
   * @return
   */
  public Long getLongFromHexString(String hexString) {
    int length = hexString.length();
    // Remove 2 chars from the substring call, since 0x was split.
    return Long.parseLong(hexString.split(ZERO_X)[1].substring(length - 11, length - 2), BASE_16);
  }
}
