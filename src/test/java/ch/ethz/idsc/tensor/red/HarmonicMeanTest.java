// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class HarmonicMeanTest extends TestCase {
  public void testGeo1() {
    Tensor a = HarmonicMean.of(Tensors.vector(8, 27, 525));
    assertEquals(a, RationalScalar.of(113400, 6197));
    Tensor b = HarmonicMean.of(Tensors.vector(8, -27, 3));
    assertEquals(b, RationalScalar.of(648, 91));
  }

  public void testEmpty() {
    try {
      HarmonicMean.of(Tensors.empty());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testZero() {
    try {
      HarmonicMean.of(Tensors.vector(3, 0, 2));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
