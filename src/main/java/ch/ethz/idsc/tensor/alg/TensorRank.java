// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Optional;

import ch.ethz.idsc.tensor.Tensor;

/** implementation consistent with Mathematica
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/TensorRank.html">TensorRank</a> */
public enum TensorRank {
  ;
  /** Examples:
   * for Scalars ...... TensorRank[3.14] == 0
   * for Vectors ...... TensorRank[{}] == 1
   * .................. TensorRank[{1, 2, 3}] == 1
   * for Matrices ..... TensorRank[{{}}] == 2
   * .................. TensorRank[{{1, 2, 3}, {4, 5, 6}}] == 2
   * for Lie-algebras.. TensorRank[LieAlgebras.so3()] == 3
   * 
   * for other cases see the documentation provided by Mathematica
   * 
   * @return rank of this tensor */
  public static int of(Tensor tensor) {
    return Dimensions.of(tensor).size();
  }

  /** @param tensor
   * @return rank of tensor if tensor is an array, else Optional.empty() */
  public static Optional<Integer> ofArray(Tensor tensor) {
    return Dimensions.arrayRank(tensor);
  }
}
