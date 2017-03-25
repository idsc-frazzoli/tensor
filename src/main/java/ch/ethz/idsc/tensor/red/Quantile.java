// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.ZeroScalar;
import ch.ethz.idsc.tensor.alg.Sort;
import ch.ethz.idsc.tensor.sca.Ceiling;

/** Quantile does not average as {@link Median}:
 * Quantile[{1,2}, 0.5] == 1
 * Median[{1,2}] == 3/2
 * 
 * implementation is compliant to Mathematica
 * 
 * inspired by
 * <a href="https://reference.wolfram.com/language/ref/Quantile.html">Quantile</a> */
public enum Quantile {
  ;
  /** Quantile[{0, 1, 2, 3, 4}, {0, 1/5, 2/5, 1}] == {0, 0, 1, 4}
   * 
   * @param vector unsorted
   * @param param is scalar or tensor with elements in interval [0, 1]
   * @return tensor with same dimensions as param */
  public static Tensor of(Tensor vector, Tensor param) {
    Tensor sorted = Sort.of(vector);
    return param.map(scalar -> _of(sorted, scalar));
  }

  private static Scalar _of(Tensor sorted, Scalar scalar) {
    Scalar length = RealScalar.of(sorted.length());
    int index = scalar.equals(ZeroScalar.get()) ? //
        0 : (Integer) Ceiling.function.apply(scalar.multiply(length)).subtract(RealScalar.ONE).number();
    return sorted.Get(index);
  }
}
