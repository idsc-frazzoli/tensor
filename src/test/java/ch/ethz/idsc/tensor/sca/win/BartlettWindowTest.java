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
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class BartlettWindowTest extends TestCase {
  public void testZero() {
    Scalar scalar = BartlettWindow.FUNCTION.apply(RealScalar.ZERO);
    assertEquals(scalar, RealScalar.ONE);
  }

  public void testExact() {
    Scalar scalar = BartlettWindow.FUNCTION.apply(RationalScalar.of(3, 3465));
    assertTrue(ExactScalarQ.of(scalar));
    assertEquals(scalar, RationalScalar.of(1153, 1155));
  }

  public void testContinuous() {
    Scalar scalar = BartlettWindow.FUNCTION.apply(RealScalar.of(.499999999));
    assertTrue(Chop._07.allZero(scalar));
  }

  public void testSemiExact() {
    Scalar scalar = BartlettWindow.FUNCTION.apply(RealScalar.of(0.5));
    assertTrue(Scalars.isZero(scalar));
    assertTrue(ExactScalarQ.of(scalar));
  }

  public void testOutside() {
    Scalar scalar = BartlettWindow.FUNCTION.apply(RealScalar.of(-0.52));
    assertEquals(scalar, RealScalar.ZERO);
  }

  public void testOf() {
    Tensor tensor = RandomVariate.of(NormalDistribution.standard(), 2, 3);
    assertEquals(BartlettWindow.of(tensor), tensor.map(BartlettWindow.FUNCTION));
  }
}
