// code by jph
package ch.ethz.idsc.tensor.red;

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
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Quantile.html">Quantile</a> */
public class Quantile implements ScalarUnaryOperator {
  /** <code>Quantile[{0, 1, 2, 3, 4}, {0, 1/5, 2/5, 1}] == {0, 0, 1, 4}</code>
   * 
   * @param tensor unsorted
   * @param param is scalar or tensor with elements in interval [0, 1]
   * @return tensor with same dimensions as param */
  public static Tensor of(Tensor tensor, Tensor param) {
    return param.map(of(tensor));
  }

  /** Hint: the function does not verify that the sequence of entries in the
   * given vector are ordered. Should that not be the case, the return value
   * is most likely incorrect.
   * 
   * @param sorted vector
   * @param param is scalar or tensor with elements in interval [0, 1]
   * @return tensor with same dimensions as param */
  public static Tensor ofSorted(Tensor sorted, Tensor param) {
    return param.map(new Quantile(OrderedQ.require(sorted)));
  }

  /** @param tensor
   * @return
   * @throws Exception if input is a scalar */
  public static ScalarUnaryOperator of(Tensor tensor) {
    return new Quantile(Sort.of(tensor));
  }

  // ---
  private final Tensor sorted;
  private final Scalar length;

  private Quantile(Tensor sorted) {
    this.sorted = sorted;
    length = RealScalar.of(sorted.length());
  }

  @Override
  public Scalar apply(Scalar scalar) {
    return sorted.Get(scalar.equals(RealScalar.ZERO) //
        ? 0
        : Scalars.intValueExact(Ceiling.FUNCTION.apply(scalar.multiply(length)).subtract(RealScalar.ONE)));
  }
}
