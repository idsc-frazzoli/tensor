// code by jph
package ch.ethz.idsc.tensor.lie;

import java.io.Serializable;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.Unprotect;
import ch.ethz.idsc.tensor.alg.Normalize;
import ch.ethz.idsc.tensor.mat.ConjugateTranspose;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.red.Diagonal;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.red.Norm2Squared;
import ch.ethz.idsc.tensor.red.Total;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Conjugate;

/** decomposition Q.R = A with Det[Q] == +1
 * householder with even number of reflections
 * reproduces example on wikipedia */
/* package */ class QRDecompositionImpl implements QRDecomposition, Serializable {
  private static final Scalar TWO = RealScalar.of(2);
  // ---
  private final int n;
  private final int m;
  private final Tensor eye;
  private final QRSignOperator qrSignOperator;
  private Tensor Qinv;
  private Tensor R;

  /** @param A
   * @param qrSignOperator
   * @throws Exception if input is not a matrix */
  QRDecompositionImpl(Tensor A, QRSignOperator qrSignOperator) {
    n = A.length();
    m = Unprotect.dimension1(A);
    eye = IdentityMatrix.of(n);
    this.qrSignOperator = qrSignOperator;
    Qinv = eye;
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

  // suggestion of wikipedia
  private Tensor reflect(final int k) {
    Tensor x = Tensors.vector(i -> i < k ? RealScalar.ZERO : R.Get(i, k), n);
    Scalar xn = Norm._2.ofVector(x);
    if (Scalars.isZero(xn))
      return eye; // reflection reduces to identity, hopefully => det == 0
    Scalar sign = qrSignOperator.of(R.Get(k, k));
    x.set(value -> value.subtract(sign.multiply(xn)), k);
    final Tensor m;
    if (ExactScalarQ.all(x))
      m = TensorProduct.of(x, Conjugate.of(x).multiply(TWO).divide(Norm2Squared.ofVector(x)));
    else {
      Tensor v = Normalize.of(x);
      m = TensorProduct.of(v, Conjugate.of(v).multiply(TWO));
    }
    Tensor r = eye.subtract(m);
    r.set(Tensor::negate, k); // 2nd reflection
    return r;
  }

  @Override // from QRDecomposition
  public Tensor getInverseQ() {
    return Qinv;
  }

  @Override // from QRDecomposition
  public Tensor getR() {
    return R;
  }

  @Override // from QRDecomposition
  public Tensor getQ() {
    return ConjugateTranspose.of(Qinv);
  }

  @Override // from QRDecomposition
  public Scalar det() {
    return n == m ? Total.prod(Diagonal.of(R)).Get() : RealScalar.ZERO;
  }
}
