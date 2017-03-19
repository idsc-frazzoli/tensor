// code by jph
package ch.ethz.idsc.tensor.sca;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.Function;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Floor.html">Floor</a> */
public enum Floor implements Function<Scalar, Scalar> {
  function;
  // ---
  /* package */ static BigInteger toBigInteger(double value) {
    BigDecimal bd = BigDecimal.valueOf(value);
    BigInteger bi = bd.toBigInteger();
    if (0 < new BigDecimal(bi).compareTo(bd)) {
      bd = BigDecimal.valueOf(value - 1);
      bi = bd.toBigInteger();
    }
    return bi;
  }

  /** @param scalar instance if {@link RealScalar}
   * @return best integer scalar approximation to floor of scalar */
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof RealScalar)
      return RationalScalar.of(toBigInteger(scalar.number().doubleValue()), BigInteger.ONE);
    // TODO still need to provide for other scalars
    throw new IllegalArgumentException();
  }

  /** @param scalar instance if {@link RealScalar}
   * @return best integer scalar approximation to floor of scalar */
  public static Tensor of(Tensor tensor) {
    return tensor.map(Floor.function);
  }
}
