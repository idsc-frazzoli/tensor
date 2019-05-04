// code by jph
package ch.ethz.idsc.tensor.lie;

import java.util.HashMap;
import java.util.Map;

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
  private static final Map<Scalar, Tensor> EXACT = new HashMap<>();
  static {
    EXACT.put(RationalScalar.of(0, 6), Tensors.vector(+1, +0));
    EXACT.put(RationalScalar.of(1, 6), Tensors.of(Cos.FUNCTION.apply(Pi.VALUE.multiply(RationalScalar.of(1, 6))), RationalScalar.HALF));
    EXACT.put(RationalScalar.of(2, 6), Tensors.of(RationalScalar.HALF, Sin.FUNCTION.apply(Pi.VALUE.multiply(RationalScalar.of(2, 6)))));
    EXACT.put(RationalScalar.of(3, 6), Tensors.vector(+0, +1));
    EXACT.put(RationalScalar.of(4, 6), Tensors.of(RationalScalar.HALF.negate(), Sin.FUNCTION.apply(Pi.VALUE.multiply(RationalScalar.of(4, 6)))));
    EXACT.put(RationalScalar.of(5, 6), Tensors.of(Cos.FUNCTION.apply(Pi.VALUE.multiply(RationalScalar.of(5, 6))), RationalScalar.HALF));
    EXACT.put(RationalScalar.of(6, 6), Tensors.vector(-1, +0));
    EXACT.put(RationalScalar.of(7, 6), Tensors.of(Cos.FUNCTION.apply(Pi.VALUE.multiply(RationalScalar.of(7, 6))), RationalScalar.HALF.negate()));
    EXACT.put(RationalScalar.of(8, 6), Tensors.of(RationalScalar.HALF.negate(), Sin.FUNCTION.apply(Pi.VALUE.multiply(RationalScalar.of(8, 6)))));
    EXACT.put(RationalScalar.of(9, 6), Tensors.vector(+0, -1));
    EXACT.put(RationalScalar.of(10, 6), Tensors.of(RationalScalar.HALF, Sin.FUNCTION.apply(Pi.VALUE.multiply(RationalScalar.of(10, 6)))));
    EXACT.put(RationalScalar.of(11, 6), Tensors.of(Cos.FUNCTION.apply(Pi.VALUE.multiply(RationalScalar.of(11, 6))), RationalScalar.HALF.negate()));
  }
  private static final Mod MOD_UNIT = Mod.function(1);

  /** @param angle in radians
   * @return vector as {Cos[angle], Sin[angle]} */
  public static Tensor of(Scalar angle) {
    return Tensors.of(Cos.FUNCTION.apply(angle), Sin.FUNCTION.apply(angle));
  }

  /** Hint: for certain input the function {@link #rotation(Scalar)} returns values in exact precision.
   * 
   * Examples:
   * <pre>
   * AngleVector.rotation(0/2) == {+1, 0}
   * AngleVector.rotation(1/2) == {-1, 0}
   * </pre>
   * 
   * @param fraction of a full rotation, for instance fraction == 1/2 means half rotation
   * @return AngleVector.of(fraction.multiply(Pi.TWO)) */
  public static Tensor rotation(Scalar fraction) {
    Scalar scalar = MOD_UNIT.apply(fraction);
    scalar = scalar.add(scalar);
    return scalar instanceof RationalScalar && EXACT.containsKey(scalar) //
        ? EXACT.get(scalar)
        : of(scalar.multiply(Pi.VALUE));
  }
}
