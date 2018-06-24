// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class AbstractContinuousDistributionTest extends TestCase {
  public void testEquals() {
    Distribution d1 = NormalDistribution.of(1, 2);
    Distribution d2 = NormalDistribution.of(1, 3);
    Distribution d3 = BinomialDistribution.of(3, RealScalar.of(.2));
    assertFalse(d1.equals(d2));
    assertFalse(d1.equals(d3));
    assertFalse(d3.equals(d1));
  }

  public void testHistogram() {
    Distribution d1 = HistogramDistribution.of(Tensors.vector(1.2, .3, .11, 2.2, 1.4, 5.0, 1.23, 0.1));
    Distribution d2 = NormalDistribution.of(1, 3);
    assertFalse(d1.equals(d2));
    assertFalse(d2.equals(d1));
  }

  public void testHistogram2() {
    Distribution d1 = HistogramDistribution.of(Tensors.vector(1.2, .3, .11, 2.2, 1.4, 5.0, 1.23, 0.1));
    Distribution d2 = HistogramDistribution.of(Tensors.vector(1.2, .3, .11, 2.2, 1.4, 5.0, 1.23, 0.1), RealScalar.of(2));
    Distribution d3 = NormalDistribution.of(1, 3);
    assertFalse(d1.equals(d2));
    assertFalse(d1.equals(d3));
    assertFalse(d3.equals(d1));
  }
}
