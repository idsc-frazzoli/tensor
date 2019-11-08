// code by jph
package ch.ethz.idsc.tensor.lie;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Range;
import ch.ethz.idsc.tensor.alg.TensorRank;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.sca.Factorial;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Symmetrize.html">Symmetrize</a>
 * 
 * @see Permutations
 * @see Transpose
 * @see Signature */
public enum Symmetrize {
  ;
  /** @param tensor of any rank with dimensions [n, n, ..., n]
   * @return symmetric tensor, i.e. invariant under transpose
   * @throws Exception if given tensor does not have regular dimensions */
  public static Tensor of(Tensor tensor) {
    Tensor sum = tensor.map(Scalar::zero);
    int rank = TensorRank.of(tensor);
    for (Tensor permutation : Permutations.of(Range.of(0, rank))) {
      Integer[] sigma = IntStream.range(0, rank) //
          .mapToObj(index -> permutation.Get(index).number()) //
          .toArray(Integer[]::new);
      Tensor transpose = Transpose.of(tensor, sigma);
      sum = sum.add(transpose);
    }
    return sum.divide(Factorial.of(rank));
  }
}
