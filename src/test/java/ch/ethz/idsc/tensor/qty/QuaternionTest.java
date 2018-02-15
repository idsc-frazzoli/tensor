// code by jph
package ch.ethz.idsc.tensor.qty;

import java.io.IOException;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.VectorQ;
import ch.ethz.idsc.tensor.io.Serialization;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.red.KroneckerDelta;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.sca.AbsSquared;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Conjugate;
import ch.ethz.idsc.tensor.sca.N;
import ch.ethz.idsc.tensor.sca.Sqrt;
import junit.framework.TestCase;

public class QuaternionTest extends TestCase {
  public void testContruct() {
    Scalar c1 = ComplexScalar.of(1, 3);
    Scalar q1 = Quaternion.of(1, 3, 0, 0);
    assertEquals(c1, q1);
    assertEquals(q1, q1);
    assertFalse(c1.equals(Quaternion.of(1, 3, 1, 0)));
  }

  public void testAdd() {
    Scalar r1 = RealScalar.of(8);
    Scalar q1 = Quaternion.of(1, 3, -2, 2);
    Scalar qr = Quaternion.of(9, 3, -2, 2);
    assertEquals(r1.add(q1), qr);
    assertEquals(q1.add(r1), qr);
  }

  public void testAbs() {
    assertEquals(Quaternion.of(2, 0, -6, 3).abs(), RealScalar.of(7));
    assertEquals(AbsSquared.of(Quaternion.of(1, 3, -2, 2)), RealScalar.of(18));
  }

  public void testMultiply() {
    Scalar q1 = Quaternion.of(2, 0, -6, 3);
    Scalar q2 = Quaternion.of(1, 3, -2, 2);
    assertEquals(q1.multiply(q2), Quaternion.of(-16, 0, -1, 25));
    assertEquals(q2.multiply(q1), Quaternion.of(-16, 12, -19, -11));
    // Scalar q1q2 = q1.multiply(q2); // -16, 0, -1, 25
    assertEquals(q1.divide(q1), RealScalar.ONE);
    assertEquals(q2.divide(q2), RealScalar.ONE);
  }

  public void testMultiplyComplex() {
    Scalar c1 = ComplexScalar.of(2, 3);
    Scalar q1 = Quaternion.of(7, 9, -6, 4);
    @SuppressWarnings("unused")
    Scalar r1 = c1.multiply(q1);
    // System.out.println(r1);
  }

  public void testReciprocal() {
    Scalar q1 = Quaternion.of(2, 0, -6, 3);
    Scalar q2 = Quaternion.of(1, 3, -2, 2);
    assertEquals(q1.reciprocal().multiply(q1), RealScalar.ONE);
    assertEquals(q2.reciprocal().multiply(q2), RealScalar.ONE);
  }

  public void testConjugate() {
    Scalar s = Conjugate.of(Quaternion.of(1, 2, 3, 4));
    assertEquals(s, Quaternion.of(1, -2, -3, -4));
  }

  public void testSqrt() {
    for (int index = 0; index < 100; ++index) {
      Tensor arg = RandomVariate.of(NormalDistribution.standard(), 4);
      Scalar q = Quaternion.of(arg.Get(0), arg.Get(1), arg.Get(2), arg.Get(3));
      Scalar r = Sqrt.of(q);
      assertTrue(Chop._11.close(r.multiply(r), q));
    }
  }

  public void testSqrt0() {
    for (int index = 0; index < 100; ++index) {
      Tensor arg = RandomVariate.of(NormalDistribution.standard(), 4);
      Scalar q = Quaternion.of(RealScalar.ZERO, arg.Get(1), arg.Get(2), arg.Get(3));
      Scalar r = Sqrt.of(q);
      assertTrue(Chop._13.close(r.multiply(r), q));
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

  public void testPlusFail() {
    Scalar quaternion = Quaternion.of(1, 3, -2, 2);
    Scalar quantity = Quantity.of(1, "m");
    try {
      quaternion.add(quantity);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      quantity.add(quaternion);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
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
    assertEquals(n1.toString(), "Q:1.0'3.0'-2.0'2.0");
  }

  public void testN2() {
    Scalar q1 = Quaternion.of(1, 3, -2, 2);
    assertTrue(ExactScalarQ.of(q1));
    Scalar n1 = N.DECIMAL64.apply(q1);
    assertFalse(ExactScalarQ.of(n1));
    assertEquals(n1.toString(), "Q:1'3'-2'2");
  }

  public void testHashcode() {
    Tensor tensor = Tensors.of( //
        Quaternion.of(1, 3, -2, 2), //
        Quaternion.of(3, 1, -2, 2), //
        Quaternion.of(3, 2, -2, 1), //
        Quaternion.of(1, 3, 2, -2));
    long count = tensor.stream().mapToInt(Tensor::hashCode).distinct().count();
    assertEquals(count, tensor.length());
  }

  public void testSerializable() throws ClassNotFoundException, IOException {
    Scalar q1 = Quaternion.of(1, 3, -2, 2);
    Scalar q2 = Serialization.copy(q1);
    assertEquals(q1, q2);
  }

  public void testNumberFail() {
    Scalar quaternion = Quaternion.of(1, 3, -2, 2);
    try {
      quaternion.number();
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
