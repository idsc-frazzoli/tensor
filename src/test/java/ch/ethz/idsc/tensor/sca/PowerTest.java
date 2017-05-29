// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.ZeroScalar;
import junit.framework.TestCase;

public class PowerTest extends TestCase {
  public void testSimple() {
    assertEquals(Power.of(2, 4), RealScalar.of(16));
    assertEquals(Power.of(-4, 5), RealScalar.of(-1024));
  }

  public void testZero() {
    assertEquals(Power.of(+2, 0), RealScalar.ONE);
    assertEquals(Power.of(+1, 0), RealScalar.ONE);
    assertEquals(Power.of(+0, 0), RealScalar.ONE);
    assertEquals(Power.of(-1, 0), RealScalar.ONE);
    assertEquals(Power.of(-2, 0), RealScalar.ONE);
  }

  public void testSqrt() {
    assertEquals(Power.of(2, .5), Sqrt.of(RealScalar.of(2)));
    assertEquals(Power.of(14, .5), Sqrt.of(RealScalar.of(14)));
  }

  public void testPowerZero() {
    assertEquals(Power.of(0, +0), RealScalar.ONE);
    assertEquals(Power.of(0, +1), ZeroScalar.get());
    assertEquals(Power.of(0, +2), ZeroScalar.get());
  }

  public void testPowerZeroFail() {
    try {
      Power.of(0, -2);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testPowerZeroComplex() {
    assertEquals(Power.of(ZeroScalar.get(), Scalars.fromString("0.1+3*I")), ZeroScalar.get());
    assertEquals(Power.of(ZeroScalar.get(), Scalars.fromString("0.1-3*I/2")), ZeroScalar.get());
  }

  public void testPowerZeroComplexFail() {
    try {
      Power.of(ZeroScalar.get(), ComplexScalar.I);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Power.of(ZeroScalar.get(), Scalars.fromString("-0.1+3*I"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNegative() {
    assertEquals(Power.of(2, -4), RealScalar.of(16).invert());
    assertEquals(Power.of(-4, -5), RealScalar.of(-1024).invert());
  }

  public void testComplex() {
    Scalar a = ComplexScalar.of(2, +3);
    Scalar b = ComplexScalar.of(4, -2);
    Scalar c = Power.of(a, b);
    // Mathematica: 245.099 + 1181.35 I
    assertEquals(c, Scalars.fromString("245.09854196562927+1181.3509801973048*I"));
  }

  public void testFunction() {
    assertEquals(RealScalar.of(7).map(Power.function(.5)), Sqrt.of(RealScalar.of(7)));
    assertEquals(Power.function(.5).apply(RealScalar.of(7)), Sqrt.of(RealScalar.of(7)));
  }
}
