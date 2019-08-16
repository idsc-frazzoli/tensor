// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.Chop;

/** Mathematica definition:
 * "A matrix m is unitary if m.ConjugateTranspose[m] is the identity matrix."
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/UnitaryMatrixQ.html">UnitaryMatrixQ</a> */
public enum UnitaryMatrixQ {
  ;
  /** @param tensor
   * @return true, if tensor is a matrix and tensor.ConjugateTranspose[tensor] is the identity matrix */
  public static boolean of(Tensor tensor, Chop chop) {
    return StaticHelper.dotId(tensor, chop, ConjugateTranspose::of);
  }

  public static boolean of(Tensor tensor) {
    return of(tensor, Tolerance.CHOP);
  }
}
