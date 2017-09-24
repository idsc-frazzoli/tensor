// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class QuantityMultiplicativeTest extends TestCase {
  public void testMultiplyScalar() {
    Scalar qs1 = Quantity.of(3, "m");
    Scalar qs2 = Quantity.of(4, "m");
    Scalar qs3 = Quantity.of(5, "m");
    Tensor vec = Tensors.of(qs1, qs2, qs3);
    Tensor sca = vec.multiply(RealScalar.of(3));
    assertEquals(sca.toString(), "{9[m], 12[m], 15[m]}");
  }

  public void testMultiply() {
    Scalar qs1 = Quantity.of(3, "m");
    Scalar qs2 = Quantity.of(-2, "s");
    assertEquals(qs1.multiply(qs2).toString(), "-6[m*s]");
  }

  public void testDivide() {
    Scalar qs1 = Quantity.of(12, "m");
    Scalar qs2 = Quantity.of(4, "m");
    Scalar qs3 = Quantity.of(3, "m^0");
    assertEquals(qs1.divide(qs2), qs3);
    assertTrue(qs3 instanceof RationalScalar);
  }

  public void testReciprocal() {
    Scalar qs1 = Quantity.of(4, "m");
    assertEquals(qs1.reciprocal().toString(), "1/4[m^-1]");
  }

  private static void _checkDivision(Scalar q1, Scalar q2) {
    assertEquals(q1.divide(q2), q2.under(q1));
    assertEquals(q2.divide(q1), q1.under(q2));
  }

  public void testDivisionUnder() {
    _checkDivision(Quantity.of(1, "m"), Quantity.of(2, "s"));
    _checkDivision(Quantity.of(1, "m"), DoubleScalar.of(2.0));
    _checkDivision(Quantity.of(1, "m"), RealScalar.of(2));
    double eps = Math.nextUp(0.0);
    _checkDivision(Quantity.of(eps, "m"), Quantity.of(2, "s"));
    _checkDivision(Quantity.of(eps, "m"), DoubleScalar.of(2.0));
    _checkDivision(Quantity.of(eps, "m"), RealScalar.of(2));
    // ---
    _checkDivision(Quantity.of(1, "m"), Quantity.of(eps, "s"));
    _checkDivision(Quantity.of(1, "m"), DoubleScalar.of(eps));
    _checkDivision(Quantity.of(1, "m"), RealScalar.of(eps));
    // ---
    _checkDivision(Quantity.of(0, "m"), Quantity.of(eps, "s"));
    _checkDivision(Quantity.of(0, "m"), DoubleScalar.of(eps));
    _checkDivision(Quantity.of(0.0, "m"), Quantity.of(eps, "s"));
    _checkDivision(Quantity.of(0.0, "m"), DoubleScalar.of(eps));
    // ---
    _checkDivision(Quantity.of(eps, "m"), Quantity.of(eps, "s"));
    _checkDivision(Quantity.of(eps, "m"), DoubleScalar.of(eps));
  }

  public void testDivision1() {
    Scalar quantity = Quantity.of(0, "m");
    Scalar eps = DoubleScalar.of(Math.nextUp(0.0));
    assertTrue(Scalars.isZero(quantity.divide(eps)));
  }

  public void testDivision2() {
    Scalar zero = DoubleScalar.of(0.0);
    Scalar eps = Quantity.of(Math.nextUp(0.0), "m");
    assertTrue(Scalars.isZero(zero.divide(eps)));
  }

  public void testDivision3() {
    Scalar s1 = ComplexScalar.of(1, 2);
    Scalar s2 = Quantity.of(3, "m");
    Scalar sds = s1.divide(s2);
    // System.out.println(sds);
    assertEquals(sds, s2.under(s1));
  }
}
