// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import junit.framework.TestCase;

public class GeometricDistributionTest extends TestCase {
  public void testSimple() {
    PDF pdf = PDF.of(GeometricDistribution.of(RationalScalar.of(1, 3)));
    assertEquals(pdf.p_equals(RealScalar.ZERO), RationalScalar.of(1, 3));
    assertEquals(pdf.p_equals(RealScalar.of(1)), RationalScalar.of(2, 9));
    assertEquals(pdf.p_equals(RealScalar.of(2)), RationalScalar.of(4, 27));
    assertEquals(pdf.p_equals(RealScalar.of(1)), RationalScalar.of(1, 3).multiply(RationalScalar.of(2, 3)));
    assertEquals(pdf.p_equals(RealScalar.of(2)), RationalScalar.of(1, 3).multiply(RationalScalar.of(4, 9)));
  }

  public void testFailP() {
    try {
      GeometricDistribution.of(RealScalar.ZERO);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      GeometricDistribution.of(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
