package io.tilenft.etc;

public class HexStringPrefixStripper {
  public static final String ZERO_X = "0x";

  public String strip0xFromHexString(String hexString) {
    return hexString.split(ZERO_X)[1];
  }
}
