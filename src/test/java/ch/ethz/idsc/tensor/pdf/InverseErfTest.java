// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class InverseErfTest extends TestCase {
  public static final Chop CHOP_04 = Chop.below(1e-04);

  public void testSymmetry() {
    Scalar v1 = InverseErf.FUNCTION.apply(RealScalar.of(.3));
    assertTrue(CHOP_04.close(v1, RealScalar.of(0.2724627147267544)));
    Scalar v2 = InverseErf.FUNCTION.apply(RealScalar.of(-.3));
    assertTrue(CHOP_04.close(v2, RealScalar.of(-0.2724627147267544)));
  }

  public void testCorners() {
    assertEquals(InverseErf.of(RealScalar.of(-1)), DoubleScalar.NEGATIVE_INFINITY);
    assertEquals(InverseErf.of(RealScalar.of(+1)), DoubleScalar.POSITIVE_INFINITY);
  }

  public void testFail() {
    try {
      InverseErf.FUNCTION.apply(RealScalar.of(+1.3));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      InverseErf.FUNCTION.apply(RealScalar.of(-1.1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
