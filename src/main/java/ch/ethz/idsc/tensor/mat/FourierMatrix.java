// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.Internal;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.sca.Sqrt;

/** applications of {@link FourierMatrix} is to perform {@link Fourier} transform
 * and inverse transform of vectors or matrices of arbitrary dimensions.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/FourierMatrix.html">FourierMatrix</a> */
public enum FourierMatrix {
  ;
  /** @param n positive
   * @return square matrix of dimensions [n x n] with complex entries
   * <code>(i, j) -> sqrt(1/n) exp(i * j * 2pi/n *I)</code> */
  public static Tensor of(int n) {
    double factor = 2 * Math.PI / Internal.requirePositive(n);
    Scalar scalar = Sqrt.FUNCTION.apply(RationalScalar.of(1, n));
    return Tensors.matrix((i, j) -> //
    ComplexScalar.unit(DoubleScalar.of(i * j * factor)).multiply(scalar), n, n);
  }

  /** @param n
   * @return inverse of fourier matrix */
  public static Tensor inverse(int n) {
    return ConjugateTranspose.of(of(n));
  }
}
