// code by jph
package ch.ethz.idsc.tensor.lie;

import java.util.Collections;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.alg.Array;

/** definition of Hankel tensor taken from [qi, luo 2017] p.230 */
public enum HankelTensor {
  ;
  /** @param vector
   * @param rank
   * @return
   * @throws Exception if input is not a vector */
  public static Tensor of(Tensor vector, int rank) {
    int last = vector.length() - 1;
    if (last % rank != 0)
      throw TensorRuntimeException.of(vector);
    int dim = last / rank + 1;
    return Array.of(list -> vector.Get(list.stream().reduce(Integer::sum).get()), //
        Collections.nCopies(rank, dim));
  }
}
