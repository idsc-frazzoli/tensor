// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.List;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.UnitVector;
import ch.ethz.idsc.tensor.red.Diagonal;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.red.Total;
import ch.ethz.idsc.tensor.sca.Arg;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Conjugate;
import ch.ethz.idsc.tensor.sca.Exp;
import ch.ethz.idsc.tensor.sca.SignInterface;

/* package */ class HouseholderQRDecomposition implements QRDecomposition {
  private final int n;
  private final int m;
  private Tensor Qinv;
  private Tensor R;

  HouseholderQRDecomposition(Tensor A) {
    List<Integer> dims = Dimensions.of(A);
    n = dims.get(0);
    m = dims.get(1);
    Qinv = IdentityMatrix.of(n);
    R = A;
    // the m-th reflection is necessary in the case where A is non-square
    for (int k = 0; k < m; ++k) {
      Tensor H = reflect(k);
      Qinv = H.dot(Qinv);
      R = H.dot(R);
    }
    // chop lower entries to symbolic zero
    for (int k = 0; k < m; ++k)
      for (int i = k + 1; i < n; ++i)
        R.set(Chop._12, i, k);
  }

  private Tensor reflect(int k) {
    Tensor y = Tensors.vector(i -> i < k ? RealScalar.ZERO : R.Get(i, k), n);
    Scalar yn = Norm._2.of(y);
    if (Scalars.isZero(yn))
      return IdentityMatrix.of(n); // reflection reduces to identity, hopefully => det == 0
    Tensor delta = UnitVector.of(n, k).multiply(yn);
    final Tensor w;
    Scalar y0 = R.Get(k, k);
    if (y0 instanceof SignInterface) {
      SignInterface Y0 = (SignInterface) y0;
      w = y.add(Y0.signInt() != -1 ? delta : delta.negate());
    } else
      w = y.add(delta);
    Tensor cw = Conjugate.of(w);
    Scalar cwy = (Scalar) cw.dot(y);
    return IdentityMatrix.of(n).subtract(wcwt(w, cw.divide(cwy)));
  }

  // outer product: product of all pairs
  private static Tensor wcwt(Tensor w, Tensor cw) {
    return Tensors.matrix((i, j) -> w.Get(i).multiply(cw.Get(j)), w.length(), cw.length());
  }

  // suggestion of wikipedia
  @SuppressWarnings("unused")
  private Tensor _reflect(final int k) {
    Tensor x = Tensors.vector(i -> i < k ? RealScalar.ZERO : R.Get(i, k), n);
    Scalar xn = Norm._2.of(x);
    if (Scalars.isZero(xn))
      return IdentityMatrix.of(n); // reflection reduces to identity, hopefully => det == 0
    Scalar y0 = R.Get(k, k);
    final Scalar alpha;
    if (y0 instanceof SignInterface) {
      SignInterface Y0 = (SignInterface) y0;
      alpha = Y0.signInt() == -1 ? xn : xn.negate();
    } else {
      alpha = Exp.of(ComplexScalar.of(RealScalar.ZERO, Arg.of(y0))).multiply(xn).negate();
    }
    Tensor e = UnitVector.of(n, k);
    final Tensor u = x.subtract(e.multiply(alpha));
    Scalar un = Norm._2SQUARED.of(u);
    Tensor v = u;
    Tensor cv = Conjugate.of(v);
    Scalar factor = RealScalar.of(2).divide(un);
    return IdentityMatrix.of(n).subtract(wcwt(v, cv.multiply(factor)));
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

  @Override
  public Scalar det() {
    if (n != m)
      return RealScalar.ZERO;
    // TODO formula is wrong for complex input
    Scalar scalar = Total.prod(Diagonal.of(R)).Get();
    return m % 2 == 0 ? scalar : scalar.negate();
  }
}
