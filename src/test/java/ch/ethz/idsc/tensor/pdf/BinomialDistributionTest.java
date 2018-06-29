// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Arrays;
import java.util.Map.Entry;
import java.util.NavigableMap;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.IntegerQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Range;
import ch.ethz.idsc.tensor.io.Serialization;
import ch.ethz.idsc.tensor.red.Tally;
import junit.framework.TestCase;

public class BinomialDistributionTest extends TestCase {
  public void testPdf() {
    Distribution distribution = BinomialDistribution.of(10, RationalScalar.of(1, 7));
    PDF pdf = PDF.of(distribution);
    Scalar prob = RealScalar.ZERO;
    for (int c = 0; c <= 10; ++c)
      prob = prob.add(pdf.at(RealScalar.of(c)));
    assertTrue(IntegerQ.of(prob));
    assertEquals(prob, RealScalar.ONE);
  }

  public void testValue() {
    Distribution distribution = BinomialDistribution.of(10, RationalScalar.of(1, 2));
    PDF pdf = PDF.of(distribution);
    assertEquals(pdf.at(RealScalar.of(0)), RationalScalar.of(1, 1024));
    assertEquals(pdf.at(RealScalar.of(0.5)), RationalScalar.ZERO);
    assertEquals(pdf.at(RealScalar.of(1)), RationalScalar.of(5, 512));
  }

  public void testValue2() {
    Distribution distribution = BinomialDistribution.of(RealScalar.of(10), RationalScalar.of(1, 3));
    PDF pdf = PDF.of(distribution);
    assertEquals(pdf.at(RealScalar.of(0)), RationalScalar.of(1024, 59049));
    // PDF[BinomialDistribution[10, 1/3], 1] == 5120/59049
    assertEquals(pdf.at(RealScalar.of(1)), RationalScalar.of(5120, 59049));
    // PDF[BinomialDistribution[10, 1/3], 10] == 1/59049
    assertEquals(pdf.at(RealScalar.of(10)), RationalScalar.of(1, 59049));
  }

  public void testValue3() {
    Distribution distribution = BinomialDistribution.of(10, RationalScalar.of(1, 3));
    PDF pdf = PDF.of(distribution);
    assertEquals(pdf.at(RealScalar.of(-1)), RealScalar.ZERO);
    assertEquals(pdf.at(RealScalar.of(11)), RealScalar.ZERO);
    assertEquals(pdf.at(RealScalar.of(12)), RealScalar.ZERO);
  }

  public void testMean() {
    Distribution distribution = BinomialDistribution.of(21, RationalScalar.of(7, 13));
    PDF pdf = PDF.of(distribution);
    Tensor sum = RealScalar.ZERO;
    for (Tensor x : Range.of(0, 22))
      sum = sum.add(x.multiply(pdf.at(x.Get())));
    assertEquals(Expectation.mean(distribution), sum);
  }

  public void testMean2() {
    Distribution distribution = BinomialDistribution.of(10, RationalScalar.of(3, 10));
    assertEquals(Expectation.mean(distribution), RealScalar.of(3));
  }

  public void testHigh() {
    Distribution distribution = BinomialDistribution.of(21, RationalScalar.of(7, 13));
    CDF cdf = CDF.of(distribution);
    cdf.p_lessThan(RealScalar.of(-1000000000));
    cdf.p_lessThan(RealScalar.of(+1000000000));
    cdf.p_lessEquals(RealScalar.of(-1000000000));
    cdf.p_lessEquals(RealScalar.of(+1000000000));
  }

  public void testCornerCase() {
    {
      Distribution distribution = BinomialDistribution.of(0, RationalScalar.ONE);
      assertEquals(RandomVariate.of(distribution), RealScalar.ZERO);
    }
    {
      Distribution distribution = BinomialDistribution.of(0, RationalScalar.ZERO);
      assertEquals(RandomVariate.of(distribution), RealScalar.ZERO);
    }
    {
      Distribution distribution = BinomialDistribution.of(0, RealScalar.of(.3));
      assertEquals(RandomVariate.of(distribution), RealScalar.ZERO);
    }
  }

  public void testBug1() {
    assertTrue(10 < Tally.of(RandomVariate.of(BinomialDistribution.of(20, RationalScalar.of(2, 3)), 10000)).size());
  }

  public void testBug2() {
    assertTrue(20 < Tally.of(RandomVariate.of(BinomialDistribution.of(100, RationalScalar.of(2, 3)), 10000)).size());
  }

  public void testBug3() {
    int size = Tally.of(RandomVariate.of(BinomialDistribution.of(1207, RationalScalar.of(2, 3)), 1000)).size();
    assertTrue(50 < size);
  }

  public void testSome() {
    for (int n = 10; n < 1200; n += 10) {
      EvaluatedDiscreteDistribution distribution = (EvaluatedDiscreteDistribution) BinomialDistribution.of(n, RealScalar.of(.333));
      double extreme = Math.nextDown(1.0);
      distribution.quantile(RealScalar.of(extreme));
      NavigableMap<Scalar, Scalar> navigableMap = distribution.inverse_cdf();
      @SuppressWarnings("unused")
      Entry<Scalar, Scalar> entry = navigableMap.lastEntry();
      // System.out.println(n + " " + entry);
    }
  }

