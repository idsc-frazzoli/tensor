// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import junit.framework.TestCase;

public class SinhTest extends TestCase {
  public void testReal() {
    Scalar i = RealScalar.of(2);
    Scalar c = Sinh.FUNCTION.apply(i);
    Scalar s = DoubleScalar.of(Math.sinh(2));
    assertEquals(c, Sinh.of(i));
    assertEquals(c, s);
  }

  public void testComplex() {
    Scalar c = Sinh.FUNCTION.apply(ComplexScalar.of(2, 3.));
    // -3.59056 + 0.530921 I
    Scalar s = Scalars.fromString("-3.59056458998578+0.5309210862485197*I");
    assertEquals(c, s);
  }
}
