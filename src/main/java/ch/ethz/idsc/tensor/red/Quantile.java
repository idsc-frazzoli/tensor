// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Integers;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.OrderedQ;
import ch.ethz.idsc.tensor.alg.Sort;
import ch.ethz.idsc.tensor.sca.Ceiling;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

/** Quantile does not average as {@link Median}:
 * <code>Quantile[{1, 2}, 0.5] == 1</code>,
 * <code>Median[{1, 2}] == 3/2</code>
 * 
 * <p>implementation is compliant to Mathematica
 * 
 * <p>Function arguments are required to be in the interval [0, 1].
 * 
 * <p>Example:
 * <code>Quantile[{0, 1, 2, 3, 4}, {0, 1/5, 2/5, 1}] == {0, 0, 1, 4}</code>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Quantile.html">Quantile</a> */
public class Quantile implements ScalarUnaryOperator {
  /** @param vector non-empty
   * @return
   * @throws Exception if input is a scalar */
  public static ScalarUnaryOperator of(Tensor vector) {
    return new Quantile(Sort.of(vector));
  }

  /** @param sorted vector non-empty
   * @return
   * @throws Exception if given sorted vector is not ordered
   * @see OrderedQ */
  public static ScalarUnaryOperator ofSorted(Tensor sorted) {
    return new Quantile(OrderedQ.require(sorted));
  }

  // ---
  private final Tensor vector;
  private final Scalar length;

  private Quantile(Tensor vector) {
    this.vector = vector;
    length = RealScalar.of(Integers.requirePositive(vector.length()));
  }

  @Override
  public Scalar apply(Scalar scalar) {
    return vector.Get(scalar.equals(RealScalar.ZERO) //
        ? 0
        : Scalars.intValueExact(Ceiling.FUNCTION.apply(scalar.multiply(length))) - 1);
  }
}
