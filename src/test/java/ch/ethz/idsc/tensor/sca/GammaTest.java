// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import junit.framework.TestCase;

public class GammaTest extends TestCase {
  public void testSimple() {
    Scalar g10 = Gamma.of(RealScalar.of(10)); // 362880
    assertEquals(g10, RealScalar.of(362880));
  }

  public void testFail() {
    try {
      Gamma.of(RealScalar.of(0));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Gamma.of(RealScalar.of(-1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Gamma.of(RealScalar.of(1.2));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