  public void testBlub() {
    BinomialDistribution.of(1200, RealScalar.of(.1));
    BinomialDistribution.of(1200, RealScalar.of(.9));
  }

  public void testInRange() {
    assertEquals(BinomialDistribution.of(1000, DoubleScalar.of(.5)).getClass(), BinomialDistribution.class);
    assertEquals(BinomialDistribution.of(2000, DoubleScalar.of(.1)).getClass(), BinomialDistribution.class);
    assertEquals(BinomialDistribution.of(5000, DoubleScalar.of(.01)).getClass(), BinomialDistribution.class);
    assertEquals(BinomialDistribution.of(5000, DoubleScalar.of(.99)).getClass(), BinomialDistribution.class);
    assertEquals(BinomialDistribution.of(10000, DoubleScalar.of(.0)).getClass(), BinomialDistribution.class);
    assertEquals(BinomialDistribution.of(10000, DoubleScalar.of(1.0)).getClass(), BinomialDistribution.class);
  }

  public void testNextDownOne() {
    AbstractDiscreteDistribution distribution = //
        (AbstractDiscreteDistribution) BinomialDistribution.of(1000, DoubleScalar.of(.5));
    distribution.quantile(RealScalar.of(Math.nextDown(1.0)));
  }

  public void testNZero() {
    Distribution distribution = BinomialDistribution.of(0, RealScalar.ZERO);
    assertEquals(Expectation.mean(distribution), RealScalar.ZERO);
    assertEquals(Expectation.variance(distribution), RealScalar.ZERO);
    assertEquals(PDF.of(distribution).at(RealScalar.ZERO), RealScalar.ONE);
    assertEquals(RandomVariate.of(distribution), RealScalar.ZERO);
  }

  public void testInverseCDF() {
    InverseCDF inv = InverseCDF.of(BinomialDistribution.of(100, RationalScalar.of(2, 3)));
    Scalar x0 = inv.quantile(RealScalar.ZERO);
    Scalar x1 = inv.quantile(RealScalar.of(.5));
    Scalar x2 = inv.quantile(RealScalar.of(.8));
    Scalar x3 = inv.quantile(RealScalar.of(Math.nextDown(1.0)));
    assertEquals(x0, RealScalar.ZERO);
    assertEquals(x1, RealScalar.of(67));
    assertEquals(x2, RealScalar.of(71));
    assertEquals(x3, RealScalar.of(99));
  }

  public void testInverseCDF2() {
    InverseCDF inv = InverseCDF.of(BinomialDistribution.of(10, RationalScalar.of(1, 2)));
    Scalar x0 = inv.quantile(RealScalar.ZERO);
    Scalar x1 = inv.quantile(RealScalar.of(.5));
    Scalar x2 = inv.quantile(RealScalar.of(.8));
    Scalar x9 = inv.quantile(RealScalar.of(.9));
    Scalar x3 = inv.quantile(RealScalar.of(Math.nextDown(1.0)));
    assertEquals(x0, RealScalar.ZERO);
    assertEquals(x1, RealScalar.of(5));
    assertEquals(x2, RealScalar.of(6));
    assertEquals(x9, RealScalar.of(7));
    assertEquals(x3, RealScalar.of(10));
  }

  public void testInverseCDFOne() {
    InverseCDF inv = InverseCDF.of(BinomialDistribution.of(10, RationalScalar.of(2, 3)));
    Scalar last = inv.quantile(RealScalar.ONE);
    assertEquals(last, RealScalar.of(10)); // consistent with Mathematica
  }

  public void testHashEquals() throws Exception {
    Distribution d1 = BinomialDistribution.of(3, RationalScalar.of(1, 2));
    Distribution d2 = BinomialDistribution.of(3, RationalScalar.of(1, 2));
    Distribution d3 = BinomialDistribution.of(3, RationalScalar.of(1, 3));
    Distribution d4 = NormalDistribution.of(1, 2);
    // assertEquals(d1, d2);
    // assertEquals(d1.hashCode(), d2.hashCode());
    assertFalse(d1.equals(d3));
    assertFalse(d1.hashCode() == d3.hashCode());
    assertFalse(d1.equals(d4));
    assertFalse(d1.hashCode() == d4.hashCode());
    byte[] b1 = Serialization.of(d1);
    byte[] b2 = Serialization.of(d2);
    assertTrue(Arrays.equals(b1, b2));
  }

  public void testToString() {
    Distribution d1 = BinomialDistribution.of(3, RationalScalar.of(1, 2));
    String string = d1.toString();
    assertEquals(string, "BinomialDistribution[3, 1/2]");
  }

  public void testFailN() {
    try {
      BinomialDistribution.of(-1, RationalScalar.of(1, 3));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      BinomialDistribution.of(RealScalar.of(0.3), RationalScalar.of(1, 3));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailP() {
    BinomialDistribution.of(3, RealScalar.ZERO);
    try {
      BinomialDistribution.of(10, RationalScalar.of(-1, 3));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    BinomialDistribution.of(3, RealScalar.ONE);
    try {
      BinomialDistribution.of(10, RationalScalar.of(4, 3));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
