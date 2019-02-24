// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.red.Mean;
import ch.ethz.idsc.tensor.red.Variance;
import ch.ethz.idsc.tensor.sca.Power;
import junit.framework.TestCase;

public class PascalDistributionTest extends TestCase {
  public void testPDF() {
    Scalar p = RationalScalar.of(2, 3);
    Distribution distribution = PascalDistribution.of(5, p);
    PDF pdf = PDF.of(distribution);
    Scalar scalar = pdf.at(RealScalar.of(5));
    assertEquals(scalar, Power.of(p, 5));
  }

  public void testCDF() {
    Scalar p = RationalScalar.of(2, 3);
    Distribution distribution = PascalDistribution.of(5, p);
    CDF pdf = CDF.of(distribution);
    Scalar probability = pdf.p_lessEquals(RealScalar.of(14));
    assertEquals(probability, RationalScalar.of(4763648, 4782969));
    ExactScalarQ.require(probability);
  }

  public void testMean() {
    Distribution distribution = PascalDistribution.of(5, RationalScalar.of(2, 3));
    Scalar mean = Mean.of(distribution);
    Scalar var = Variance.of(distribution);
    assertEquals(mean, RationalScalar.of(15, 2));
    assertEquals(var, RationalScalar.of(15, 4));
    ExactScalarQ.require(mean);
    ExactScalarQ.require(var);
  }

  public void testVariance() {
    Distribution distribution = PascalDistribution.of(11, RationalScalar.of(5, 17));
    Scalar mean = Mean.of(distribution);
    Scalar var = Variance.of(distribution);
    assertEquals(mean, RationalScalar.of(187, 5));
    assertEquals(var, RationalScalar.of(2244, 25));
    ExactScalarQ.require(mean);
    ExactScalarQ.require(var);
  }

  public void testRandomVariate() {
    Scalar p = RationalScalar.of(3, 4);
    Distribution distribution = PascalDistribution.of(5, p);
    Tensor tensor = RandomVariate.of(distribution, 2000);
    Tensor mean = Mean.of(tensor);
    Scalar diff = Mean.of(distribution).subtract(mean);
    assertTrue(Scalars.lessThan(diff.abs(), RealScalar.of(0.1)));
    ExactScalarQ.require(diff);
  }
}
