// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.List;
import java.util.function.Function;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.ZeroScalar;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.UnitVector;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Conjugate;

/* package */ class QRDecompositionImpl implements QRDecomposition {
  private static final Function<Scalar, Scalar> CHOP = Chop.below(1e-12);
  // ---
  private Tensor Qinv;
  private Tensor R;

  QRDecompositionImpl(Tensor A) {
    List<Integer> dims = Dimensions.of(A);
    int n = dims.get(0);
    int m = dims.get(1);
    Qinv = IdentityMatrix.of(n);
    R = A;
    for (int k = 0; k < m; ++k) {
      Tensor H = reflect(R, k);
      Qinv = H.dot(Qinv);
      R = H.dot(R);
      for (int j = k; j < n; ++j)
        R.set(CHOP, j, k);
    }
  }

  private static Tensor reflect(Tensor R, int k) {
    int n = R.length();
    Tensor y = Tensors.vector(i -> i < k ? ZeroScalar.get() : R.Get(i, k), n);
    Scalar yn = Norm._2.of(y);
    if (yn instanceof ZeroScalar)
      return IdentityMatrix.of(n);
    Tensor delta = UnitVector.of(k, n).multiply(yn);
    final Tensor w;
    Scalar y0 = R.Get(k, k);
    if (y0 instanceof RealScalar) {
      RealScalar Y0 = (RealScalar) y0;
      w = y.add(Y0.signInt() != -1 ? delta : delta.negate());
    } else
      w = y.add(delta);
    Tensor cw = Conjugate.of(w);
    Scalar cwy = (Scalar) cw.dot(y);
    return IdentityMatrix.of(n).subtract(wcwt(w, cw.multiply(cwy.invert())));
  }

  private static Tensor wcwt(Tensor w, Tensor cw) {
    return Tensors.matrix((i, j) -> w.Get(i).multiply(cw.Get(j)), w.length(), cw.length());
  }

  @Override
  public Tensor getInverseQ() {
    return Qinv;
  }

  @Override
  public Tensor getR() {
    return R;
  }

  @Override
  public Tensor getQ() {
    return ConjugateTranspose.of(Qinv);
  }
}
