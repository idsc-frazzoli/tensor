// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Unprotect;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.MatrixQ;

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
    return _of(matrix, Pivot.argMaxAbs);
  }

  /** @param matrix square matrix
   * @return determinant of m */
  public static Scalar withoutAbs(Tensor matrix) {
    return _of(matrix, Pivot.firstNonZero);
  }

  // helper function
  private static Scalar _of(Tensor matrix, Pivot pivot) {
    if (!MatrixQ.of(matrix))
      throw TensorRuntimeException.of(matrix);
    final int n = matrix.length();
    final int m = Unprotect.length0(matrix);
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
