// code by jph
package ch.ethz.idsc.tensor.sca;

import java.io.IOException;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.NumberQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.io.Serialization;
import ch.ethz.idsc.tensor.num.GaussScalar;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class LogTest extends TestCase {
  public void testOne() {
    Scalar scalar = Log.of(RealScalar.ONE);
    assertTrue(Scalars.isZero(scalar));
  }

  public void testLog() {
    Scalar scalar = DoubleScalar.of(-3);
    Chop._14.requireClose(Log.of(scalar), Scalars.fromString("1.0986122886681098+3.141592653589793*I"));
    assertEquals(Log.of(RealScalar.ZERO), DoubleScalar.NEGATIVE_INFINITY);
  }

  public void testComplex() {
    Scalar s = ComplexScalar.of(2, 3);
    Scalar r = Scalars.fromString("1.2824746787307681+0.982793723247329*I"); // mathematica
    Chop._14.requireClose(Log.of(s), r);
  }

  public void testRational() {
    Scalar rem = Scalars.fromString("1/10000000000");
    Scalar ratio = RealScalar.ONE.add(rem);
    assertEquals(Log.of(ratio).toString(), "" + Math.log1p(rem.number().doubleValue()));
  }

  public void testSerializable() throws ClassNotFoundException, IOException {
    ScalarUnaryOperator scalarUnaryOperator = Log.base(RealScalar.of(2));
    ScalarUnaryOperator copy = Serialization.copy(scalarUnaryOperator);
    Scalar scalar = copy.apply(RealScalar.of(4));
    assertEquals(scalar, RealScalar.of(2));
  }

  public void testBaseNumber() {
    ScalarUnaryOperator scalarUnaryOperator = Log.base(2);
    Scalar scalar = scalarUnaryOperator.apply(RealScalar.of(64));
    assertEquals(scalar, RealScalar.of(6));
  }

  public void testStrangeBase() {
    ScalarUnaryOperator scalarUnaryOperator = Log.base(0.1);
    Scalar scalar = scalarUnaryOperator.apply(RealScalar.of(3));
    // Mathematica ............................ -0.47712125471966243730
    Chop._12.requireClose(scalar, RealScalar.of(-0.47712125471966255));
  }

  public void testNegativeBase() {
    ScalarUnaryOperator scalarUnaryOperator = Log.base(-0.1);
    Scalar scalar = scalarUnaryOperator.apply(RealScalar.of(3));
    // Mathematica ................. -0.1667368328837891, -0.22749179210112070I
    Scalar result = ComplexScalar.of(-0.1667368328837892, -0.22749179210112075);
    Chop._12.requireClose(scalar, result);
  }

  public void testBaseZero() {
    ScalarUnaryOperator scalarUnaryOperator = Log.base(0);
    assertEquals(scalarUnaryOperator.apply(RealScalar.of(+4)), RealScalar.ZERO);
    assertEquals(scalarUnaryOperator.apply(RealScalar.of(-4)), RealScalar.ZERO);
    Scalar scalar = scalarUnaryOperator.apply(RealScalar.of(0));
    assertTrue(Double.isNaN(scalar.number().doubleValue()));
    assertFalse(NumberQ.of(scalar));
  }

  public void testBaseOneFail() {
    try {
      Log.base(1);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailQuantity() {
    Scalar scalar = Quantity.of(2, "m");
    try {
      Log.of(scalar);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFail() {
    Scalar scalar = GaussScalar.of(6, 7);
    try {
      Log.of(scalar);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
