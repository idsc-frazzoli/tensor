// code by jph
package ch.ethz.idsc.tensor;

import junit.framework.TestCase;

public class ExactNumberQTest extends TestCase {
  public void testSimple() {
    assertTrue(ExactNumberQ.of(RealScalar.ZERO));
    assertFalse(ExactNumberQ.of(DoubleScalar.of(0)));
    assertFalse(ExactNumberQ.of(DecimalScalar.of(0)));
    assertTrue(ExactNumberQ.of(ComplexScalar.of(3, 4)));
    assertFalse(ExactNumberQ.of(ComplexScalar.of(3., 4)));
  }

  public void testTensor() {
    assertFalse(ExactNumberQ.of(Tensors.empty()));
    assertFalse(ExactNumberQ.of(Tensors.vector(1)));
  }
}
