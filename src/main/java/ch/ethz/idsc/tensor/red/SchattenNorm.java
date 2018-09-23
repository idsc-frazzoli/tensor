// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Unprotect;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.mat.SingularValueDecomposition;

/** Quote from Wikipedia:
 * The Schatten p-norms arise when applying the p-norm to the vector of singular values of a matrix.
 * 
 * p = 1 yields the nuclear norm (also known as the trace norm, or the Ky Fan 'n'-norm). */
public class SchattenNorm extends VectorNorm implements NormInterface {
  /** Hint: for enhanced precision, use p as instance of {@link RationalScalar} if possible
   * 
   * @param p exponent
   * @return */
  public static NormInterface with(Scalar p) {
    if (p.equals(RealScalar.of(2)))
      return Frobenius.NORM;
    return new SchattenNorm(p);
  }

  /** @param p exponent
   * @return */
  public static NormInterface with(Number p) {
    return with(RealScalar.of(p));
  }

  // ---
  SchattenNorm(Scalar p) {
    super(p);
  }

  @Override // from NormInterface
  public Scalar ofMatrix(Tensor matrix) {
    int rows = matrix.length();
    int cols = Unprotect.dimension1(matrix);
    return ofVector(SingularValueDecomposition.of(cols <= rows ? matrix : Transpose.of(matrix)).values());
  }
}
