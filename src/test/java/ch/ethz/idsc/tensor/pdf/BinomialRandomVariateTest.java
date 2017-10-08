// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.DeleteDuplicates;
import ch.ethz.idsc.tensor.sca.Sign;
import junit.framework.TestCase;

public class BinomialRandomVariateTest extends TestCase {
  public void testDivert() {
    assertEquals(BinomialDistribution.of(1200, DoubleScalar.of(.5)).getClass(), BinomialRandomVariate.class);
    assertEquals(BinomialDistribution.of(1200, RationalScalar.of(1, 2)).getClass(), BinomialDistribution.class);
    assertEquals(BinomialDistribution.of(12000, DoubleScalar.of(.1)).getClass(), BinomialRandomVariate.class);
    assertEquals(BinomialDistribution.of(120000, DoubleScalar.of(.0001)).getClass(), BinomialRandomVariate.class);
  }

  public void testRandom() {
    int n = 200;
    Distribution distribution = new BinomialRandomVariate(n, RealScalar.of(.4));
    Scalar value = RandomVariate.of(distribution);
    assertTrue(Sign.isPositive(value));
    assertTrue(Scalars.lessThan(value, RealScalar.of(n)));
    Scalar mean = Expectation.mean(distribution);
    assertEquals(mean, RealScalar.of(n * 0.4));
    Scalar var = Expectation.variance(distribution);
    assertEquals(var, RealScalar.of(n * 0.4 * 0.6));
  }

  public void testRandomVector() {
    int n = 200;
    Distribution distribution = new BinomialRandomVariate(n, RealScalar.of(.4));
    Tensor tensor = RandomVariate.of(distribution, 100);
    Tensor unique = DeleteDuplicates.of(tensor);
    assertTrue(5 < unique.length());
  }

  public void testCorner() {
    Distribution distribution1 = BinomialDistribution.of(10, RealScalar.ONE);
    Distribution distribution2 = new BinomialRandomVariate(10, RealScalar.ONE);
    Scalar s1 = RandomVariate.of(distribution1);
    Scalar s2 = RandomVariate.of(distribution2);
    assertEquals(s1, s2);
    assertEquals(s2, RealScalar.of(10));
  }

  public void testPDFFail() {
    try {
      PDF.of(BinomialDistribution.of(1200, DoubleScalar.of(.5)));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testCDFFail() {
    try {
      CDF.of(BinomialDistribution.of(1200, DoubleScalar.of(.5)));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
