// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.opt.Pi;
import ch.ethz.idsc.tensor.sca.Cos;
import ch.ethz.idsc.tensor.sca.Mod;
import ch.ethz.idsc.tensor.sca.Sin;

/** @see CirclePoints
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/AngleVector.html">AngleVector</a> */
public enum AngleVector {
  ;
  private static final Mod MOD = Mod.function(1);

  /** @param angle in radian
   * @return vector as {Cos[angle], Sin[angle]} */
  public static Tensor of(Scalar angle) {
    return Tensors.of(Cos.FUNCTION.apply(angle), Sin.FUNCTION.apply(angle));
  }

  /** For certain input the function {@link #turns(Scalar)} returns values in exact precision.
   * The function name is inspired by https://en.wikipedia.org/wiki/Turn_(geometry)
   * 
   * Examples:
   * <pre>
   * AngleVector.turns(0/2) == {+1, 0}
   * AngleVector.turns(1/2) == {-1, 0}
   * </pre>
   * 
   * @param turns of a full rotation, for instance turns == 1/2 means half rotation
   * @return AngleVector.of(turns.multiply(Pi.TWO)) */
  public static Tensor turns(Scalar turns) {
    Scalar scalar = MOD.apply(turns);
    return scalar instanceof RationalScalar && CirclePoint.INSTANCE.contains(scalar) //
        ? CirclePoint.INSTANCE.turns(scalar)
        : of(scalar.multiply(Pi.TWO));
  }
}
