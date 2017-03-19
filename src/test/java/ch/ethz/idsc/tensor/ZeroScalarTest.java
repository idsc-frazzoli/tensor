// code by jph
package ch.ethz.idsc.tensor;

import junit.framework.TestCase;

public class ZeroScalarTest extends TestCase {
  public void testPositive() {
    assertFalse(ZeroScalar.get().isPositive());
  }
}
