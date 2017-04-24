// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.function.Function;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.ZeroScalar;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Mod.html">Mod</a> */
public class Mod implements Function<Scalar, Scalar> {
  /** @param n
   * @return remainder on division by n */
  public static Mod function(Scalar n) {
    return function(n, ZeroScalar.get());
  }

  /** @param n
   * @param d
   * @return remainder on division by n with offset d */
  public static Mod function(Scalar n, Scalar d) {
    return new Mod(n, d);
  }

  private final Scalar n;
  private final Scalar d;

  private Mod(Scalar n, Scalar d) {
    this.n = n;
    this.d = d;
  }

  @Override
  public Scalar apply(Scalar scalar) {
    Scalar loops = Floor.function.apply(scalar.subtract(d).divide(n));
    // if (Scalars.lessThan(loops.abs(), RealScalar.of(5)))
    // return iterate(scalar);
    return scalar.subtract(loops.multiply(n));
  }

  // helper function
  @SuppressWarnings("unused")
  private Scalar iterate(Scalar scalar) {
    Scalar shift = scalar;
    while (Scalars.lessThan(shift, d))
      shift = shift.add(n);
    while (Scalars.lessEquals(d.add(n), shift))
      shift = shift.subtract(n);
    return shift;
  }

  public Tensor of(Tensor tensor) {
    return tensor.map(this);
  }
}
