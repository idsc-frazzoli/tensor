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
 * <a href="https://reference.wolfram.com/language/ref/TensorWedge.html">TensorWedge</a>
 * 
 * @see Permutations
 * @see Transpose
 * @see Signature */
public enum TensorWedge {
  ;
  /** @param a of any rank with dimensions [n, n, ..., n]
   * @param b of any rank with dimensions [n, n, ..., n]
   * @return alternating tensor product of a and b */
  public static Tensor of(Tensor a, Tensor b) {
    return of(TensorProduct.of(a, b));
  }

  /** @param tensor of any rank with dimensions [n, n, ..., n]
   * @return alternating tensor
   * @throws Exception if given tensor does not have regular dimensions */
  public static Tensor of(Tensor tensor) {
    Tensor sum = tensor.map(Scalar::zero);
    int rank = TensorRank.of(tensor);
    for (Tensor permutation : Permutations.of(Range.of(0, rank))) {
      Integer[] sigma = IntStream.range(0, rank) //
          .mapToObj(index -> permutation.Get(index).number()) //
          .toArray(Integer[]::new);
      Tensor transpose = Transpose.of(tensor, sigma);
      sum = Signature.of(sigma).equals(RealScalar.ONE) //
          ? sum.add(transpose)
          : sum.subtract(transpose);
    }
    return sum.divide(Factorial.of(rank));
  }
}
