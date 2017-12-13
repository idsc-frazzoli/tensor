// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class ComplexScalarTest extends TestCase {
  public void testConstructFail() {
    try {
      ComplexScalar.of(RealScalar.ONE, ComplexScalar.I);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      ComplexScalar.of(ComplexScalar.I, RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNullFail() {
    try {
      ComplexScalar.of(RealScalar.ONE, null);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      ComplexScalar.of(null, RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      ComplexScalar.of(null, RealScalar.ZERO);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testPolarFail() {
    try {
      ComplexScalar.fromPolar(RealScalar.ONE, ComplexScalar.I);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      ComplexScalar.fromPolar(ComplexScalar.I, RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      ComplexScalar.fromPolar(ComplexScalar.I, ComplexScalar.I);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testPolarQuantityFail() {
    try {
      ComplexScalar.fromPolar(RealScalar.ONE, Quantity.of(1.3, "m"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testPolar() {
    assertTrue(ComplexScalar.fromPolar(1, 3) instanceof ComplexScalar);
    assertTrue(ComplexScalar.fromPolar(1, 0) instanceof RealScalar);
  }

  public void testPolarNumberFail() {
    try {
      ComplexScalar.fromPolar(-1, 3);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testUnitFail() {
    try {
      ComplexScalar.unit(ComplexScalar.of(-1, 3));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      ComplexScalar.unit(Quantity.of(3, "s"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
