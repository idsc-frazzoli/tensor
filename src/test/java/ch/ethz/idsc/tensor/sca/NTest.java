// code by jph
package ch.ethz.idsc.tensor.sca;

import java.math.MathContext;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.ExactNumberQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import junit.framework.TestCase;

public class NTest extends TestCase {
  public void testZero() {
    Scalar result = N.of(RealScalar.ZERO);
    assertTrue(result instanceof DoubleScalar);
    assertEquals(result.toString(), "0.0");
  }

  public void testReal() {
    Scalar c = RationalScalar.of(3, 5);
    assertEquals(c.toString(), "3/5");
    assertEquals(N.of(c).toString(), "" + (3 / 5.0));
  }

  public void testComplex() {
    Scalar c = ComplexScalar.of(3, 5);
    assertEquals(c.toString(), "3+5*I");
    assertEquals(N.of(c).toString(), "3.0+5.0*I");
  }

  public void testContext() {
    Scalar d = N.of(RealScalar.of(3).reciprocal(), MathContext.DECIMAL128);
    assertEquals(d.toString(), "0.3333333333333333333333333333333333");
  }

  public void testComplexContext() {
    Scalar s = ComplexScalar.of(3, 7).reciprocal();
    assertTrue(ExactNumberQ.of(s));
    Scalar d = N.of(s, MathContext.DECIMAL128);
    // mathematica gives ...... 0.05172413793103448275862068965517241-0.12068965517241379310344827586206897 I
    assertEquals(d.toString(), "0.05172413793103448275862068965517241-0.1206896551724137931034482758620690*I");
  }
}
