// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigInteger;

import junit.framework.TestCase;

public class RealScalarTest extends TestCase {
  public void testSign() {
    assertEquals(ZeroScalar.get().getSignInt(), 0);
    assertEquals(RealScalar.of(+5).getSignInt(), 1);
    assertEquals(RealScalar.of(-5).getSignInt(), -1);
    RealScalar r1 = RationalScalar.of(1927365481254298736L, 1927365481254298737L);
    RealScalar r2 = RationalScalar.of(1927365481254298741L, -1927365481254298739L);
    assertEquals(r1.getSignInt(), 1);
    assertEquals(r2.getSignInt(), -1);
  }

  public void testCompare() {
    {
      final Integer a = 0;
      final Integer b = 5;
      assertEquals(Integer.compare(0, b), ZeroScalar.get().compareTo(RealScalar.of(b)));
      assertEquals(a.compareTo(b), ZeroScalar.get().compareTo(RealScalar.of(b)));
    }
    {
      final Integer a = 0;
      final Integer b = -5;
      assertEquals(Integer.compare(0, b), ZeroScalar.get().compareTo(RealScalar.of(b)));
      assertEquals(a.compareTo(b), ZeroScalar.get().compareTo(RealScalar.of(b)));
    }
    {
      assertEquals(Double.compare(.3, .4), DoubleScalar.of(.3).compareTo(DoubleScalar.of(.4)));
      assertEquals(Double.compare(.3, -4e10), DoubleScalar.of(.3).compareTo(DoubleScalar.of(-4e10)));
    }
  }

  public void testCompareDouble() {
    assertEquals(Double.compare(.3, .4), DoubleScalar.of(.3).compareTo(DoubleScalar.of(.4)));
    assertEquals(Double.compare(.3, -4e10), DoubleScalar.of(.3).compareTo(DoubleScalar.of(-4e10)));
  }

  public void testCompareRational() {
    RealScalar r1 = RationalScalar.of(1927365481254298736L, 1927365481254298737L);
    RealScalar r2 = RationalScalar.of(1927365481254298741L, 1927365481254298739L);
    assertEquals(r1.compareTo(r2), -1);
    RealScalar d1 = DoubleScalar.of(r1.number().doubleValue());
    RealScalar d2 = DoubleScalar.of(r2.number().doubleValue());
    assertEquals(d1.compareTo(d2), 0);
  }

  public void testNumber() {
    assertEquals(ZeroScalar.get(), RealScalar.of(0));
    assertEquals(ZeroScalar.get(), RealScalar.of(0.));
    assertEquals(DoubleScalar.of(3.), RealScalar.of(3.));
    assertEquals(DoubleScalar.of(3.), RealScalar.of(3.f));
    assertEquals(RationalScalar.of(3, 1), RealScalar.of(3));
    assertEquals(RationalScalar.of(3, 1), RealScalar.of(3L));
    assertEquals(RationalScalar.of(1, 1), RealScalar.of(BigInteger.ONE));
  }
}
