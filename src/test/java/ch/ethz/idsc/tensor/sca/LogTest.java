// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.GaussScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import junit.framework.TestCase;

public class LogTest extends TestCase {
  public void testOne() {
    Scalar scalar = Log.of(RealScalar.ONE);
    assertTrue(Scalars.isZero(scalar));
  }

  public void testLog() {
    Scalar s = DoubleScalar.of(-3);
    assertTrue(Statics.PRECISION.close(Log.of(s), Scalars.fromString("1.0986122886681098+3.141592653589793*I")));
    assertEquals(Log.of(RealScalar.ZERO), DoubleScalar.NEGATIVE_INFINITY);
  }

  public void testComplex() {
    Scalar s = ComplexScalar.of(2, 3);
    Scalar r = Scalars.fromString("1.2824746787307681+0.982793723247329*I"); // mathematica
    assertTrue(Statics.PRECISION.close(Log.of(s), r));
  }

  public void testRational() {
    Scalar one = RealScalar.ONE;
    Scalar rem = Scalars.fromString("1/10000000000");
    Scalar ratio = one.add(rem);
    assertEquals(Log.of(ratio).toString(), "" + Math.log1p(rem.number().doubleValue()));
  }

  public void testRange() {
    assertEquals(Math.log(Log.HI), Math.log1p(Log.HI - 1));
    assertEquals(Math.log(Log.LO), Math.log1p(Log.LO - 1));
  }

  public void testFail() {
    try {
      Log.of(GaussScalar.of(6, 7));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
