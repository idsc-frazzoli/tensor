// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.function.Function;

import ch.ethz.idsc.tensor.ExactPrecision;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.ZeroScalar;

/** function to modify {@link Scalar}s that do not have {@link ExactPrecision}
 * 
 * <p>consistent with Mathematica
 * Chop[1/1000000000000000] != 0, but
 * Chop[1/1000000000000000.] == 0
 * 
 * inspired by
 * <a href="https://reference.wolfram.com/language/ref/Chop.html">Chop</a> */
public enum Chop implements Function<Scalar, Scalar> {
  function;
  // ---
  /** default threshold for numerical truncation to 0 */
  public static final double THRESHOLD = 1e-12;
  private static final Function<Scalar, Scalar> belowDefault = below(Chop.THRESHOLD);

  public static Function<Scalar, Scalar> below(double threshold) {
    return scalar -> {
      if (scalar instanceof ChopInterface)
        return ((ChopInterface) scalar).chop();
      if (scalar instanceof ExactPrecision) // TODO how to treat ComplexScalar?
        return scalar;
      if (scalar.abs().number().doubleValue() < threshold)
        return ZeroScalar.get();
      return scalar;
    };
  }

  @Override
  public Scalar apply(Scalar scalar) {
    return belowDefault.apply(scalar);
  }

  /** @return chop(scalar) == zero ? zero : scalar.invert() */
  public static Function<Scalar, Scalar> orInvert(double threshold) {
    return InvertUnlessZero.function.compose(below(threshold));
  }

  /** @param tensor
   * @return */
  public static Tensor of(Tensor tensor) {
    return tensor.map(belowDefault);
  }

  /** @param tensor
   * @param threshold
   * @return */
  public static Tensor of(Tensor tensor, double threshold) {
    return tensor.map(below(threshold));
  }
}
