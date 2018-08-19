// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.DiagonalMatrix;
import ch.ethz.idsc.tensor.red.Times;
import ch.ethz.idsc.tensor.sca.Log;
import ch.ethz.idsc.tensor.sca.Sqrt;

/* package */ enum MatrixLog2 {
  ;
  private static final Scalar FOUR = RealScalar.of(4);
  private static final Scalar TWO = RealScalar.of(2);

  public static Tensor of(Tensor matrix) {
    Scalar a = matrix.Get(0, 0);
    Scalar b = matrix.Get(0, 1);
    Scalar c = matrix.Get(1, 0);
    Scalar d = matrix.Get(1, 1);
    if (Scalars.isZero(b) && Scalars.isZero(c)) // diagonal matrix
      return DiagonalMatrix.of(Log.of(a), Log.of(d));
    // ---
    Scalar ad = a.subtract(d);
    Scalar A = Sqrt.FUNCTION.apply(ad.multiply(ad).add(Times.of(b, c, FOUR)));
    Scalar s = a.add(A).add(d);
    Scalar p = a.subtract(A).add(d);
    Scalar q = A.add(d).subtract(a);
    Scalar t = a.add(A).subtract(d);
    Scalar r11 = Log.of(p.divide(TWO)).multiply(q).add(Log.of(s.divide(TWO)).multiply(t)).divide(TWO);
    Scalar r22 = Log.of(p.divide(TWO)).multiply(t).add(Log.of(s.divide(TWO)).multiply(q)).divide(TWO);
    Scalar r12 = Log.of(s).subtract(Log.of(p)).multiply(b);
    Scalar r21 = Log.of(s).subtract(Log.of(p)).multiply(c);
    return Tensors.matrix(new Scalar[][] { { r11, r12 }, { r21, r22 } }).divide(A);
  }
}
