// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.VectorQ;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.red.KroneckerDelta;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Conjugate;
import ch.ethz.idsc.tensor.sca.N;
import ch.ethz.idsc.tensor.sca.Sqrt;
import junit.framework.TestCase;

public class QuaternionTest extends TestCase {
  public void testContruct() {
    Scalar c1 = ComplexScalar.of(1, 3);
    Scalar q1 = Quaternion.of(1, 3, 0, 0);
    // assertEquals(c1, q1);
    assertEquals(q1, q1);
    assertFalse(c1.equals(Quaternion.of(1, 3, 1, 0)));
  }

  public void testAdd() {
    Scalar q1 = Quaternion.of(1, 3, -2, 2);
    Scalar qr = Quaternion.of(9, 3, -1, 3);
    Scalar qb = Quaternion.of(10, 6, -3, 5);
    assertEquals(q1.add(qr), qb);
    assertEquals(qb.subtract(qr), q1);
    assertEquals(qr.add(q1), qb);
    assertEquals(qb.subtract(q1), qr);
  }

  public void testAbs() {
    Quaternion quaternion = Quaternion.of(2, 0, -6, 3);
    Scalar norm = quaternion.abs();
    assertEquals(norm, RealScalar.of(7));
    Quaternion divide = quaternion.divide(norm);
    assertEquals(divide.abs(), RealScalar.ONE);
  }

  public void testMultiply() {
    Scalar q1 = Quaternion.of(2, 0, -6, 3);
    Scalar q2 = Quaternion.of(1, 3, -2, 2);
    assertEquals(q1.multiply(q2), Quaternion.of(-16, 0, -1, 25));
    assertEquals(q2.multiply(q1), Quaternion.of(-16, 12, -19, -11));
    assertEquals(q1.divide(q1), Quaternion.ONE);
    assertEquals(q2.divide(q2), Quaternion.ONE);
  }

  public void testMultiplyComplex() {
    Scalar c1 = ComplexScalar.of(2, 3);
    Scalar q1 = Quaternion.of(7, 9, -6, 4);
    Scalar r1 = c1.multiply(q1);
    assertEquals(r1, Quaternion.of(-13, 39, 0, 26));
  }

  public void testReciprocal() {
    Scalar q1 = Quaternion.of(2, 0, -6, 3);
    Scalar q2 = Quaternion.of(1, 3, -2, 2);
    assertEquals(q1.reciprocal().multiply(q1), Quaternion.ONE);
    assertEquals(q2.reciprocal().multiply(q2), Quaternion.ONE);
  }

  public void testConjugate() {
    Scalar s = Conjugate.of(Quaternion.of(1, 2, 3, 4));
    assertEquals(s, Quaternion.of(1, -2, -3, -4));
  }

  public void testSqrt() {
    for (int index = 0; index < 100; ++index) {
      Tensor arg = RandomVariate.of(NormalDistribution.standard(), 4);
      Scalar q = Quaternion.of(arg.Get(0), arg.extract(1, 4));
      Scalar r = Sqrt.of(q);
      Scalar r2 = r.multiply(r);
      Chop._11.requireClose(r2, q);
    }
  }

  public void testSqrt0() {
    for (int index = 0; index < 100; ++index) {
      Tensor arg = RandomVariate.of(NormalDistribution.standard(), 4);
      Scalar q = Quaternion.of(RealScalar.ZERO, arg.extract(1, 4));
      Scalar r = Sqrt.of(q);
      Scalar r2 = r.multiply(r);
      Chop._11.requireClose(r2, q);
    }
  }

  public void testSome() {
    Scalar q1 = Quaternion.of(1, 23, 4, 5);
    Scalar q2 = Quaternion.of(1, 2, 4, 5);
    Scalar q3 = Quaternion.of(1, 23, 3, 5);
    Scalar q4 = Quaternion.of(1, 23, 4, 4);
    Tensor v = Tensors.of(q1, q2, q3, q4);
    int n = v.length();
    Tensor matrix = Tensors.matrix((i, j) -> KroneckerDelta.of(v.Get(i), v.Get(j)), n, n);
    assertEquals(matrix, IdentityMatrix.of(n));
  }

  private static Scalar _createQ(Tensor vec) {
    assertTrue(VectorQ.ofLength(vec, 4));
    return Quaternion.of(vec.Get(0), vec.Get(1), vec.Get(2), vec.Get(3));
  }

  public void testNormVsAbs() {
    Distribution distribution = NormalDistribution.standard();
    for (int index = 0; index < 100; ++index) {
      Tensor vec = RandomVariate.of(distribution, 4);
      Scalar q1 = _createQ(vec);
      Scalar nrm = Norm._2.ofVector(vec);
      Scalar abs = q1.abs();
      assertTrue(Chop._12.close(nrm, abs));
    }
  }

  public void testExactScalarQ() {
    Scalar q1 = Quaternion.of(1, 3, -2, 2);
    assertTrue(ExactScalarQ.of(q1));
    Scalar q2 = Quaternion.of(1, 3, -2., 2);
    assertFalse(ExactScalarQ.of(q2));
  }

  public void testN() {
    Scalar q1 = Quaternion.of(1, 3, -2, 2);
    assertTrue(ExactScalarQ.of(q1));
    Scalar n1 = N.DOUBLE.apply(q1);
    assertFalse(ExactScalarQ.of(n1));
    assertEquals(n1.toString(), "Q:1.0'{3.0, -2.0, 2.0}");
  }

  public void testN2() {
    Scalar q1 = Quaternion.of(1, 3, -2, 2);
    assertTrue(ExactScalarQ.of(q1));
    Scalar n1 = N.DECIMAL64.apply(q1);
    assertFalse(ExactScalarQ.of(n1));
    assertEquals(n1.toString(), "Q:1'{3, -2, 2}");
  }

  public void testExpLog() {
    Quaternion quaternion = Quaternion.of(.1, .3, .2, -.3);
    Quaternion exp = quaternion.exp();
    Quaternion log = exp.log();
    Chop._14.requireClose(quaternion, log);
  }

  public void testExpLogRandom() {
    Distribution distribution = NormalDistribution.of(0, 0.3);
    for (int count = 0; count < 30; ++count) {
      Quaternion quaternion = Quaternion.of(RandomVariate.of(distribution), RandomVariate.of(distribution, 3));
      Quaternion exp = quaternion.exp();
      Quaternion log = exp.log();
      Chop._12.requireClose(quaternion, log);
    }
  }

  public void testLogExpRandom() {
    Distribution distribution = NormalDistribution.of(0, 2.3);
    for (int count = 0; count < 30; ++count) {
      Quaternion quaternion = Quaternion.of(RandomVariate.of(distribution), RandomVariate.of(distribution, 3));
      Quaternion log = quaternion.log();
      Quaternion exp = log.exp();
      Chop._12.requireClose(quaternion, exp);
    }
  }

  public void testMatrixFail() {
    try {
      Quaternion.of(RealScalar.ONE, HilbertMatrix.of(3, 3));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      Quaternion.of(RealScalar.ONE, RealScalar.of(4));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNullFail() {
    try {
      Quaternion.of(null, Tensors.vector(1, 2, 3));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      Quaternion.of(RealScalar.ONE, Tensors.vector(1, 2, 3, 4));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      Quaternion.of(1, null, 2, 3);
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      Quaternion.of(RealScalar.ONE, null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
