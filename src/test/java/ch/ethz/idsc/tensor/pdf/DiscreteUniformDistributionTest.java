// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.pdf.DiscreteDistribution;
import ch.ethz.idsc.tensor.pdf.DiscreteUniformDistribution;
import ch.ethz.idsc.tensor.pdf.PDF;
import junit.framework.TestCase;

public class DiscreteUniformDistributionTest extends TestCase {
  public void testSimple() {
    DiscreteDistribution discreteDistribution = DiscreteUniformDistribution.of(RealScalar.of(3), RealScalar.of(10));
    PDF pdf = PDF.of(discreteDistribution);
    Scalar prob = pdf.p_equals(RealScalar.of(4));
    assertEquals(prob, RationalScalar.of(1, 10 - 3 + 1));
    assertEquals(pdf.p_equals(RealScalar.of(4)), pdf.p_equals(RealScalar.of(8)));
    assertEquals(pdf.p_equals(RealScalar.of(2)), RealScalar.ZERO);
    assertEquals(pdf.p_equals(RealScalar.of(2)), pdf.p_equals(RealScalar.of(11)));
    assertEquals(pdf.p_equals(RealScalar.of(10)), RationalScalar.of(1, 10 - 3 + 1));
    assertEquals(pdf.p_equals(RealScalar.of(11)), RealScalar.ZERO);
  }

  public void testLessThan() {
    DiscreteDistribution discreteDistribution = DiscreteUniformDistribution.of(RealScalar.of(3), RealScalar.of(10));
    PDF pdf = PDF.of(discreteDistribution);
    assertEquals(pdf.p_lessThan(RealScalar.of(2)), RationalScalar.of(0, 10 - 3 + 1));
    assertEquals(pdf.p_lessThan(RealScalar.of(3)), RationalScalar.of(0, 10 - 3 + 1));
    assertEquals(pdf.p_lessThan(RealScalar.of(4)), RationalScalar.of(1, 10 - 3 + 1));
    assertEquals(pdf.p_lessThan(RealScalar.of(5)), RationalScalar.of(2, 10 - 3 + 1));
    assertEquals(pdf.p_lessThan(RealScalar.of(10)), RationalScalar.of(7, 10 - 3 + 1));
    assertEquals(pdf.p_lessThan(RealScalar.of(11)), RationalScalar.of(8, 10 - 3 + 1));
  }

  public void testLessEquals() {
    DiscreteDistribution discreteDistribution = DiscreteUniformDistribution.of(RealScalar.of(3), RealScalar.of(10));
    PDF pdf = PDF.of(discreteDistribution);
    assertEquals(pdf.p_lessEquals(RealScalar.of(2)), RationalScalar.of(0, 10 - 3 + 1));
    assertEquals(pdf.p_lessEquals(RealScalar.of(3)), RationalScalar.of(1, 10 - 3 + 1));
    assertEquals(pdf.p_lessEquals(RealScalar.of(4)), RationalScalar.of(2, 10 - 3 + 1));
    assertEquals(pdf.p_lessEquals(RealScalar.of(5)), RationalScalar.of(3, 10 - 3 + 1));
    assertEquals(pdf.p_lessEquals(RealScalar.of(10)), RationalScalar.of(8, 10 - 3 + 1));
    assertEquals(pdf.p_lessEquals(RealScalar.of(11)), RationalScalar.of(8, 10 - 3 + 1));
  }
}
