// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import junit.framework.TestCase;

public class TanhTest extends TestCase {
  public void testReal() {
    Scalar i = RealScalar.of(2);
    Scalar c = Tanh.function.apply(i);
    Scalar s = DoubleScalar.of(Math.tanh(2));
    assertEquals(c, Tanh.of(i));
    assertEquals(c, s);
  }

  public void testComplex() {
    Scalar c = Tanh.function.apply(ComplexScalar.of(2, 3.));
    // 0.965386 - 0.00988438 I
    Scalar s = Scalars.fromString("0.9653858790221332-0.009884375038322485*I");
    assertEquals(c, s);
  }
}
