// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;

/** implementation consistent with Mathematica
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/TensorRank.html">TensorRank</a> */
public enum TensorRank {
  ;
  /** Examples:
   * for Scalars
   * TensorRank[3.14] == 0
   * for Vectors
   * TensorRank[{}] == 1
   * TensorRank[{1, 2, 3}] == 1
   * for Matrices
   * TensorRank[{{}}] == 2
   * TensorRank[{{1, 2, 3}, {4, 5, 6}}] == 2
   * 
   * @return rank of this tensor */
  public static int of(Tensor tensor) {
    return Dimensions.of(tensor).size();
  }
}
