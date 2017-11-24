// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.GaussScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Subdivide;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class ErfcTest extends TestCase {
  public void testCompare() {
    Tensor x = Subdivide.of(-0.6, 0.6, 50);
    assertTrue(Chop._07.close(Erfc.of(x), x.map(ErfcRestricted.FUNCTION)));
  }

  public void testLimits() {
    assertEquals(Erfc.FUNCTION.apply(DoubleScalar.POSITIVE_INFINITY), RealScalar.ZERO);
    assertEquals(Erfc.FUNCTION.apply(DoubleScalar.NEGATIVE_INFINITY), RealScalar.of(2));
  }

  public void testFail() {
    try {
      ErfcRestricted.FUNCTION.apply(GaussScalar.of(6, 7));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testComplexFail() {
    Scalar scalar = ComplexScalar.of(1.2, 3.4);
    try {
      Erfc.FUNCTION.apply(scalar);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
