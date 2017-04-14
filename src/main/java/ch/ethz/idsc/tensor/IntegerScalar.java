// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigInteger;

/* package */ class IntegerScalar {
  public static RealScalar of(BigInteger num) {
    return RationalScalar.of(num, BigInteger.ONE);
  }

  public static RealScalar of(long num) {
    return RationalScalar.of(num, 1);
  }

  private IntegerScalar() {
  }
}
