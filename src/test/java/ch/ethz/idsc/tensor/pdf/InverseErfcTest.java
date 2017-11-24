// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import junit.framework.TestCase;

public class InverseErfcTest extends TestCase {
  public void testSimple() {
    InverseCDF icdf = (InverseCDF) NormalDistribution.of(2, 8);
    Scalar phi = icdf.quantile(RealScalar.of(0.5));
    assertEquals(phi, RealScalar.of(2));
  }

  public void testFail() {
    InverseCDF icdf = (InverseCDF) NormalDistribution.of(2, 8);
    try {
      icdf.quantile(RealScalar.of(1.5));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
