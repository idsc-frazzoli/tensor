// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.ZeroScalar;
import ch.ethz.idsc.tensor.sca.Exp;

/** applications of {@link FourierMatrix} is to perform fourier transform and
 * inverse transform of vectors or matrices */
public enum FourierMatrix {
  ;
  private static final Scalar PI = DoubleScalar.of(Math.PI);

  /** @param n
   * @return square matrix of dimensions [n x n] with complex entries
   * <code>(i,j) -> exp(i * j * 2pi/n *I)</code> */
  public static Tensor of(int n) {
    Scalar scalar = RealScalar.of(1 / Math.sqrt(n));
    return Tensors.matrix((i, j) -> //
    Exp.function.apply(ComplexScalar.of(ZeroScalar.get(), //
        RationalScalar.of(2 * i * j, n).multiply(PI))).multiply(scalar), n, n);
  }

  /** @param n
   * @return inverse of fourier matrix */
  public static Tensor inverse(int n) {
    return ConjugateTranspose.of(of(n));
  }
}
