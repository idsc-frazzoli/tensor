// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/LinearSolve.html">LinearSolve</a> */
public enum LinearSolve {
  ;
  /** gives solution to linear system of equations.
   * scalar entries are required to implement
   * Comparable<Scalar> for pivoting.
   * 
   * @param m square matrix of {@link Scalar}s that implement absolute value abs()
   * @param b tensor with first dimension identical to size of matrix
   * @return x with m.dot(x) == b
   * @throws TensorRuntimeException if matrix m is singular */
  public static Tensor of(Tensor m, Tensor b) {
    return new GaussianElimination(m, b, Pivot.argMaxAbs).solution();
  }

  /** method only checks for non-zero
   * and doesn't use Scalar::abs().
   * 
   * @param m square matrix
   * @param b tensor with first dimension identical to size of matrix
   * @return x with m.dot(x) == b
   * @throws TensorRuntimeException if matrix m is singular */
  public static Tensor withoutAbs(Tensor m, Tensor b) { // function name is not final
    return new GaussianElimination(m, b, Pivot.firstNonZero).solution();
  }
}
