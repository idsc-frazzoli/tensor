// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import junit.framework.TestCase;

public class ArcTanhTest extends TestCase {
  public void testReal() {
    Scalar s = ArcTanh.FUNCTION.apply(RealScalar.of(.5));
    assertEquals(s, RealScalar.of(0.5493061443340548));
  }

  public void testComplex() {
    Scalar s = ArcTanh.FUNCTION.apply(ComplexScalar.of(5, -9));
    // 0.0468657 - 1.48591 I
    assertEquals(s, ComplexScalar.of(0.04686573907359337, -1.4859071898107274));
  }
}
