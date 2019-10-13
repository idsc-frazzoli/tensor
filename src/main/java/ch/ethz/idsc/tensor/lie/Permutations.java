// code by jph
package ch.ethz.idsc.tensor.lie;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.ScalarQ;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.Unprotect;
import ch.ethz.idsc.tensor.alg.Sort;

/** implementation is consistent with Mathematica
 * 
 * <p>Example:
 * <pre>
 * Permutations.of({1, 2, 1}) == {{1, 2, 1}, {1, 1, 2}, {2, 1, 1}}
 * </pre>
 * 
 * <p>For Scalar input the function is not defined
 * Permutations.of(5) // throws an exception
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Permutations.html">Permutations</a> */
public class Permutations {
  /** @param tensor that is not a {@link Scalar}
   * @return
   * @throws Exception if given tensor is a scalar */
  public static Tensor of(Tensor tensor) {
    ScalarQ.thenThrow(tensor);
    return new Permutations(tensor).tensor;
  }

  // ---
  private Permutations(Tensor tensor) {
    recur(Tensors.empty(), tensor);
  }

  private final Tensor tensor = Unprotect.using(new LinkedList<>());

  private void recur(Tensor ante, Tensor post) {
    int length = post.length();
    if (length == 0)
      tensor.append(ante);
    else {
      Set<Tensor> set = new HashSet<>();
      for (int index = 0; index < length; ++index) {
        Tensor key = Tensor.of(Stream.concat( //
            post.stream().limit(index), //
            post.stream().skip(index + 1)));
        if (set.add(Sort.of(key)))
          recur(ante.copy().append(post.get(index)), key);
      }
    }
  }
}
