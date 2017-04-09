// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.UnitVector;
import ch.ethz.idsc.tensor.red.Norm;

class QRDecompositionImpl implements QRDecomposition {
  QRDecompositionImpl(Tensor A) {
    Tensor y = A.get(-1, 0);
    // System.out.println(y);
    Scalar yn = Norm._2.of(y);
    // System.out.println(yn);
    Tensor w = y.subtract(UnitVector.of(0, 4).multiply(yn));
    Scalar w2 = Norm._2Squared.of(w);
    Tensor R1 = vvt(w).multiply(w2.invert());
    Tensor H1 = IdentityMatrix.of(4).subtract(R1.multiply(RealScalar.of(2)));
    // System.out.println(Pretty.of(H1.dot(A)));
  }

  private static Tensor vvt(Tensor v) {
    return Tensors.matrix((i, j) -> v.Get(i).multiply(v.Get(j)), v.length(), v.length());
  }

  @Override
  public Tensor getQ() {
    return null;
  }

  @Override
  public Tensor getR() {
    return null;
  }
}
