// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import junit.framework.TestCase;

public class HarmonicMeanTest extends TestCase {
  public void testGeo1() {
    Tensor a = HarmonicMean.ofVector(Tensors.vector(8, 27, 525));
    assertEquals(a, RationalScalar.of(113400, 6197));
    Tensor b = HarmonicMean.ofVector(Tensors.vector(8, -27, 3));
    assertEquals(b, RationalScalar.of(648, 91));
  }

  public void testEmpty() {
    try {
      HarmonicMean.ofVector(Tensors.empty());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testZero() {
    try {
      HarmonicMean.ofVector(Tensors.vector(3, 0, 2));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testScalarFail() {
    try {
      HarmonicMean.ofVector(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testMatrixFail() {
    try {
      HarmonicMean.ofVector(HilbertMatrix.of(4));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
