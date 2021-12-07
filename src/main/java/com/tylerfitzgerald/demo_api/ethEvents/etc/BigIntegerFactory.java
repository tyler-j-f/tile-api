package com.tylerfitzgerald.demo_api.ethEvents.etc;

import java.math.BigInteger;

public class BigIntegerFactory {

  public BigInteger build(String val) {
    return new BigInteger(val);
  }

  public BigInteger build(String val, int radix) {
    return new BigInteger(val, radix);
  }
}
