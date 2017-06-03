// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.function.Function;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Mod.html">Mod</a> */
public class Mod implements Function<Scalar, Scalar> {
  /** @param n
   * @return remainder on division by n */
  public static Mod function(Scalar n) {
    return function(n, n.zero());
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
    return scalar.subtract(loops.multiply(n));
  }

  public Tensor of(Tensor tensor) {
    return tensor.map(this);
  }
}
