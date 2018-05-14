// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.VectorQ;

/** Quote from Wikipedia:
 * a Toeplitz matrix or diagonal-constant matrix, named after Otto Toeplitz,
 * is a matrix in which each descending diagonal from left to right is constant
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ToeplitzMatrix.html">ToeplitzMatrix</a> */
public enum ToeplitzMatrix {
  ;
  /** For example:
   * ToeplitzMatrix.of(Tensors.vector(1, 2, 3, 4, 5)) gives the 3x3 matrix
   * <pre>
   * [ 3 4 5 ]
   * [ 2 3 4 ]
   * [ 1 2 3 ]
   * </pre>
   * 
   * @param vector with odd number of entries
   * @return */
  public static Tensor of(Tensor vector) {
    VectorQ.require(vector);
    if (vector.length() % 2 == 0)
      throw TensorRuntimeException.of(vector);
    final int n = (vector.length() + 1) / 2;
    return Tensors.matrix((i, j) -> vector.Get(n - i + j - 1), n, n);
  }
}
