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
 * <a href="https://reference.wolfram.com/language/ref/ArcCos.html">ArcCos</a> */
public enum ArcCos implements Function<Scalar, Scalar> {
  function;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof RealScalar) {
      double value = scalar.number().doubleValue();
      if (-1 <= value && value <= 1)
        return DoubleScalar.of(Math.acos(value));
    }
    Scalar o_x2 = Sqrt.function.apply(RealScalar.ONE.subtract(scalar.multiply(scalar)));
    return ComplexScalar.I.negate().multiply(Log.function.apply(scalar.add(ComplexScalar.I.multiply(o_x2))));
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their arc cos */
  public static Tensor of(Tensor tensor) {
    return tensor.map(ArcCos.function);
  }
}
