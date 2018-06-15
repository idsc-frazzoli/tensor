// code by jph
package ch.ethz.idsc.tensor;

import junit.framework.TestCase;

public class IntegerQTest extends TestCase {
  public void testPositive() {
    assertTrue(IntegerQ.of(Scalars.fromString("9/3")));
    assertTrue(IntegerQ.of(Scalars.fromString("-529384765923478653476593847659876237486")));
  }

  public void testNegative() {
    assertFalse(IntegerQ.of(Scalars.fromString("9.0")));
    assertFalse(IntegerQ.of(ComplexScalar.of(2, 3)));
  }

  public void testTensor() {
    assertFalse(IntegerQ.of(Tensors.empty()));
    assertFalse(IntegerQ.of(Tensors.vector(1)));
  }

  public void testRequire() {
    Scalar scalar = IntegerQ.require(RealScalar.of(2));
    assertEquals(scalar, RealScalar.of(2));
  }

  public void testRequireFail() {
    try {
      IntegerQ.require(RealScalar.of(.2));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
