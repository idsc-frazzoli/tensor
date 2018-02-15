// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Sort;
import ch.ethz.idsc.tensor.sca.Ceiling;

/** Quantile does not average as {@link Median}:
 * <code>Quantile[{1,2}, 0.5] == 1</code>,
 * <code>Median[{1,2}] == 3/2</code>
 * 
 * <p>implementation is compliant to Mathematica
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Quantile.html">Quantile</a> */
public enum Quantile {
  ;
  /** <code>Quantile[{0, 1, 2, 3, 4}, {0, 1/5, 2/5, 1}] == {0, 0, 1, 4}</code>
   * 
   * @param tensor unsorted
   * @param param is scalar or tensor with elements in interval [0, 1]
   * @return tensor with same dimensions as param */
  public static Tensor of(Tensor tensor, Tensor param) {
    return ofSorted(Sort.of(tensor), param);
  }

  /** Hint: the function does not verify that the sequence of entries in the
   * given vector are ordered. Should that not be the case, the return value
   * is most likely incorrect.
   * 
   * @param sorted vector
   * @param param is scalar or tensor with elements in interval [0, 1]
   * @return tensor with same dimensions as param */
  public static Tensor ofSorted(Tensor sorted, Tensor param) {
    return param.map(scalar -> pick(sorted, scalar));
  }

  // helper function
  private static Scalar pick(Tensor sorted, Scalar scalar) {
    Scalar index = Ceiling.FUNCTION.apply(scalar.multiply(RealScalar.of(sorted.length())));
    return sorted.Get(Scalars.isZero(scalar) ? 0 : Scalars.intValueExact(index.subtract(RealScalar.ONE)));
  }
}
