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
 * <a href="https://reference.wolfram.com/language/ref/Ceiling.html">Ceiling</a> */
public enum Ceiling implements Function<Scalar, Scalar> {
  function;
  // ---
  /** @param scalar instance if {@link RealScalar}
   * @return best integer scalar approximation to ceiling of scalar
   * @throws TensorRuntimeException if scalar is Infinity, or NaN */
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof RationalScalar) {
      RationalScalar rationalScalar = (RationalScalar) scalar;
      return RealScalar.of(rationalScalar.toBigDecimal(0, RoundingMode.CEILING).toBigIntegerExact());
    }
    if (scalar instanceof RealScalar)
      return RationalScalar.of(_ceiling(scalar.number().doubleValue()), BigInteger.ONE);
    throw TensorRuntimeException.of(scalar);
  }

  // helper function
  private static BigInteger _ceiling(double value) {
    // throws an exception if value is Infinity
    BigDecimal bd = BigDecimal.valueOf(value);
    BigInteger bi = bd.toBigInteger();
    if (new BigDecimal(bi).compareTo(bd) < 0) {
      bd = BigDecimal.valueOf(value + 1);
      bi = bd.toBigInteger();
    }
    return bi;
  }

  /** @param tensor
   * @return */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(Ceiling.function);
  }
}
