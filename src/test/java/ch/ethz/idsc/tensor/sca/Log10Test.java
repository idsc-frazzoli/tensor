// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.GaussScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import junit.framework.TestCase;

public class Log10Test extends TestCase {
  public void testOne() {
    Scalar scalar = Log10.of(RealScalar.ONE);
    assertTrue(Scalars.isZero(scalar));
  }

  public void testLog() {
    Scalar s = DoubleScalar.of(-3);
    Scalar r = Scalars.fromString("0.4771212547196624 + 1.3643763538418412* I");
    assertTrue(Chop._12.close(Log10.of(s), r));
    assertEquals(Log10.of(RealScalar.ZERO), DoubleScalar.NEGATIVE_INFINITY);
  }

  public void testComplex() {
    Scalar s = ComplexScalar.of(-2, 1);
    Scalar r = Scalars.fromString("0.3494850021680094 + 1.1630167557051545* I ");
    assertTrue(Chop._12.close(Log10.of(s), r));
  }

  public void testBase() {
    Scalar scalar = DoubleScalar.of(1412.123);
    assertTrue(Chop._08.close(Log10.of(scalar), Log.base(RealScalar.of(10)).apply(scalar)));
  }

  public void testFail() {
    Scalar scalar = GaussScalar.of(6, 7);
    try {
      Log10.of(scalar);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
