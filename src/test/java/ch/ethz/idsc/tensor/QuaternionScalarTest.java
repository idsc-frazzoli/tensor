// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.sca.AbsSquared;
import junit.framework.TestCase;

public class QuaternionScalarTest extends TestCase {
  public void testMultiply() {
    Scalar q1 = QuaternionScalar.of(2, 0, -6, 3);
    Scalar q2 = QuaternionScalar.of(1, 3, -2, 2);
    // Scalar q1q2 = q1.multiply(q2); // -16, 0, -1, 25
    assertEquals(AbsSquared.of(q2), RealScalar.of(18));
    assertEquals(q1.reciprocal().multiply(q1), RealScalar.ONE);
    assertEquals(q2.reciprocal().multiply(q2), RealScalar.ONE);
    assertEquals(q1.divide(q1), RealScalar.ONE);
    assertEquals(q2.divide(q2), RealScalar.ONE);
  }
}
