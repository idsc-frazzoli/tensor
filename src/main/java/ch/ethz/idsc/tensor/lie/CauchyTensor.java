// code by jph
package ch.ethz.idsc.tensor.lie;

import java.util.Collections;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;

/** definition of Cauchy tensor taken from [qi, luo 2017] p.248
 * 
 * the {@link HilbertMatrix} is a Cauchy tensor */
public enum CauchyTensor {
  ;
  /** @param vector generator
   * @param rank
   * @return
   * @throws Exception if input is not a vector */
  public static Tensor of(Tensor vector, int rank) {
    return Array.of(list -> list.stream().map(vector::Get).reduce(Scalar::add).get().reciprocal(), //
        Collections.nCopies(rank, vector.length()));
  }
}
