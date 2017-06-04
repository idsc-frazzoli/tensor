// code by jph
package ch.ethz.idsc.tensor.sca;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Function;

import ch.ethz.idsc.tensor.DecimalScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** consistent with Mathematica:
 * <pre>
 * Round[+11.5] == +12
 * Round[-11.5] == -12
 * </pre>
 * 
 * not consistent with java.lang.Math::round which rounds -11.5 to -11.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Round.html">Round</a> */
public enum Round implements Function<Scalar, Scalar> {
  function;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof RationalScalar) {
      RationalScalar rationalScalar = (RationalScalar) scalar;
      return RealScalar.of(rationalScalar.toBigDecimal(0, RoundingMode.HALF_UP).toBigIntegerExact());
    }
    if (scalar instanceof RealScalar) {
      BigDecimal bigDecimal = BigDecimal.valueOf(scalar.number().doubleValue());
      return RealScalar.of(bigDecimal.setScale(0, RoundingMode.HALF_UP).toBigIntegerExact());
    }
    throw TensorRuntimeException.of(scalar);
  }

  /** for best results, the parameter increment should be a instance of
   * {@link DecimalScalar}, or {@link RationalScalar}
   * Examples:
   * DecimalScalar.of(0.1), or RationalScalar.of(1, 10)
   * 
   * <p>if instead increment is a {@link DoubleScalar} the return value
   * may suffer from numeric round off error in the style of "3.4000000000000004"
   * 
   * @param increment
   * @return */
  public static Function<Scalar, Scalar> toMultipleOf(Scalar increment) {
    Scalar inverse = increment.invert();
    return scalar -> function.apply(scalar.multiply(inverse)).multiply(increment);
  }

  /** rounds all entries of tensor to nearest integers, with
   * ties rounding to positive infinity.
   * 
   * @param tensor
   * @return Rationalize.of(tensor, 1) */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(Round.function);
  }
}
