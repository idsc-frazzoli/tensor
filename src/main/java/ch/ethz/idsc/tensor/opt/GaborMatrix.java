// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.red.Norm2Squared;
import ch.ethz.idsc.tensor.sca.AbsSquared;
import ch.ethz.idsc.tensor.sca.Cos;
import ch.ethz.idsc.tensor.sca.Exp;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/GaborMatrix.html">GaborMatrix</a> */
public enum GaborMatrix {
  ;
  private static final Scalar TWO = RealScalar.of(2);

  /** only approximately consistent with Mathematica
   * 
   * @param r
   * @param k
   * @param phi
   * @return */
  public static Tensor of(int r, Tensor k, Scalar phi) {
    final Scalar sigma = RationalScalar.of(r, 2);
    final Scalar factor = AbsSquared.FUNCTION.apply(sigma).multiply(TWO).negate();
    final int m = 2 * r + 1;
    final Tensor offset = Tensors.vector(-r, -r);
    Tensor matrix = Array.of(list -> Norm2Squared.ofVector(Tensors.vector(list).add(offset)), m, m) //
        .divide(factor).map(Exp.FUNCTION);
    Tensor weight = Array.of(list -> k.dot(Tensors.vector(list).add(offset)).subtract(phi), m, m) //
        .map(Cos.FUNCTION);
    return weight.pmul(matrix);
  }
}
