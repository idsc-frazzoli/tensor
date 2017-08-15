// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** logarithm
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Log.html">Log</a> */
public enum Log implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  private static final Scalar PI = DoubleScalar.of(Math.PI);
  static final double LO = 0.75;
  static final double HI = 1.3;

  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof RealScalar) {
      if (Scalars.lessEquals(RealScalar.ZERO, scalar)) {
        double value = scalar.number().doubleValue();
        if (LO < value && value < HI)
          return DoubleScalar.of(Math.log1p(scalar.subtract(RealScalar.ONE).number().doubleValue()));
        return DoubleScalar.of(Math.log(value));
      }
      return ComplexScalar.of(apply(scalar.negate()), PI);
    }
    if (scalar instanceof ComplexScalar) {
      ComplexScalar z = (ComplexScalar) scalar;
      return ComplexScalar.of(Log.FUNCTION.apply(z.abs()), Arg.FUNCTION.apply(z));
    }
    throw TensorRuntimeException.of(scalar);
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their logarithm */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }

  /** for natural logarithm use {@link Log},
   * for base 10 use {@link Log10}.
   * 
   * @param base
   * @return logarithm function with given base */
  public static ScalarUnaryOperator base(Scalar base) {
    Scalar log_b = Log.of(base);
    return scalar -> Log.of(scalar).divide(log_b);
  }
}
