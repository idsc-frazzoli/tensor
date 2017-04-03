// code by jph
package ch.ethz.idsc.tensor.sca;

import java.math.BigDecimal;
import java.math.BigInteger;
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
   * @return best integer scalar approximation to ceiling of scalar */
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof RealScalar)
      // TODO this conversion formula is probably not the best we can do numerically!
      return RationalScalar.of(toBigInteger(scalar.number().doubleValue()), BigInteger.ONE);
    throw TensorRuntimeException.of(scalar);
  }

  /* package */ static BigInteger toBigInteger(double value) {
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
  public static Tensor of(Tensor tensor) {
    return tensor.map(Ceiling.function);
  }
}
