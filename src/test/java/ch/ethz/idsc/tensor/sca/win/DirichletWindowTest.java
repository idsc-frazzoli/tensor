// code by jph
package ch.ethz.idsc.tensor.sca.win;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;
import junit.framework.TestCase;

public class DirichletWindowTest extends TestCase {
  public void testSimple() {
    ScalarUnaryOperator scalarUnaryOperator = DirichletWindow.FUNCTION;
    assertEquals(scalarUnaryOperator.apply(RealScalar.of(+0.1)), RealScalar.ONE);
    assertEquals(scalarUnaryOperator.apply(RealScalar.of(+0.6)), RealScalar.ZERO);
    assertEquals(scalarUnaryOperator.apply(RealScalar.of(-0.1)), RealScalar.ONE);
    assertEquals(scalarUnaryOperator.apply(RealScalar.of(-0.6)), RealScalar.ZERO);
  }

  public void testSemiExact() {
    Scalar scalar = DirichletWindow.FUNCTION.apply(RealScalar.of(0.5));
    assertTrue(Scalars.nonZero(scalar));
    assertTrue(ExactScalarQ.of(scalar));
  }

  public void testOf() {
    Tensor tensor = RandomVariate.of(NormalDistribution.standard(), 2, 3);
    assertEquals(DirichletWindow.of(tensor), tensor.map(DirichletWindow.FUNCTION));
  }

  public void testQuantityFail() {
    try {
      DirichletWindow.FUNCTION.apply(Quantity.of(0, "s"));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      DirichletWindow.FUNCTION.apply(Quantity.of(2, "s"));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
