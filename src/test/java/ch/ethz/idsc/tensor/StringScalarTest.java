// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.io.StringScalar;
import junit.framework.TestCase;

public class StringScalarTest extends TestCase {
  public void testStrings() {
    Tensor a = StringScalar.of("asd");
    Tensor b = StringScalar.of("x");
    Tensor d = Tensors.of(a, b, a, b);
    assertEquals(d.length(), 4);
    assertEquals(d.toString(), "{asd, x, asd, x}");
  }

  public void testHashCode() {
    assertEquals( //
        StringScalar.of("asd").hashCode(), //
        StringScalar.of("asd").hashCode());
  }

  public void testEquals() {
    assertTrue(StringScalar.of("3.14").equals(StringScalar.of("3.14")));
    assertFalse(StringScalar.of("3.14").equals(null));
    assertFalse(StringScalar.of("3.14").equals(StringScalar.of("3.141")));
    assertFalse(StringScalar.of("3.14").equals(DoubleScalar.of(3.14)));
  }

  public void testCurrentStandard() {
    String string = "{Hello, World}";
    assertTrue(string.equals(Tensors.fromString(string).toString()));
  }

  public void testFailOp() {
    try {
      StringScalar.of("asd").reciprocal();
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      StringScalar.of("asd").negate();
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      StringScalar.of("asd").number();
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      StringScalar.of("asd").multiply(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      StringScalar.of("asd").add(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testMultiplyFail() {
    try {
      ComplexScalar.I.multiply(StringScalar.of("asd"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
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
