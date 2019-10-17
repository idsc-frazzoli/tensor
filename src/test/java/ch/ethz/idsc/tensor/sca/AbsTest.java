// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class AbsTest extends TestCase {
  public void testReal() {
    assertEquals(Abs.FUNCTION.apply(RealScalar.of(+3)), RealScalar.of(3));
    assertEquals(Abs.FUNCTION.apply(RealScalar.of(-3)), RealScalar.of(3));
  }

  public void testComplex() {
    assertEquals(Abs.FUNCTION.apply(ComplexScalar.of(3, 4)), RealScalar.of(5));
    assertEquals(Abs.FUNCTION.apply(ComplexScalar.of(4, 3)), RealScalar.of(5));
  }

  public void testBetween() {
    Scalar a = Quantity.of(+9, "s");
    Scalar b = Quantity.of(+5, "s");
    Scalar c = Quantity.of(-2, "s");
    assertEquals(Abs.between(a, b), Quantity.of(4, "s"));
    assertEquals(Abs.between(b, a), Quantity.of(4, "s"));
    assertEquals(Abs.between(a, c), Quantity.of(11, "s"));
    assertEquals(Abs.between(c, a), Quantity.of(11, "s"));
    assertEquals(Abs.between(b, c), Quantity.of(7, "s"));
    assertEquals(Abs.between(c, b), Quantity.of(7, "s"));
  }
}
