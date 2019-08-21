// code by jph
package ch.ethz.idsc.tensor.num;

import ch.ethz.idsc.tensor.RealScalar;
import junit.framework.TestCase;

public class CoprimeQTest extends TestCase {
  public static void _check(int n1, int n2, boolean expected) {
    assertEquals(CoprimeQ.of(RealScalar.of(n1), RealScalar.of(n2)), expected);
    assertEquals(CoprimeQ.of(RealScalar.of(-n1), RealScalar.of(n2)), expected);
    assertEquals(CoprimeQ.of(RealScalar.of(n1), RealScalar.of(-n2)), expected);
    assertEquals(CoprimeQ.of(RealScalar.of(-n1), RealScalar.of(-n2)), expected);
  }

  public void testSimple() {
    _check(3, 9, false);
    _check(2, 3, true);
    _check(1, 7, true);
    _check(1, 6, true);
    _check(1, 1, true);
    _check(1, 0, true);
    _check(0, 3, true);
    _check(0, 0, false);
    _check(19, 17, true);
    _check(19 * 5, 17 * 5 * 8, false);
  }
  // public void testComplex() {
  // Scalar a = ComplexScalar.of(2, 1);
  // Scalar b = ComplexScalar.of(3, 1);
  // Scalar lcm = LCM.of(a, b);
  // Scalar prd = a.multiply(b);
  // System.out.println(lcm);
  // System.out.println(prd);
  // // assertTrue(CoprimeQ.of(ComplexScalar.of(2, 1), ComplexScalar.of(3, 1)));
  // }
}
