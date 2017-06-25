// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.alg.Array;

/** implementation is consistent with Mathematica
 * 
 * <p>The determinant of an empty matrix Det[{{}}] throws an exception
 * just as <code>Mathematica::Det[{{}}]</code> results in Exception
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Det.html">Det</a> */
public enum Det {
  ;
  /** @param matrix
   * @return determinant of matrix */
  public static Scalar of(Tensor matrix) {
    if (!MatrixQ.of(matrix))
      throw TensorRuntimeException.of(matrix);
    return _of(matrix, Pivot.argMaxAbs);
  }

  /** @param matrix square matrix
   * @return determinant of m */
  public static Scalar withoutAbs(Tensor matrix) {
    if (!MatrixQ.of(matrix))
      throw TensorRuntimeException.of(matrix);
    return _of(matrix, Pivot.firstNonZero);
  }

  // helper function
  private static Scalar _of(Tensor matrix, Pivot pivot) {
    final int n = matrix.length();
    final int m = matrix.get(0).length();
    if (m == 0)
      throw TensorRuntimeException.of(matrix);
    if (n == m) // square
      try {
        return new GaussianElimination(matrix, Array.zeros(n), pivot).det();
      } catch (Exception exception) {
        // matrix is singular
      }
    return RealScalar.ZERO;
  }
}
