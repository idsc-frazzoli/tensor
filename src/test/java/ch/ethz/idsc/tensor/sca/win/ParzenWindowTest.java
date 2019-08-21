// code by jph
package ch.ethz.idsc.tensor.sca.win;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class ParzenWindowTest extends TestCase {
  public void testSimple() {
    assertEquals(ParzenWindow.FUNCTION.apply(RationalScalar.of(1, 10)), RationalScalar.of(101, 125));
    assertEquals(ParzenWindow.FUNCTION.apply(RationalScalar.of(3, 10)), RationalScalar.of(16, 125));
  }

  public void testSemiExact() {
    Scalar scalar = ParzenWindow.FUNCTION.apply(RealScalar.of(0.5));
    assertTrue(Scalars.isZero(scalar));
    assertTrue(ExactScalarQ.of(scalar));
  }

  public void testOutside() {
    Scalar scalar = ParzenWindow.FUNCTION.apply(RealScalar.of(-0.52));
    assertEquals(scalar, RealScalar.ZERO);
    assertTrue(ExactScalarQ.of(scalar));
  }

  public void testOf() {
    Tensor tensor = RandomVariate.of(NormalDistribution.standard(), 2, 3);
    assertEquals(ParzenWindow.of(tensor), tensor.map(ParzenWindow.FUNCTION));
  }

  public void testQuantityFail() {
    try {
      ParzenWindow.FUNCTION.apply(Quantity.of(0, "s"));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      ParzenWindow.FUNCTION.apply(Quantity.of(2, "s"));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
