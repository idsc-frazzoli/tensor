// code by jph
package ch.ethz.idsc.tensor.sca.win;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.pdf.UniformDistribution;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class WindowFunctionTest extends TestCase {
  public void testSimple() {
    for (WindowFunction windowFunction : WindowFunction.values()) {
      assertEquals(windowFunction.apply(RealScalar.of(-0.500001)), RealScalar.ZERO);
      assertEquals(windowFunction.apply(RealScalar.of(+0.500001)), RealScalar.ZERO);
      Chop._15.requireClose(windowFunction.apply(RealScalar.ZERO), RealScalar.ONE);
    }
  }

  public void testSymmetry() {
    Distribution distribution = UniformDistribution.of(-0.5, 0.5);
    for (WindowFunction windowFunction : WindowFunction.values())
      for (int count = 0; count < 10; ++count) {
        Scalar x = RandomVariate.of(distribution);
        Chop._15.requireClose(windowFunction.apply(x), windowFunction.apply(x.negate()));
      }
  }

  public void testInsideFail() {
    for (WindowFunction windowFunction : WindowFunction.values())
      try {
        windowFunction.apply(Quantity.of(0.1, "s"));
        fail();
      } catch (Exception exception) {
        // ---
      }
  }

  public void testOustideFail() {
    for (WindowFunction windowFunction : WindowFunction.values())
      try {
        windowFunction.apply(Quantity.of(1, "s"));
        fail();
      } catch (Exception exception) {
        // ---
      }
  }
}
