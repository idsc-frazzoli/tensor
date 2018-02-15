// code by jph
package ch.ethz.idsc.tensor.lie;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Range;
import ch.ethz.idsc.tensor.alg.TensorRank;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.sca.Factorial;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/TensorWedge.html">TensorWedge</a> */
public enum TensorWedge {
  ;
  /** @param a
   * @param b
   * @return alternating tensor product of a and b */
  public static Tensor of(Tensor a, Tensor b) {
    return of(TensorProduct.of(a, b));
  }

  /** @param tensor
   * @return alternating tensor */
  public static Tensor of(Tensor tensor) {
    Tensor sum = tensor.map(Scalar::zero);
    int rank = TensorRank.of(tensor);
    Integer[] sigma = new Integer[rank];
    for (Tensor permutation : Permutations.of(Range.of(0, rank))) {
      IntStream.range(0, rank).forEach(index -> sigma[index] = permutation.Get(index).number().intValue());
      Tensor transpose = Transpose.of(tensor, sigma);
      sum = Signature.of(permutation).equals(RealScalar.ONE) ? sum.add(transpose) : sum.subtract(transpose);
    }
    return sum.divide(Factorial.of(rank));
  }
}
