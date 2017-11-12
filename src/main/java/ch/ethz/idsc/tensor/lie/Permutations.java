// code by jph
package ch.ethz.idsc.tensor.lie;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Sort;

/** implementation is consistent with Mathematica
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Permutations.html">Permutations</a> */
public enum Permutations {
  ;
  /** @param tensor
   * @return */
  public static Tensor of(Tensor tensor) {
    return Build.permutations(tensor);
  }

  // helper class
  static class Build {
    static Tensor permutations(Tensor tensor) {
      Build builder = new Build();
      builder.recur(Tensors.empty(), tensor);
      return builder.build;
    }

    private final Tensor build = Tensors.empty();

    void recur(Tensor ante, Tensor post) {
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
