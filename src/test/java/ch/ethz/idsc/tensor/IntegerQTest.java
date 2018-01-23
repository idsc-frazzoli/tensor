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

  public void testElseThrow() {
    IntegerQ.elseThrow(RealScalar.of(2));
    try {
      IntegerQ.elseThrow(RealScalar.of(.2));
    } catch (Exception exception) {
      // ---
    }
    try {
      IntegerQ.elseThrow(Tensors.vector(1, 2, 7));
    } catch (Exception exception) {
      // ---
    }
  }
}