// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.function.BiFunction;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.ZeroScalar;
import ch.ethz.idsc.tensor.sca.Sqrt;
import ch.ethz.idsc.tensor.sca.SqrtInterface;

/** Hypot computes
 * <code>sqrt(<i>a</i><sup>2</sup>&nbsp;+<i>b</i><sup>2</sup>)</code>
 * for a and b as {@link RealScalar}s
 * without intermediate overflow or underflow.
 * 
 * Hypot does <b>not</b> make use of {@link SqrtInterface}. */
public enum Hypot implements BiFunction<Scalar, Scalar, Scalar> {
  bifunction;
  // ---
  @Override
  public Scalar apply(Scalar a, Scalar b) {
    Scalar ax = a.abs();
    Scalar ay = b.abs();
    Scalar min = Min.of(ax, ay);
    Scalar max = Max.of(ax, ay);
    if (min.equals(ZeroScalar.get()))
      return max; // if minimum == 0 return maximum
    // else 0 < t <= max
    min = min.divide(max);
    return max.multiply(Sqrt.function.apply(RealScalar.ONE.add(min.multiply(min))));
  }

  /** function computes the 2-Norm of a vector
   * without intermediate overflow or underflow
   * 
   * @param vector
   * @return */
  public static Scalar ofVector(Tensor vector) {
    if (vector.length() == 0) // <- condition not compliant with Mathematica
      return ZeroScalar.get();
    Tensor abs = vector.map(Scalar::abs);
    Scalar max = (Scalar) abs.flatten(0).reduce(Max::of).get();
    if (max.equals(ZeroScalar.get()))
      return ZeroScalar.get();
    abs = abs.multiply(max.invert());
    return (Scalar) Sqrt.of(abs.dot(abs)).multiply(max);
  }
}
