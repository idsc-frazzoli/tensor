// code by jph
package ch.ethz.idsc.tensor.qty;

import java.io.IOException;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.io.Serialization;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.N;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;
import junit.framework.TestCase;

public class QuantityMagnitudeTest extends TestCase {
  public void testMagnitudeKgf() {
    Scalar scalar = QuantityMagnitude.SI().in("N").apply(Quantity.of(1, "kgf"));
    assertEquals(N.DOUBLE.apply(scalar).number().doubleValue(), 9.80665);
  }

  public void testInUnit() {
    Scalar scalar = QuantityMagnitude.SI().in(Unit.of("K*m^2")).apply(Quantity.of(2, "K*km^2"));
    assertEquals(scalar, RealScalar.of(2_000_000));
  }

  public void testInString() {
    Scalar scalar = QuantityMagnitude.SI().in("K*m^2*s").apply(Quantity.of(2, "K*km^2*s"));
    assertEquals(scalar, RealScalar.of(2_000_000));
  }

  public void testRad() throws ClassNotFoundException, IOException {
    QuantityMagnitude quantityMagnitude = Serialization.copy(QuantityMagnitude.SI());
    ScalarUnaryOperator scalarUnaryOperator = quantityMagnitude.in(Unit.of("rad"));
    assertTrue(Chop._12.close(scalarUnaryOperator.apply(Quantity.of(360, "deg")), RealScalar.of(Math.PI * 2)));
    Scalar scalar = scalarUnaryOperator.apply(RealScalar.of(2));
    assertEquals(scalar, RealScalar.of(2));
    assertTrue(ExactScalarQ.of(scalar));
  }

  public void testSingleton() {
    Scalar scalar = Quantity.of(3, "m^2*s");
    ScalarUnaryOperator suo = QuantityMagnitude.singleton("s*m^2");
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
    ScalarUnaryOperator scalarUnaryOperator = quantityMagnitude.in("N");
    Scalar result = scalarUnaryOperator.apply(scalar);
    assertEquals(result, RealScalar.ONE);
    assertTrue(ExactScalarQ.of(result));
  }

  public void testConversionMoWk() {
    Scalar scalar = Quantity.of(1, "mo");
    QuantityMagnitude quantityMagnitude = QuantityMagnitude.SI();
    ScalarUnaryOperator scalarUnaryOperator = quantityMagnitude.in("wk");
    Scalar result = scalarUnaryOperator.apply(scalar);
    assertEquals(result, RationalScalar.of(365, 84));
  }

  public void testHorsepower() {
    ScalarUnaryOperator scalarUnaryOperator = QuantityMagnitude.SI().in("W");
    Scalar ps = scalarUnaryOperator.apply(Quantity.of(1.0, "PS"));
    Scalar hp = scalarUnaryOperator.apply(Quantity.of(1.0, "hp"));
    assertEquals(ps, RealScalar.of(735.49875));
    Chop._12.requireClose(hp, RealScalar.of(745.6998715822702));
  }

  public void testHorsepowerKiloWatts() {
    ScalarUnaryOperator scalarUnaryOperator = QuantityMagnitude.SI().in("kW");
    Scalar ps = scalarUnaryOperator.apply(Quantity.of(1.0, "PS"));
    Scalar hp = scalarUnaryOperator.apply(Quantity.of(1.0, "hp"));
    Chop._14.requireClose(ps, RealScalar.of(0.73549875));
    Chop._14.requireClose(hp, RealScalar.of(0.7456998715822702));
  }

  public void testKiloponds() {
    ScalarUnaryOperator scalarUnaryOperator = QuantityMagnitude.SI().in("N");
    Scalar kp = scalarUnaryOperator.apply(Quantity.of(1.0, "kp"));
    assertEquals(kp, RealScalar.of(9.80665));
  }

  public void testMetricTons() {
    ScalarUnaryOperator scalarUnaryOperator = QuantityMagnitude.SI().in("t");
    Scalar _1kg_in_tons = scalarUnaryOperator.apply(Quantity.of(1000, "g"));
    ExactScalarQ.require(_1kg_in_tons);
    assertEquals(_1kg_in_tons, RationalScalar.of(1, 1000));
  }

  public void testFailConversion() {
    QuantityMagnitude quantityMagnitude = QuantityMagnitude.SI();
    Scalar quantity = Quantity.of(360, "kg");
    ScalarUnaryOperator scalarUnaryOperator = quantityMagnitude.in("m");
    try {
      scalarUnaryOperator.apply(quantity);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailInNull() {
    try {
      QuantityMagnitude.SI().in((Unit) null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailNull() {
    try {
      new QuantityMagnitude(null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
