// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.red.Max;
import ch.ethz.idsc.tensor.sca.Abs;

/** matrix exponential via power series
 * 
 * @see {@link MatrixExp} */
/* package */ class SeriesMatrixExp {
  private static final int MAXITER = 500;

  /** @param m
   * @return */
  static Tensor of(Tensor m) {
    final int n = m.length();
    Tensor sum = IdentityMatrix.of(n);
    Tensor nxt = IdentityMatrix.of(n);
    for (int k = 1; k < MAXITER; ++k) {
      nxt = nxt.dot(m).multiply(RationalScalar.of(1, k));
      Tensor prv = sum;
      sum = sum.add(nxt);
      if (Scalars.isZero(_maxAbsNumber(sum.subtract(prv))))
        return sum;
    }
    throw TensorRuntimeException.of(m);
  }

  // helper function to estimate distance to zero array
  private static Scalar _maxAbsNumber(Tensor matrix) {
    return matrix.flatten(-1) //
        .map(Scalar.class::cast) //
        .map(Abs.FUNCTION) //
        .map(Scalar::number) //
        .map(RealScalar::of) //
        .reduce(Max::of).get(); //
  }
}
