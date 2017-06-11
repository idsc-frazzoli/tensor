// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.IntegerQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import junit.framework.TestCase;

public class BinomialDistributionTest extends TestCase {
  public void testSimple() {
    DiscreteDistribution discreteDistribution = BinomialDistribution.of(10, RationalScalar.of(1, 7));
    PDF pdf = PDF.of(discreteDistribution);
    Scalar prob = RealScalar.ZERO;
    for (int c = 0; c <= 10; ++c)
      prob = prob.add(pdf.p_equals(RealScalar.of(c)));
    assertTrue(IntegerQ.of(prob));
    assertEquals(prob, RealScalar.ONE);
  }

  public void testValue() {
    DiscreteDistribution discreteDistribution = BinomialDistribution.of(10, RationalScalar.of(1, 2));
    PDF pdf = PDF.of(discreteDistribution);
    assertEquals(pdf.p_equals(RealScalar.of(0)), RationalScalar.of(1, 1024));
    assertEquals(pdf.p_equals(RealScalar.of(1)), RationalScalar.of(5, 512));
  }

  public void testValue2() {
    DiscreteDistribution discreteDistribution = BinomialDistribution.of(10, RationalScalar.of(1, 3));
    PDF pdf = PDF.of(discreteDistribution);
    assertEquals(pdf.p_equals(RealScalar.of(0)), RationalScalar.of(1024, 59049));
    // PDF[BinomialDistribution[10, 1/3], 1] == 5120/59049
    assertEquals(pdf.p_equals(RealScalar.of(1)), RationalScalar.of(5120, 59049));
    // PDF[BinomialDistribution[10, 1/3], 10] == 1/59049
    assertEquals(pdf.p_equals(RealScalar.of(10)), RationalScalar.of(1, 59049));
  }

  public void testValue3() {
    DiscreteDistribution discreteDistribution = BinomialDistribution.of(10, RationalScalar.of(1, 3));
    PDF pdf = PDF.of(discreteDistribution);
    assertEquals(pdf.p_equals(RealScalar.of(-1)), RealScalar.ZERO);
    assertEquals(pdf.p_equals(RealScalar.of(11)), RealScalar.ZERO);
    assertEquals(pdf.p_equals(RealScalar.of(12)), RealScalar.ZERO);
  }
}
