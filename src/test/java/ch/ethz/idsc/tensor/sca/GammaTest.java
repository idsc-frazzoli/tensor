// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import junit.framework.TestCase;

public class GammaTest extends TestCase {
  public void testFactorial() {
    for (int index = 0; index < 20; ++index)
      assertEquals(Gamma.of(RealScalar.of(index + 1)), Factorial.of(RealScalar.of(index)));
  }

  public void testValue() {
    assertTrue(Chop._10.close(Gamma.of(RealScalar.of(3.2)), RealScalar.of(2.4239654799353683)));
    assertTrue(Chop._10.close(Gamma.of(RealScalar.of(7.9)), RealScalar.of(4122.709484285446)));
  }

  public void testNegative() {
    assertTrue(Chop._10.close(Gamma.of(RealScalar.of(-2.1)), RealScalar.of(-4.626098277572807)));
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
}
