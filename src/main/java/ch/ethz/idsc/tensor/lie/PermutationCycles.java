// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.VectorQ;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/PermutationCycles.html">PermutationCycles</a> */
public enum PermutationCycles {
  ;
  public static Tensor of(Tensor vector) {
    VectorQ.elseThrow(vector);
    Tensor cycles = Tensors.empty();
    for (int index = 0; index < vector.length(); ++index) {
      Scalar first = vector.Get(index);
      Scalar next = vector.Get(first.number().intValue());
      while (!first.equals(next)) {
      }
    }
    // FIXME
    return cycles;
  }
}
