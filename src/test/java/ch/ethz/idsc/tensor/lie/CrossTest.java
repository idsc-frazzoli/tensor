// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.ExactTensorQ;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.UnitVector;
import ch.ethz.idsc.tensor.num.GaussScalar;
import ch.ethz.idsc.tensor.pdf.DiscreteUniformDistribution;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import junit.framework.TestCase;

public class CrossTest extends TestCase {
  public static Tensor alt_skew3(Tensor a) {
    return LieAlgebras.so3().dot(a);
  }

  static void checkAB(Tensor a, Tensor b) {
    Tensor r1 = Cross.of(a, b);
    Tensor r2 = Cross.skew3(a).dot(b);
    Tensor r3 = alt_skew3(a).dot(b);
    assertEquals(r1, r2);
    assertEquals(r2, r3);
  }

  public void testUnits() {
    Tensor v1 = UnitVector.of(3, 0);
    Tensor v2 = UnitVector.of(3, 1);
    Tensor v3 = UnitVector.of(3, 2);
    assertTrue(ExactTensorQ.of(Cross.of(v1, v2)));
    assertEquals(Cross.of(v1, v2), v3);
    assertTrue(ExactTensorQ.of(Cross.of(v2, v3)));
    assertEquals(Cross.of(v2, v3), v1);
    assertTrue(ExactTensorQ.of(Cross.of(v3, v1)));
    assertEquals(Cross.of(v3, v1), v2);
  }

  public void testNormal() {
    Distribution distribution = NormalDistribution.standard();
    for (int c = 0; c < 10; ++c) {
      Tensor a = RandomVariate.of(distribution, 3);
      Tensor b = RandomVariate.of(distribution, 3);
      checkAB(a, b);
    }
  }

  public void testUniform() {
    Distribution distribution = DiscreteUniformDistribution.of(-10, 10);
    for (int c = 0; c < 10; ++c) {
      Tensor a = RandomVariate.of(distribution, 3);
      Tensor b = RandomVariate.of(distribution, 3);
      checkAB(a, b);
    }
  }

  public void testGauss() {
    Tensor v1 = Tensors.of( //
        GaussScalar.of(3, 7), //
        GaussScalar.of(4, 7), //
        GaussScalar.of(2, 7)); //
    Tensor v2 = Tensors.of( //
        GaussScalar.of(1, 7), //
        GaussScalar.of(5, 7), //
        GaussScalar.of(6, 7)); //
    Tensor tensor = Cross.of(v1, v2);
    Tensor v3 = Tensors.of( //
        GaussScalar.of(0, 7), //
        GaussScalar.of(5, 7), //
        GaussScalar.of(4, 7)); //
    assertEquals(tensor, v3);
  }

  public void testSkew3LengthFail() {
    try {
      Cross.skew3(Tensors.vector(1, 2, 3, 4));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailLength2() {
    Tensor v1 = UnitVector.of(3, 0);
    Tensor v2 = UnitVector.of(2, 1);
    try {
      Cross.of(v1, v2);
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      Cross.of(v2, v1);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailLength4() {
    Tensor v1 = UnitVector.of(4, 0);
    Tensor v2 = UnitVector.of(3, 1);
    try {
      Cross.of(v1, v2);
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      Cross.of(v2, v1);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
