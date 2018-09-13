// code by jph
package ch.ethz.idsc.tensor.sca;

import java.io.IOException;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.GaussScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.io.Serialization;
import junit.framework.TestCase;

public class LogTest extends TestCase {
  public void testOne() {
    Scalar scalar = Log.of(RealScalar.ONE);
    assertTrue(Scalars.isZero(scalar));
  }

  public void testLog() {
    Scalar scalar = DoubleScalar.of(-3);
    assertTrue(Statics.PRECISION.close(Log.of(scalar), Scalars.fromString("1.0986122886681098+3.141592653589793*I")));
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

  public void testSerializable() throws ClassNotFoundException, IOException {
    ScalarUnaryOperator scalarUnaryOperator = Log.base(RealScalar.of(2));
    ScalarUnaryOperator copy = Serialization.copy(scalarUnaryOperator);
    Scalar scalar = copy.apply(RealScalar.of(4));
    assertEquals(scalar, RealScalar.of(2));
  }

  public void testFail() {
    Scalar scalar = GaussScalar.of(6, 7);
    try {
      Log.of(scalar);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
