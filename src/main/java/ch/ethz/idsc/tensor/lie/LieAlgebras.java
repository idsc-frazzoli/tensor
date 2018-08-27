// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Array;

/** class provides ad-tensors of several low-dimensional Lie-algebras */
public enum LieAlgebras {
  ;
  private static final Scalar P1 = RealScalar.ONE;
  private static final Scalar M1 = RealScalar.ONE.negate();
  private static final Scalar P2 = RealScalar.of(+2);
  private static final Scalar M2 = RealScalar.of(-2);

  private static Tensor _so3() {
    Tensor ad = Array.zeros(3, 3, 3);
    ad.set(P1, 2, 1, 0);
    ad.set(M1, 2, 0, 1);
    ad.set(P1, 0, 2, 1);
    ad.set(M1, 0, 1, 2);
    ad.set(P1, 1, 0, 2);
    ad.set(M1, 1, 2, 0);
    return ad;
  }

  private static final Tensor SO3 = _so3().unmodifiable();

  /** @param x square matrix
   * @param y square matrix
   * @return Lie-bracket [x, y] == x.y - y.x
   * @throws Exception if x or y are not square matrices */
  public static Tensor bracketMatrix(Tensor x, Tensor y) {
    Tensor z = x.dot(y).subtract(y.dot(x));
    z.Get(0, 0); // asserts that z is a matrix
    return z;
  }

  /** @return ad tensor of 3-dimensional Heisenberg Lie-algebra */
  public static Tensor heisenberg3() {
    Tensor ad = Array.zeros(3, 3, 3);
    ad.set(P1, 2, 1, 0);
    ad.set(M1, 2, 0, 1);
    return ad;
  }

  /** @return ad tensor of 3-dimensional so(3) */
  public static Tensor so3() {
    return SO3;
  }

  /** @return ad tensor of 3-dimensional sl(2) */
  public static Tensor sl2() {
    Tensor ad = Array.zeros(3, 3, 3);
    ad.set(P1, 1, 1, 0);
    ad.set(M1, 1, 0, 1);
    ad.set(M1, 2, 2, 0);
    ad.set(P1, 2, 0, 2);
    ad.set(P2, 0, 2, 1);
    ad.set(M2, 0, 1, 2);
    return ad;
  }

  /** @return ad tensor of 3-dimensional se(2) */
  public static Tensor se2() {
    Tensor ad = Array.zeros(3, 3, 3);
    ad.set(P1, 1, 0, 2);
    ad.set(M1, 1, 2, 0);
    ad.set(M1, 0, 2, 1);
    ad.set(P1, 0, 1, 2);
    return ad;
  }
}
