package com.tylerfitzgerald.demo_api.etc;

import java.math.BigInteger;

public class BigIntegerFactory {

  public BigInteger build(String val) {
    return new BigInteger(val);
  }

  public BigInteger buildHex(String val, int radix) {
    return new BigInteger(val, radix);
  }
}
