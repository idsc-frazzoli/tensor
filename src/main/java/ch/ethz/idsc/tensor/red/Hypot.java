// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.function.BiFunction;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
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
    return DoubleScalar.of(Math.hypot(a.number().doubleValue(), b.number().doubleValue()));
  }
}
