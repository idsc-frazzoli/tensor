// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** For real input in the interval [-1, 1] the returned angle is in the range -pi/2 through pi/2.
 * 
 * http://www.milefoot.com/math/complex/functionsofi.htm
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ArcCos.html">ArcCos</a> */
public enum ArcSin implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  private static final Scalar I_NEGATE = ComplexScalar.I.negate();

  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof RealScalar) {
      double value = scalar.number().doubleValue();
      if (-1 <= value && value <= 1)
        return DoubleScalar.of(Math.asin(value));
    }
    Scalar o_x2 = Sqrt.FUNCTION.apply(RealScalar.ONE.subtract(scalar.multiply(scalar)));
    return I_NEGATE.multiply(Log.FUNCTION.apply(ComplexScalar.I.multiply(scalar).add(o_x2)));
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their arc sin */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }
}
