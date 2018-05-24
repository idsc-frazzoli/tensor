// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DecimalScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.NumberQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.red.Nest;
import junit.framework.TestCase;

public class GammaTest extends TestCase {
  // recursive implementation fails for large values; used only as reference
  @SuppressWarnings("unused")
  private static Scalar recursive(Scalar scalar) {
    if (!NumberQ.of(scalar))
      return scalar;
    Scalar real = Real.FUNCTION.apply(scalar);
    if (Scalars.lessThan(real, Gamma.LO))
      return recursive(scalar.add(RealScalar.ONE)).divide(scalar);
    if (Scalars.lessThan(Gamma.HI, real)) {
      Scalar decrement = scalar.subtract(RealScalar.ONE);
      return recursive(decrement).multiply(decrement);
    }
    return Gamma.SERIES.apply(scalar.add(Gamma.NEGATIVE_THREE));
  }

  public void testFactorial() {
    for (int index = 0; index < 20; ++index)
      assertEquals(Gamma.of(RealScalar.of(index + 1)), Factorial.of(RealScalar.of(index)));
  }

  public void testGammaNumPos() {
    assertTrue(Chop._08.close(Gamma.of(RealScalar.of(3.0)), RealScalar.of(2)));
    assertTrue(Chop._08.close(Gamma.of(RealScalar.of(4.0)), RealScalar.of(6)));
    assertTrue(Chop._08.close(Gamma.of(RealScalar.of(5.0)), RealScalar.of(24)));
    assertTrue(Chop._10.close(Gamma.of(RealScalar.of(3.2)), RealScalar.of(2.4239654799353683)));
    assertTrue(Chop._10.close(Gamma.of(RealScalar.of(7.9)), RealScalar.of(4122.709484285446)));
  }

  public void testGammaNum() {
    assertTrue(Chop._08.close(Gamma.of(RealScalar.of(2.0)), RealScalar.of(1)));
    assertTrue(Chop._08.close(Gamma.of(RealScalar.of(1.0)), RealScalar.of(1)));
    assertTrue(Chop._08.close(Gamma.of(RealScalar.of(-1.2)), RealScalar.of(4.850957140522099)));
    assertTrue(Chop._08.close(Gamma.of(RealScalar.of(-3.8)), RealScalar.of(0.29963213450284565)));
    assertTrue(Chop._10.close(Gamma.of(RealScalar.of(-2.1)), RealScalar.of(-4.626098277572807)));
  }

  public void testLargeInt() {
    assertEquals(Gamma.of(RealScalar.of(-1000.2)), RealScalar.ZERO);
    assertEquals(Gamma.of(RealScalar.of(1000.2)), DoubleScalar.POSITIVE_INFINITY);
  }

  public void testLargeNegativeInteger() {
    try {
      Gamma.of(RealScalar.of(-100000000000l));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Gamma.of(DecimalScalar.of("-100000000000.0"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testLargeNegative() {
    assertEquals(Gamma.of(RealScalar.of(-23764528364522.345)), RealScalar.ZERO);
  }

  public void testLargePoistive() {
    assertEquals(Gamma.of(RealScalar.of(1000000000000L)), DoubleScalar.POSITIVE_INFINITY);
    assertEquals(Gamma.of(RealScalar.of(23764528364522.345)), DoubleScalar.POSITIVE_INFINITY);
  }

  public void testComplex1() {
    Scalar result = Gamma.of(ComplexScalar.of(1.1, 0.3));
    Scalar actual = ComplexScalar.of(0.886904759534451, -0.10608824042449128);
    assertTrue(Chop._10.close(result, actual));
  }

  public void testComplex2() {
    Scalar result = Gamma.of(ComplexScalar.of(0, 1));
    Scalar actual = ComplexScalar.of(-0.15494982830181073, -0.4980156681183565);
    assertTrue(Chop._09.close(result, actual));
  }

  public void testNest1() {
    Scalar seed = Scalars.fromString("-1.0894117647058823-0.07745098039215685*I");
    seed = Nest.of(Gamma.FUNCTION, seed, 3);
    assertTrue(Chop._50.allZero(seed));
  }

  public void testNest2() {
    Scalar seed = Scalars.fromString("-1.0486274509803923-0.028431372549019604*I");
    // seed = Nest.of(Gamma.FUNCTION, seed, 3);
    seed = Gamma.of(seed);
    // System.out.println(seed);
    seed = Gamma.of(seed);
    // System.out.println(seed);
    seed = Gamma.of(seed);
    // System.out.println(seed);
    assertFalse(NumberQ.of(seed));
    // assertTrue(Chop._50.allZero(seed));
  }

  public void testFail() {
    try {
      Gamma.of(RealScalar.of(0));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Gamma.of(RealScalar.of(-1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailNumeric() {
    try {
      Gamma.of(RealScalar.of(0.0));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Gamma.of(RealScalar.of(-1.0));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testGammaFail() {
    try {
      Gamma.of(Quantity.of(3, "m*s"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Gamma.of(Quantity.of(-2, "m")); // <- fails for the wrong reason
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Gamma.of(Quantity.of(-2.12, "m^2"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
