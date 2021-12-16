package io.tileNft.etc;

import java.math.BigInteger;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class BigIntegerFactoryTest {

  @Test
  void testFactory() {
    String value = "1";
    Assertions.assertThat(new BigIntegerFactory().build(value)).isEqualTo(new BigInteger(value));
  }

  @Test
  void testFactoryHex() {
    Assertions.assertThat(new BigIntegerFactory().buildHex("A120B3", 16).intValue())
        .isEqualTo(new BigInteger("10559667").intValue());
  }

  @Test
  void testFactoryBinary() {
    Assertions.assertThat(new BigIntegerFactory().buildHex("1010111010101110", 2).intValue())
        .isEqualTo(new BigInteger("44718").intValue());
  }
}
