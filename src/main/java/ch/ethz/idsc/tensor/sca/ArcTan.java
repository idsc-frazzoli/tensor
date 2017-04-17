// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.function.Function;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** http://www.milefoot.com/math/complex/functionsofi.htm
 * 
 * inspired by
 * <a href="https://reference.wolfram.com/language/ref/ArcTan.html">ArcTan</a> */
public enum ArcTan implements Function<Scalar, Scalar> {
  function;
  // ---
  private static final Scalar I_HALF = ComplexScalar.I.divide(RealScalar.of(2));

  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof RealScalar)
      return DoubleScalar.of(Math.atan(scalar.number().doubleValue()));
    return I_HALF.multiply(Log.function.apply(ComplexScalar.I.add(scalar).divide(ComplexScalar.I.subtract(scalar))));
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their arc tan */
  public static Tensor of(Tensor tensor) {
    return tensor.map(ArcTan.function);
  }
}
