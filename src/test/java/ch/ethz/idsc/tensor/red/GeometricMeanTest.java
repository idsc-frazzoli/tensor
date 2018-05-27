// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class GeometricMeanTest extends TestCase {
  public void testGeo1() {
    assertEquals(GeometricMean.of(Tensors.vectorDouble(4, 9)), RealScalar.of(6));
    Tensor a = GeometricMean.of(Tensors.vectorDouble(8, 27, 525));
    // 48.4029
    assertEquals(a, RealScalar.of(48.4028593807363));
  }

  public void testGeo2() {
    Tensor a = Tensors.matrixDouble(new double[][] { { 5, 10 }, { 2, 1 }, { 4, 3 }, { 12, 15 } });
    Tensor b = GeometricMean.of(a);
    // {4.68069, 4.60578}
    Tensor r = Tensors.fromString("{4.680694638641432, 4.605779351596907}");
    assertEquals(b, r);
  }

  public void testFailScalar() {
    try {
      GeometricMean.of(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailEmpty() {
    try {
      GeometricMean.of(Tensors.empty());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailMatrix() {
    Tensor mean = GeometricMean.of(HilbertMatrix.of(4));
    Tensor expected = Tensors.vector(0.4518010018049224, 0.3021375397356768, 0.2295748846661433, 0.18575057999133598);
    assertTrue(Chop._14.close(mean, expected));
  }
}
