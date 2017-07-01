// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigDecimal;

import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class DecimalScalarTest extends TestCase {
  public void testZero() {
    assertEquals(RealScalar.ZERO, DecimalScalar.of(BigDecimal.ZERO));
  }

  public void testSimple() {
    BigDecimal d = BigDecimal.ONE;
    Scalar sc1 = DecimalScalar.of(d);
    Scalar sc2 = sc1.add(sc1);
    Scalar sc2c = sc1.add(sc1);
    Scalar sc4 = sc2.multiply(sc2);
    Scalar r23 = RationalScalar.of(2, 3);
    assertEquals(sc2, sc2c);
    // System.out.println(sc4);
    Scalar sc4pr23 = sc4.add(r23);
    Scalar sc4mr23 = sc4.multiply(r23);
    assertTrue(sc4pr23 instanceof DecimalScalar);
    assertTrue(sc4mr23 instanceof DecimalScalar);
    // System.out.println(sc4mr23);
    // System.out.println(4 + 2 / 3.);
  }

  public void testDouble() {
    BigDecimal d = BigDecimal.ONE;
    Scalar sc1 = DecimalScalar.of(d);
    Scalar sc2 = sc1.add(sc1);
    Scalar sc4 = sc2.multiply(sc2);
    Scalar r23 = DoubleScalar.of(2 / 3.);
    Scalar sc4pr23 = sc4.add(r23);
    Scalar sc4mr23 = sc4.multiply(r23);
    assertTrue(sc4pr23 instanceof DoubleScalar);
    assertTrue(sc4mr23 instanceof DoubleScalar);
    // System.out.println(sc4pr23);
    // System.out.println(sc4mr23);
    // System.out.println(4 + 2 / 3.);
  }

  public void testInvert() {
    BigDecimal d = BigDecimal.ONE;
    Scalar sc1 = DecimalScalar.of(d);
    Scalar sc2 = sc1.add(sc1);
    Scalar sc3 = sc2.add(sc1);
    Scalar s13 = sc3.invert();
    Scalar r13 = RationalScalar.of(1, 3);
    Scalar d13 = DoubleScalar.of(1. / 3);
    assertEquals(r13, s13);
    assertEquals(s13, r13);
    assertEquals(d13, s13);
    assertEquals(s13, d13);
  }

  public void testDivide() {
    BigDecimal d = BigDecimal.ONE;
    Scalar sc1 = DecimalScalar.of(d);
    Scalar sc2 = sc1.add(sc1);
    Scalar sc3 = sc2.add(sc1);
    Scalar s23 = sc2.divide(sc3);
    Scalar r23 = RationalScalar.of(2, 3);
    Scalar d23 = DoubleScalar.of(Math.nextUp(2. / 3));
    assertEquals(Chop._12.of(r23.subtract(s23)), RealScalar.ZERO);
    assertEquals(Chop._12.of(s23.subtract(r23)), RealScalar.ZERO);
    // assertEquals(s23, r23);
    assertEquals(Chop._12.of(d23.subtract(s23)), RealScalar.ZERO);
    assertEquals(Chop._12.of(s23.subtract(d23)), RealScalar.ZERO);
    // assertEquals(d23, s23);
    // assertEquals(s23, d23);
  }

  public void testSqrt() {
    // Mathematica N[Sqrt[2], 50] gives
    // 1.4142135623730950488016887242096980785696718753769
    String expected = "1.41421356237309504880168872420969807856967187537";
    BigDecimal d = BigDecimal.ONE;
    Scalar sc1 = DecimalScalar.of(d);
    DecimalScalar sc2 = (DecimalScalar) sc1.add(sc1);
    Scalar root2 = sc2.sqrt();
    assertTrue(root2.toString().startsWith(expected));
  }

  public void testCompare1() {
    Scalar dec = DecimalScalar.of(.1);
    Scalar alt = DoubleScalar.of(.01);
    assertTrue(Scalars.lessThan(alt, dec));
    assertFalse(Scalars.lessThan(dec, alt));
  }

  public void testCompare2() {
    Scalar dec = DecimalScalar.of(.1);
    Scalar alt = RationalScalar.of(1, 100);
    assertTrue(Scalars.lessThan(alt, dec));
    assertFalse(Scalars.lessThan(dec, alt));
  }

  public void testCompare3() {
    assertTrue(Scalars.lessThan(DecimalScalar.of(-3), RealScalar.ZERO));
    assertFalse(Scalars.lessThan(DecimalScalar.of(3), RealScalar.ZERO));
    assertTrue(!Scalars.lessThan(RealScalar.ZERO, DecimalScalar.of(-3)));
    assertFalse(!Scalars.lessThan(RealScalar.ZERO, DecimalScalar.of(3)));
  }

  public void testEquals() {
    Scalar rs1 = RealScalar.ONE;
    Scalar ds1 = DecimalScalar.of(new BigDecimal("1.0000"));
    assertEquals(ds1, rs1);
    assertEquals(rs1, ds1);
  }
}
