// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RealScalar;
import junit.framework.TestCase;

public class AbsTest extends TestCase {
  public void testReal() {
    assertEquals(Abs.FUNCTION.apply(RealScalar.of(+3)), RealScalar.of(3));
    assertEquals(Abs.FUNCTION.apply(RealScalar.of(-3)), RealScalar.of(3));
  }

  public void testComplex() {
    assertEquals(Abs.FUNCTION.apply(ComplexScalar.of(3, 4)), RealScalar.of(5));
    assertEquals(Abs.FUNCTION.apply(ComplexScalar.of(4, 3)), RealScalar.of(5));
  }
}
