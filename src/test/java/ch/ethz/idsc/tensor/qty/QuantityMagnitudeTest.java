// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;
import junit.framework.TestCase;

public class QuantityMagnitudeTest extends TestCase {
  public void testSimple() {
    Scalar scalar = QuantityMagnitude.SI().in(Unit.of("K*m^2")).apply(Quantity.of(2, "K*km^2"));
    assertEquals(scalar, RealScalar.of(2_000_000));
  }

  public void testSimpleString() {
    Scalar scalar = QuantityMagnitude.SI().in("K*m^2*s").apply(Quantity.of(2, "K*km^2*s"));
    assertEquals(scalar, RealScalar.of(2_000_000));
  }

  public void testRad() {
    QuantityMagnitude quantityMagnitude = QuantityMagnitude.SI();
    Scalar scalar = Quantity.of(360, "deg");
    Unit unit = Unit.of("rad");
    Scalar result = quantityMagnitude.in(unit).apply(scalar);
    assertTrue(Chop._12.close(result, RealScalar.of(Math.PI * 2)));
  }

  public void testSingleton() {
    Scalar scalar = Quantity.of(3, "m^2*s");
    ScalarUnaryOperator suo = QuantityMagnitude.singleton(Unit.of("s*m^2"));
    assertEquals(suo.apply(scalar), RealScalar.of(3));
  }

  public void testSingleton2() {
    Scalar scalar = RealScalar.of(3);
    ScalarUnaryOperator suo = QuantityMagnitude.singleton(Unit.ONE);
    assertEquals(suo.apply(scalar), RealScalar.of(3));
  }

  public void testConversionJ() {
    Scalar scalar = Quantity.of(6.241509125883258E9, "GeV");
    QuantityMagnitude quantityMagnitude = QuantityMagnitude.SI();
    ScalarUnaryOperator suo = quantityMagnitude.in("J");
    Scalar result = suo.apply(scalar);
    assertEquals(result, RealScalar.ONE);
  }

  public void testConversionPa() {
    Scalar scalar = Quantity.of(RationalScalar.of(8896443230521L, 1290320000).reciprocal(), "psi");
    QuantityMagnitude quantityMagnitude = QuantityMagnitude.SI();
    ScalarUnaryOperator suo = quantityMagnitude.in("Pa");
    Scalar result = suo.apply(scalar);
    assertEquals(result, RealScalar.ONE);
    assertTrue(ExactScalarQ.of(result));
  }

  public void testConversionN() {
    Scalar scalar = Quantity.of(RationalScalar.of(8896443230521L, 2000000000000L).reciprocal(), "lbf");
    QuantityMagnitude quantityMagnitude = QuantityMagnitude.SI();
    ScalarUnaryOperator suo = quantityMagnitude.in("N");
    Scalar result = suo.apply(scalar);
    assertEquals(result, RealScalar.ONE);
    assertTrue(ExactScalarQ.of(result));
  }

  public void testFailConversion() {
    QuantityMagnitude quantityMagnitude = QuantityMagnitude.SI();
    Scalar quantity = Quantity.of(360, "kg");
    ScalarUnaryOperator suo = quantityMagnitude.in("m");
    try {
      suo.apply(quantity);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailNull() {
    try {
      new QuantityMagnitude(null);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
