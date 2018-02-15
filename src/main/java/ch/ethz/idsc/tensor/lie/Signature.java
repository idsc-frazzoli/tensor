// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** Hint: the tensor library implementation of Signature is not as general as
 * Mathematica::Signature. For instance, Mathematica::Signature may also return 0.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Signature.html">Signature</a> */
public enum Signature {
  ;
  private static final Scalar[] SIGN = new Scalar[] { RealScalar.ONE, RealScalar.ONE.negate() };

  /** @param tensor with integer entries, for instance {2, 0, 1}
   * @return either +1 or -1 */
  public static Scalar of(Tensor tensor) {
    Tensor vector = tensor.copy();
    int transpositions = 0;
    for (int index = 0; index < vector.length(); ++index)
      while (vector.Get(index).number().intValue() != index) {
        Scalar value = vector.Get(index);
        int jndex = value.number().intValue();
        vector.set(vector.Get(jndex), index);
        vector.set(value, jndex);
        ++transpositions;
      }
    return SIGN[transpositions % 2];
  }
}
