// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.qty.QuantityTensor;
import junit.framework.TestCase;

public class SubdivideTest extends TestCase {
  public void testSubdivide() {
    Tensor t = Subdivide.of(RealScalar.of(10), RealScalar.of(15), 5);
    Tensor r = Tensors.vector(10, 11, 12, 13, 14, 15);
    assertEquals(t, r);
    assertEquals(t.toString(), r.toString());
  }

  public void testSubdivideRev() {
    Tensor t = Subdivide.of(RealScalar.of(-1), RealScalar.of(-4), 3);
    Tensor r = Tensors.vector(-1, -2, -3, -4);
    assertEquals(t, r);
    assertEquals(t.toString(), r.toString());
  }

  public void testSubdivideTensor() {
    Tensor t = Subdivide.of(Tensors.vector(10, 5), Tensors.vector(5, 15), 5);
    Tensor r = Tensors.fromString("{{10, 5}, {9, 7}, {8, 9}, {7, 11}, {6, 13}, {5, 15}}");
    assertEquals(t, r);
  }

  public void testSubdivideTensor2() {
    Tensor t = Subdivide.of(Tensors.vector(10, 5), Tensors.vector(5, 15), 4);
    Tensor r = Tensors.fromString("{{10, 5}, {35/4, 15/2}, {15/2, 10}, {25/4, 25/2}, {5, 15}}");
    assertEquals(t, r);
  }

  public void testRange() {
    assertEquals(Range.of(1, 11), Subdivide.of(1, 10, 9));
    assertEquals(Reverse.of(Range.of(1, 11)), Subdivide.of(10, 1, 9));
  }

  public void testQuantity() {
    Tensor t = Subdivide.of(Quantity.of(-20, "deg"), Quantity.of(20, "deg"), 4);
    assertEquals(t, QuantityTensor.of(Tensors.vector(-20, -10, 0, 10, 20), "deg"));
  }

  public void testLength() {
    int n = 5;
    Tensor t = Subdivide.of(2, 3, n);
    assertEquals(t.length(), n + 1);
  }

  public void testZeroFail() {
    try {
      Subdivide.of(RealScalar.of(-2), RealScalar.of(2), 0);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNegativeFail() {
    try {
      Subdivide.of(RealScalar.of(-2), RealScalar.of(2), -10);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNullFail() {
    try {
      Subdivide.of(RealScalar.of(2), null, 1);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Subdivide.of(null, RealScalar.of(2), 1);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
