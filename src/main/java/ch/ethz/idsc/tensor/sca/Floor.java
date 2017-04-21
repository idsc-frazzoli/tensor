// code by jph
package ch.ethz.idsc.tensor.sca;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.function.Function;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Floor.html">Floor</a> */
public enum Floor implements Function<Scalar, Scalar> {
  function;
  // ---
  /** @param scalar instance if {@link RealScalar}
   * @return best integer scalar approximation to floor of scalar */
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof RationalScalar) {
      RationalScalar rationalScalar = (RationalScalar) scalar;
      return RealScalar.of(rationalScalar.toBigDecimal(0, RoundingMode.FLOOR).toBigIntegerExact());
    }
    if (scalar instanceof RealScalar)
      return RationalScalar.of(_floor(scalar.number().doubleValue()), BigInteger.ONE);
    throw TensorRuntimeException.of(scalar);
  }

  // helper function
  private static BigInteger _floor(double value) {
    BigDecimal bd = BigDecimal.valueOf(value);
    BigInteger bi = bd.toBigInteger();
    if (0 < new BigDecimal(bi).compareTo(bd)) {
      bd = BigDecimal.valueOf(value - 1);
      bi = bd.toBigInteger();
    }
    return bi;
  }

  /** @param tensor
   * @return tensor with all entries replaced by their floor */
  public static Tensor of(Tensor tensor) {
    return tensor.map(Floor.function);
  }
}
