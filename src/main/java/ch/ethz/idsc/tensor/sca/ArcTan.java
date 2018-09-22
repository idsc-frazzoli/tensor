// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** For real input, the returned angle is in the range -pi/2 through pi/2.
 * 
 * http://www.milefoot.com/math/complex/functionsofi.htm
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ArcTan.html">ArcTan</a> */
public enum ArcTan implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  private static final Scalar I_HALF = ComplexScalar.I.multiply(RationalScalar.HALF);

  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof RealScalar)
      return DoubleScalar.of(Math.atan(scalar.number().doubleValue()));
    return I_HALF.multiply(Log.FUNCTION.apply(ComplexScalar.I.add(scalar).divide(ComplexScalar.I.subtract(scalar))));
  }

  /** CAREFUL: the ordering of input arguments is
   * consistent with Mathematica::ArcTan[x, y]
   * but opposite to java.lang.Math::atan2(y, x)
   * 
   * ArcTan[x, y] == -ArcTan[x, -y]
   * ArcTan[x, y] == ArcTan[x * lambda, y * lambda] for positive lambda
   * 
   * ArcTan.of(0, 0) == 0 is not consistent with Mathematica.
   * Mathematica::ArcTan[0, 0] is undefined
   * 
   * @param x
   * @param y
   * @return arc tangent of y/x, taking into account which quadrant the point (x,y) is in */
  public static Scalar of(Scalar x, Scalar y) {
    if (y instanceof ArcTanInterface) {
      ArcTanInterface arcTanInterface = (ArcTanInterface) y;
      return arcTanInterface.arcTan(x);
    }
    throw TensorRuntimeException.of(x, y);
  }

  /** @param x
   * @param y
   * @return ArcTan.of(RealScalar.of(x), RealScalar.of(y)) */
  public static Scalar of(Number x, Number y) {
    return of(RealScalar.of(x), RealScalar.of(y));
  }

  /** @param vector of the form {x, y, ...}
   * @return ArcTan[x, y] */
  public static Scalar ofVectorXY(Tensor vector) {
    return of(vector.Get(0), vector.Get(1));
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their arc tan */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }
}
