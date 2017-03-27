// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.Scalar;
import junit.framework.TestCase;

public class NTest extends TestCase {
  public void testComplex() {
    Scalar c = ComplexScalar.of(3, 5);
    assertEquals(c.toString(), "3+5*I");
    assertEquals(N.of(c).toString(), "3.0+5.0*I");
  }
}
