// code by jph
package ch.ethz.idsc.tensor;

import junit.framework.TestCase;

public class StringScalarTest extends TestCase {
  public void testStrings() {
    Tensor a = StringScalar.of("asd");
    Tensor b = StringScalar.of("x");
    Tensor d = Tensors.of(a, b, a, b);
    assertEquals(d.length(), 4);
    assertEquals(d.toString(), "{asd, x, asd, x}");
  }

  public void testFail() {
    try {
      StringScalar.of(null);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
