// code by jph
package ch.ethz.idsc.tensor;

import junit.framework.TestCase;

public class ZeroScalarTest extends TestCase {
  public void testPositive() {
    assertTrue(ZeroScalar.get().isNonNegative());
  }

  public void testCompare() {
    assertEquals(ZeroScalar.get().compareTo(ZeroScalar.get()), 0);
  }
}
