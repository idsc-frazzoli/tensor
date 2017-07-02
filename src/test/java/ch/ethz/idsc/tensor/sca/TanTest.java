// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import junit.framework.TestCase;

public class TanTest extends TestCase {
  public void testReal() {
    Scalar i = RealScalar.of(2);
    Scalar c = Tan.FUNCTION.apply(i);
    Scalar s = DoubleScalar.of(Math.tan(2));
    assertEquals(c, Tan.of(i));
    assertEquals(c, s);
  }

  public void testComplex() {
    Scalar c = Tan.FUNCTION.apply(ComplexScalar.of(2, 3.));
    // -0.00376403 + 1.00324 I
    Scalar s = Scalars.fromString("-0.0037640256415041873+1.00323862735361*I");
    assertEquals(c, s);
  }
}
