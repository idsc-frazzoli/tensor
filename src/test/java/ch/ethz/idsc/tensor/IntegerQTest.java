// code by jph
package ch.ethz.idsc.tensor;

import junit.framework.TestCase;

public class IntegerQTest extends TestCase {
  public void testSimple() {
    assertTrue(IntegerQ.of(Scalars.fromString("9/3")));
    assertFalse(IntegerQ.of(Scalars.fromString("9.0")));
    assertFalse(IntegerQ.of(Tensors.empty()));
  }
}
