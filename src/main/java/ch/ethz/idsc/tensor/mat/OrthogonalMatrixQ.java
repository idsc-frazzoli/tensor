// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.sca.Chop;

/** Mathematica definition:
 * "A matrix m is orthogonal if m.Transpose[m] is the identity matrix."
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/OrthogonalMatrixQ.html">OrthogonalMatrixQ</a> */
public enum OrthogonalMatrixQ {
  ;
  /** @param tensor
   * @param chop
   * @return true, if given tensor is a matrix and tensor.Transpose[tensor] is the identity matrix */
  public static boolean of(Tensor tensor, Chop chop) {
    return StaticHelper.dotId(tensor, chop, Transpose::of);
  }

  /** @param tensor
   * @return true, if given tensor is a matrix and tensor.Transpose[tensor] is the identity matrix
   * @see UnitaryMatrixQ */
  public static boolean of(Tensor tensor) {
    return of(tensor, Tolerance.CHOP);
  }

  /** @param tensor
   * @param chop
   * @return
   * @throws Exception if given tensor is not an orthogonal matrix */
  public static Tensor require(Tensor tensor, Chop chop) {
    if (of(tensor, chop))
      return tensor;
    throw TensorRuntimeException.of(tensor);
  }

  /** @param tensor
   * @param chop
   * @return
   * @throws Exception if given tensor is not an orthogonal matrix */
  public static Tensor require(Tensor tensor) {
    if (of(tensor))
      return tensor;
    throw TensorRuntimeException.of(tensor);
  }
}
