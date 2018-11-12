//code by clruch
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.red.Mean;
import junit.framework.TestCase;

public class TrapezoidalDistributionTest extends TestCase {
  private static final Random random = new Random();

  public void testPositive() {
    Scalar a = RationalScalar.of(random.nextInt(100), 1);
    Scalar b = a.add(RealScalar.of(random.nextDouble() * 10));
    Scalar c = b.add(RealScalar.of(random.nextDouble() * 10));
    Scalar d = c.add(RealScalar.of(random.nextDouble() * 10));
    Distribution distribution = TrapezoidalDistribution.of(a, b, c, d);
    for (int i = 0; i < 100; ++i) {
      Scalar s = RandomVariate.of(distribution);
      assertTrue(Scalars.lessEquals(RealScalar.ZERO, s));
    }
  }

  public void testPDF() {
    Scalar a = RationalScalar.of(1, 1);
    Scalar b = RationalScalar.of(2, 1);
    Scalar c = RationalScalar.of(3, 1);
    Scalar d = RationalScalar.of(4, 1);
    Distribution distribution = TrapezoidalDistribution.of(a, b, c, d);
    {
      Scalar actual = PDF.of(distribution).at(RealScalar.of(3));
      Scalar expected = RealScalar.of(2).divide(d.add(c).subtract(a).subtract(b));
      assertEquals(expected, actual);
    }
    {
      Scalar actual = PDF.of(distribution).at(RealScalar.of(-3));
      assertEquals(actual, RealScalar.ZERO);
    }
  }

  public void testCDFPositive() {
    Scalar a = RationalScalar.of(1, 1);
    Scalar b = RationalScalar.of(2, 1);
    Scalar c = RationalScalar.of(3, 1);
    Scalar d = RationalScalar.of(4, 1);
    Distribution distribution = TrapezoidalDistribution.of(a, b, c, d);
    CDF cdf = CDF.of(distribution);
    Scalar actual = cdf.p_lessEquals(RealScalar.of(4));
    Scalar expected = RealScalar.ONE;
    assertEquals(expected, actual);
  }

  public void testMean() {
    Scalar a = RationalScalar.of(random.nextInt(100), 1);
    Scalar b = a.add(RealScalar.of(random.nextDouble() * 10));
    Scalar c = b.add(RealScalar.of(random.nextDouble() * 10));
    Scalar d = c.add(RealScalar.of(random.nextDouble() * 10));
    TrapezoidalDistribution distribution = (TrapezoidalDistribution) TrapezoidalDistribution.of(a, b, c, d);
    Tensor all = Tensors.empty();
    for (int i = 0; i < 3000; ++i) {
      Scalar s = RandomVariate.of(distribution);
      all.append(s);
    }
    Scalar meanCalc = distribution.mean();
    Scalar meanSamples = (Scalar) Mean.of(all);
    System.out.println("meanCalc: " + meanCalc);
    System.out.println("meanSamp: " + meanSamples);
    Scalar diff = meanCalc.subtract(meanSamples).abs();
    assertTrue(Scalars.lessEquals(diff, RealScalar.of(0.1)));
  }
}
