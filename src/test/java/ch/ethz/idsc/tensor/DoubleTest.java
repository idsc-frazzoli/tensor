// code by jph
package ch.ethz.idsc.tensor;

import junit.framework.TestCase;

public class DoubleTest extends TestCase {
  public void testZeros() {
    final double d1 = 0.0;
    final double d2 = -0.0;
    assertFalse(Double.valueOf(d1).equals(Double.valueOf(d2)));
    assertFalse(d1 < d2);
    assertFalse(d2 < d1);
    assertTrue(d1 <= d2);
    assertTrue(d2 <= d1);
    assertTrue(d1 >= d2);
    assertTrue(d2 >= d1);
    // the tensor library overrides this logic (see DoubleScalar)
    assertTrue(Double.compare(d1, d2) == +1);
    assertTrue(Double.compare(d2, d1) == -1);
  }

  public void testReciprocal() {
    double e1 = 1e-300;
    double e2 = 1e-200;
    double l2 = 1e200;
    double r1 = e2 / e1;
    double rb = e2 * (1.0 / e1);
    double r2 = l2 / e1;
    assertTrue(Double.isFinite(r1));
    assertTrue(Double.isFinite(rb));
    assertFalse(Double.isFinite(r2));
  }
}
