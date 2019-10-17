// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.LinkedList;
import java.util.List;

import ch.ethz.idsc.tensor.ScalarQ;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.Unprotect;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Subsets.html">Subsets</a>
 * 
 * @see Tuples
 * @see Binomial */
public class Subsets {
  /** @param tensor of length n, not a scalar
   * @param k non-negative
   * @return tensor of length n choose k
   * @throws Exception if k is negative */
  public static Tensor of(Tensor tensor, int k) {
    ScalarQ.thenThrow(tensor);
    return Unprotect.using(new Subsets(tensor, k).list);
  }

  // ---
  private final List<Tensor> list = new LinkedList<>();

  private Subsets(Tensor tensor, int k) {
    recur(Tensors.empty(), tensor, k);
  }

  private void recur(Tensor ante, Tensor tensor, int k) {
    if (k == 0)
      list.add(ante);
    else {
      --k;
      int limit = tensor.length() - k;
      for (int index = 0; index < limit;)
        recur(ante.copy().append(tensor.get(index)), Tensor.of(tensor.stream().skip(++index)), k);
    }
  }
}
