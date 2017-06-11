// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Map;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.pdf.BernoulliDistribution;
import ch.ethz.idsc.tensor.pdf.DiscreteDistribution;
import ch.ethz.idsc.tensor.pdf.PDF;
import ch.ethz.idsc.tensor.red.Tally;
import ch.ethz.idsc.tensor.sca.N;
import junit.framework.TestCase;

public class BernoulliDistributionTest extends TestCase {
  public void testEquals() {
    Scalar p = RationalScalar.of(1, 3);
    DiscreteDistribution discreteDistribution = BernoulliDistribution.of(p);
    PDF pdf = PDF.of(discreteDistribution);
    // PDF[BernoulliDistribution[1/3], 0] == 2/3
    assertEquals(pdf.p_equals(RealScalar.of(0)), RationalScalar.of(2, 3));
    assertEquals(pdf.p_equals(RealScalar.of(1)), RationalScalar.of(1, 3));
    assertEquals(pdf.p_equals(RealScalar.of(2)), RealScalar.ZERO);
  }

  public void testLessThan() {
    Scalar p = RationalScalar.of(1, 3);
    DiscreteDistribution discreteDistribution = BernoulliDistribution.of(p);
    PDF pdf = PDF.of(discreteDistribution);
    assertEquals(pdf.p_lessThan(RealScalar.of(0)), RationalScalar.ZERO);
    assertEquals(pdf.p_lessThan(RealScalar.of(1)), RationalScalar.of(2, 3));
    assertEquals(pdf.p_lessThan(RealScalar.of(2)), RealScalar.ONE);
  }

  public void testLessEquals() {
    Scalar p = RationalScalar.of(1, 3);
    DiscreteDistribution discreteDistribution = BernoulliDistribution.of(p);
    PDF pdf = PDF.of(discreteDistribution);
    assertEquals(pdf.p_lessEquals(RealScalar.of(0)), RationalScalar.of(2, 3));
    assertEquals(pdf.p_lessEquals(RealScalar.of(1)), RationalScalar.ONE);
    assertEquals(pdf.p_lessEquals(RealScalar.of(2)), RealScalar.ONE);
  }

  public void testSample() {
    final Scalar p = RationalScalar.of(1, 3);
    DiscreteDistribution discreteDistribution = BernoulliDistribution.of(p);
    PDF pdf = PDF.of(discreteDistribution);
    Tensor list = Tensors.empty();
    for (int c = 0; c < 2000; ++c)
      list.append(pdf.nextSample());
    Map<Tensor, Long> map = Tally.of(list);
    long v0 = map.get(RealScalar.ZERO);
    long v1 = map.get(RealScalar.ONE);
    Scalar ratio = RationalScalar.of(v1, v0 + v1);
    Scalar dev = N.of(ratio.subtract(p).abs());
    // System.out.println(dev);
    assertTrue(Scalars.lessThan(dev, RealScalar.of(.06)));
  }
}
