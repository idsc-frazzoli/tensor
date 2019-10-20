// code by guedelmi
// modified by jph
package ch.ethz.idsc.tensor.mat;

import java.io.Serializable;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Ordering;
import ch.ethz.idsc.tensor.red.Diagonal;
import ch.ethz.idsc.tensor.red.Hypot;
import ch.ethz.idsc.tensor.red.Norm1;
import ch.ethz.idsc.tensor.sca.Sign;

/** The Jacobi transformations of a real symmetric matrix establishes the
 * diagonal matrix D
 * 
 * D == V* . A . V,
 * 
 * where the matrix V,
 * 
 * V == P1 * P2 * P3 * ...,
 * 
 * is the product of the successive Jacobi rotation matrices Pi. The diagonal
 * entries of D are the eigenvalues of A and the columns of V are the
 * eigenvectors of A. */
/* package */ class JacobiMethod implements Eigensystem, Serializable {
  private static final int MAX_ITERATIONS = 50;
  private static final Scalar HUNDRED = RealScalar.of(100);
  private static final Scalar EPS = DoubleScalar.of(Math.ulp(1));
  // ---
  private final int n;
  private Tensor V;
  private Tensor d;

  /** @param matrix symmetric, non-empty, and real valued */
  public JacobiMethod(Tensor matrix) {
    Tensor A = matrix.copy();
    n = A.length();
    V = IdentityMatrix.of(n);
    Tensor z = Array.zeros(n);
    Tensor b = Diagonal.of(matrix);
    d = b.copy();
    Scalar factor = DoubleScalar.of(0.2 / (n * n));
    for (int iteration = 0; iteration < MAX_ITERATIONS; ++iteration) {
      Scalar sum = Norm1.ofVector(UpperTriangularize.of(A, 1).flatten(1).map(Scalar.class::cast));
      if (Scalars.isZero(sum)) {
        Integer[] ordering = Ordering.DECREASING.of(d);
        d = Tensor.of(Stream.of(ordering).map(d::get)).unmodifiable();
        V = Tensor.of(Stream.of(ordering).map(V::get)).unmodifiable();
        return;
      }
      Scalar tresh = iteration < 4 //
          ? sum.multiply(factor)
          : RealScalar.ZERO;
      for (int ip = 0; ip < n - 1; ++ip) {
        for (int iq = ip + 1; iq < n; ++iq) {
          Scalar g = HUNDRED.multiply(A.Get(ip, iq).abs());
          if (4 < iteration && //
              Scalars.lessEquals(g, EPS.multiply(d.Get(ip).abs())) && //
              Scalars.lessEquals(g, EPS.multiply(d.Get(iq).abs()))) {
            A.set(Scalar::zero, ip, iq);
          } else //
          if (Scalars.lessThan(tresh, A.Get(ip, iq).abs())) {
            Scalar h = d.Get(iq).subtract(d.Get(ip));
            Scalar t;
            if (Scalars.lessEquals(g, EPS.multiply(h.abs()))) {
              t = A.Get(ip, iq).divide(h);
            } else {
              Scalar theta = RationalScalar.HALF.multiply(h).divide(A.Get(ip, iq));
              t = theta.abs().add(Hypot.of(theta, RealScalar.ONE)).reciprocal();
              if (Sign.isNegative(theta))
                t = t.negate();
            }
            Scalar c = Hypot.of(t, RealScalar.ONE).reciprocal();
            Scalar s = t.multiply(c);
            Scalar tau = s.divide(c.add(RealScalar.ONE));
            final Scalar fh = t.multiply(A.Get(ip, iq));
            z.set(v -> v.subtract(fh), ip);
            z.set(v -> v.add(fh), iq);
            d.set(v -> v.subtract(fh), ip);
            d.set(v -> v.add(fh), iq);
            A.set(Scalar::zero, ip, iq);
            final int fip = ip;
            final int fiq = iq;
            IntStream.range(0, ip).forEach(j -> rotate(A, s, tau, j, fip, j, fiq));
            IntStream.range(ip + 1, iq).forEach(j -> rotate(A, s, tau, fip, j, j, fiq));
            IntStream.range(iq + 1, n).forEach(j -> rotate(A, s, tau, fip, j, fiq, j));
            IntStream.range(0, n).forEach(j -> rotate(V, s, tau, fip, j, fiq, j));
          }
        }
      }
      b = b.add(z);
      z = Array.zeros(n);
      d = b.copy();
    }
    throw TensorRuntimeException.of(A);
  }

  @Override // from Eigensystem
  public Tensor vectors() {
    return V;
  }

  @Override // from Eigensystem
  public Tensor values() {
    return d;
  }

  private static void rotate(Tensor A, Scalar s, Scalar tau, int i, int j, int k, int l) {
    Scalar g = A.Get(i, j);
    Scalar h = A.Get(k, l);
    A.set(g.subtract(s.multiply(h.add(g.multiply(tau)))), i, j);
    A.set(h.add(s.multiply(g.subtract(h.multiply(tau)))), k, l);
  }
}
