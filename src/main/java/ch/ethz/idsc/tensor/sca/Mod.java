// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** our implementation is <em>not</em> consistent with Mathematica for negative, and complex n.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Mod.html">Mod</a> */
public class Mod implements ScalarUnaryOperator {
  /** @param n
   * @return remainder on division by n */
  public static Mod function(Scalar n) {
    return function(n, n.zero());
  }

  /** @param n
   * @return remainder on division by n */
  public static Mod function(Number n) {
    return function(RealScalar.of(n));
  }

  /** @param n
   * @param d
   * @return remainder on division by n with offset d */
  public static Mod function(Scalar n, Scalar d) {
    if (Scalars.isZero(n))
      throw TensorRuntimeException.of(n);
    return new Mod(n, d);
  }

  /** @param n
   * @param d
   * @return remainder on division by n with offset d */
  public static Mod function(Number n, Number d) {
    return function(RealScalar.of(n), RealScalar.of(d));
  }
  // ---

  private final Scalar n;
  private final Scalar d;

  private Mod(Scalar n, Scalar d) {
    this.n = n;
    this.d = d;
  }

  @Override
  public Scalar apply(Scalar scalar) {
    Scalar loops = Floor.FUNCTION.apply(scalar.subtract(d).divide(n));
    return scalar.subtract(loops.multiply(n));
  }

  @SuppressWarnings("unchecked")
  public <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(this);
  }
}
