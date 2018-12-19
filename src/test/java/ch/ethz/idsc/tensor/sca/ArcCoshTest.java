// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import junit.framework.TestCase;

public class ArcCoshTest extends TestCase {
  public void testArcCosh() {
    Scalar s = ComplexScalar.of(5, -7);
    Scalar r = ArcCosh.of(s);
    // 2.84629 - 0.953732 I
    Scalar a = Scalars.fromString("2.8462888282083862-0.9537320301189031*I");
    assertEquals(a, r);
    assertEquals(a, ArcCosh.of(s));
  }
}
