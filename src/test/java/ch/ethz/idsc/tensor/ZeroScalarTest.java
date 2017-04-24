// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.io.Serialization;
import junit.framework.TestCase;

public class ZeroScalarTest extends TestCase {
  public void testPositive() {
    assertTrue(ZeroScalar.get().signInt() == 0);
  }

  public void testCompare() {
    assertEquals(ZeroScalar.get().compareTo(ZeroScalar.get()), 0);
  }

  public void testSerializable() throws Exception {
    Scalar a = ZeroScalar.get();
    Scalar b = Serialization.parse(Serialization.of(a));
    assertEquals(a, b);
    assertFalse(a == b);
  }
}
