// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.ExactNumberQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import junit.framework.TestCase;

public class NTest extends TestCase {
  public void testZero() {
    Scalar result = N.DOUBLE.of(RealScalar.ZERO);
    assertTrue(result instanceof DoubleScalar);
    assertEquals(result.toString(), "0.0");
  }

  public void testReal() {
    Scalar c = RationalScalar.of(3, 5);
    assertEquals(c.toString(), "3/5");
    assertEquals(N.DOUBLE.of(c).toString(), "" + (3 / 5.0));
  }

  public void testComplex() {
    Scalar c = ComplexScalar.of(3, 5);
    assertEquals(c.toString(), "3+5*I");
    assertEquals(N.DOUBLE.of(c).toString(), "3.0+5.0*I");
  }

  public void testDoubleId() {
    assertTrue(N.DOUBLE.of(RealScalar.of(3.14)) instanceof DoubleScalar);
    assertTrue(N.DECIMAL32.of(RealScalar.of(3.14)) instanceof DoubleScalar);
  }

  public void testContext() {
    Scalar d = N.DECIMAL128.of(RealScalar.of(3).reciprocal());
    assertEquals(d.toString(), "0.3333333333333333333333333333333333");
    Scalar d32 = N.DECIMAL32.apply(d);
    assertEquals(d32.toString(), "0.3333333");
  }

  public void testComplexContext() {
    Scalar s = ComplexScalar.of(3, 7).reciprocal();
    assertTrue(ExactNumberQ.of(s));
    Scalar d = N.DECIMAL128.of(s);
    // mathematica gives ...... 0.05172413793103448275862068965517241-0.12068965517241379310344827586206897 I
    assertEquals(d.toString(), "0.05172413793103448275862068965517241-0.1206896551724137931034482758620690*I");
  }
}
