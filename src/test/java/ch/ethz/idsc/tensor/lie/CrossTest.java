// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.GaussScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.UnitVector;
import ch.ethz.idsc.tensor.pdf.DiscreteUniformDistribution;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import junit.framework.TestCase;

public class CrossTest extends TestCase {
  static Tensor checkAB(Tensor a, Tensor b) {
    return Cross.of(a).dot(b);
  }

  public void testUnits() {
    Tensor v1 = UnitVector.of(3, 0);
    Tensor v2 = UnitVector.of(3, 1);
    Tensor v3 = UnitVector.of(3, 2);
    assertTrue(ExactScalarQ.all(Cross.of(v1, v2)));
    assertEquals(Cross.of(v1, v2), v3);
    assertTrue(ExactScalarQ.all(Cross.of(v2, v3)));
    assertEquals(Cross.of(v2, v3), v1);
    assertTrue(ExactScalarQ.all(Cross.of(v3, v1)));
    assertEquals(Cross.of(v3, v1), v2);
  }

  public void testNormal() {
    Distribution distribution = NormalDistribution.standard();
    for (int c = 0; c < 10; ++c) {
      Tensor a = RandomVariate.of(distribution, 3);
      Tensor b = RandomVariate.of(distribution, 3);
      assertEquals(Cross.of(a, b), checkAB(a, b));
    }
  }

  public void testUniform() {
    Distribution distribution = DiscreteUniformDistribution.of(-10, 10);
    for (int c = 0; c < 10; ++c) {
      Tensor a = RandomVariate.of(distribution, 3);
      Tensor b = RandomVariate.of(distribution, 3);
      assertEquals(Cross.of(a, b), checkAB(a, b));
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

  public void testFailLength2() {
    Tensor v1 = UnitVector.of(3, 0);
    Tensor v2 = UnitVector.of(2, 1);
    try {
      Cross.of(v1, v2);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Cross.of(v2, v1);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailLength4() {
    Tensor v1 = UnitVector.of(4, 0);
    Tensor v2 = UnitVector.of(3, 1);
    try {
      Cross.of(v1, v2);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Cross.of(v2, v1);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
