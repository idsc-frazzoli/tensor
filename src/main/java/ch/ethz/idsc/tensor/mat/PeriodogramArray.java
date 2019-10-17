// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Partition;
import ch.ethz.idsc.tensor.red.Mean;
import ch.ethz.idsc.tensor.sca.AbsSquared;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/PeriodogramArray.html">PeriodogramArray</a> */
public enum PeriodogramArray {
  ;
  /** @param vector of length of power of 2
   * @return */
  public static Tensor of(Tensor vector) {
    return Fourier.of(vector).map(AbsSquared.FUNCTION);
  }

  /** @param vector of length of power of 2
   * @param size positive
   * @return */
  public static Tensor of(Tensor vector, int size) {
    return of(vector, size, size);
  }

  /** @param vector of length of power of 2
   * @param size not smaller than offset
   * @param offset positive
   * @return */
  public static Tensor of(Tensor vector, int size, int offset) {
    return Mean.of(Tensor.of(Partition.stream(vector, size, offset).map(Fourier::of).map(AbsSquared::of)));
  }
}
