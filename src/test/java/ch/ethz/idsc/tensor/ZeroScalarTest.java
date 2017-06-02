// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.io.Serialization;
import junit.framework.TestCase;

@Deprecated
public class ZeroScalarTest extends TestCase {
  public void testPositive() {
    assertTrue(RealScalar.ZERO.signInt() == 0);
  }

  public void testCompare() {
    assertEquals(RealScalar.ZERO.compareTo(RealScalar.ZERO), 0);
  }

  public void testSerializable() throws Exception {
    Scalar a = RealScalar.ZERO;
    Scalar b = Serialization.parse(Serialization.of(a));
    assertEquals(a, b);
    assertFalse(a == b);
  }
}
