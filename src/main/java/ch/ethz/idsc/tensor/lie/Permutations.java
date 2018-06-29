// code by jph
package ch.ethz.idsc.tensor.lie;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.ScalarQ;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
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
public enum Permutations {
  ;
  /** @param tensor that is not a {@link Scalar}
   * @return */
  public static Tensor of(Tensor tensor) {
    ScalarQ.thenThrow(tensor);
    return Build.permutations(tensor);
  }

  // helper class
  private static class Build {
    static Tensor permutations(Tensor tensor) {
      Build builder = new Build();
      builder.recur(Tensors.empty(), tensor);
      return builder.build;
    }

    private final Tensor build = Tensors.empty();

    private void recur(Tensor ante, Tensor post) {
      int length = post.length();
      if (length == 0)
        build.append(ante);
      else {
        Set<Tensor> set = new HashSet<>();
        for (int index = 0; index < length; ++index) {
          Tensor key = Tensor.of(Stream.concat(post.stream().limit(index), post.stream().skip(index + 1)));
          if (set.add(Sort.of(key)))
            recur(ante.copy().append(post.get(index)), key);
        }
      }
    }
  }
}
