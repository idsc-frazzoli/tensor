// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import junit.framework.TestCase;

public class FactorialTest extends TestCase {
  public void testSimple() {
    assertEquals(Factorial.function.apply(RealScalar.of(0)), RealScalar.of(1));
    assertEquals(Factorial.function.apply(RealScalar.of(1)), RealScalar.of(1));
    assertEquals(Factorial.function.apply(RealScalar.of(2)), RealScalar.of(2));
    assertEquals(Factorial.function.apply(RealScalar.of(3)), RealScalar.of(6));
    assertEquals(Factorial.function.apply(RealScalar.of(4)), RealScalar.of(24));
  }

  public void testFail() {
    try {
      Factorial.function.apply(RealScalar.of(-1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Factorial.function.apply(RealScalar.of(1.2));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
