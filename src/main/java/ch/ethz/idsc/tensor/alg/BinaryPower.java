// code by jph
// implementation adapted from Ruby code of https://en.wikipedia.org/wiki/Exponentiation_by_squaring
package ch.ethz.idsc.tensor.alg;

import java.io.Serializable;
import java.math.BigInteger;

import ch.ethz.idsc.tensor.GaussScalar;
import ch.ethz.idsc.tensor.mat.MatrixPower;

/** interface used by {@link MatrixPower} and {@link GaussScalar} */
public abstract class BinaryPower<T> implements Serializable {
  public abstract T zeroth();

  public abstract T invert(T object);

  public abstract T multiply(T fac1, T object);

  public final T apply(T x, long exponent) {
    return apply(x, BigInteger.valueOf(exponent));
  }

  public final T apply(T x, BigInteger exponent) {
    T result = zeroth();
    if (exponent.signum() == 0)
      return result;
    if (exponent.signum() == -1) { // convert problem to positive exponent
      exponent = exponent.negate();
      x = invert(x);
    }
    // the below implementation was adapted from
    // https://en.wikipedia.org/wiki/Exponentiation_by_squaring
    // Section: Computation by powers of 2
    // non-recursive implementation of the algorithm in Ruby
    while (true) { // iteration
      if (exponent.testBit(0))
        result = multiply(x, result);
      exponent = exponent.shiftRight(1);
      if (exponent.signum() == 0)
        return result;
      x = multiply(x, x);
    }
  }
}
