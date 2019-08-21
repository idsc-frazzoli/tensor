// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import junit.framework.TestCase;

public class ToleranceTest extends TestCase {
  public void testSimple() {
    Scalar scalar = Scalars.fromString("1E-20");
    assertEquals(scalar, DoubleScalar.of(1E-20));
    assertTrue(Tolerance.CHOP.allZero(scalar));
    assertFalse(Tolerance.CHOP.allZero(Scalars.fromString("1E-8")));
  }
}
