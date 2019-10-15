// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.lie.Permutations;

/** implementation consistent with Mathematica except in special case n == 0
 * Tensor-Lib.::Tuples[{a, b, c}, 0] == {}
 * Mathematica::Tuples[{a, b, c}, 0] == {{}}
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Tuples.html">Tuples</a>
 * 
 * @see Array
 * @see Permutations
 * @see Subsets */
public enum Tuples {
  ;
  /** Example:
   * Tuples.of(Tensors.vector(3, 4, 5), 2) gives
   * {{3, 3}, {3, 4}, {3, 5}, {4, 3}, {4, 4}, {4, 5}, {5, 3}, {5, 4}, {5, 5}}
   * 
   * @param tensor of length k
   * @param n non-negative
   * @return tensor with k ^ n elements
   * @throws Exception if n is negative */
  public static Tensor of(Tensor tensor, int n) {
    Integer[] dimensions = IntStream.generate(() -> tensor.length()).limit(n).boxed().toArray(Integer[]::new);
    Tensor array = Array.of(list -> Tensor.of(list.stream().map(tensor::get)), dimensions);
    return n <= 1 ? array : Tensor.of(array.flatten(n - 1));
  }
}
