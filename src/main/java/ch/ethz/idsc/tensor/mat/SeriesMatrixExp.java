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
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.N;

/** matrix exponential via power series */
/* package */ class SeriesMatrixExp {
  static final int MAXITER = 100;

  /** @param m
   * @return */
  public static Tensor of(Tensor m) {
    final int n = m.length();
    Tensor sum = IdentityMatrix.of(n);
    Tensor nxt = IdentityMatrix.of(n); // identity matrix
    for (int k = 1; k < MAXITER; ++k) {
      nxt = nxt.dot(m).multiply(RationalScalar.of(1, k));
      sum = sum.add(nxt);
      Scalar norm = _maxAbsNumber(nxt);
      if (Scalars.isZero(norm))
        return sum;
      if (Chop._40.allZero(N.of(norm)))
        return N.of(sum);
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
