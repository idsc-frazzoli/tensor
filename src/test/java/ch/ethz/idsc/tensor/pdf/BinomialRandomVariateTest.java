// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import junit.framework.TestCase;

public class BinomialRandomVariateTest extends TestCase {
  public void testDivert() {
    assertEquals(BinomialDistribution.of(1200, DoubleScalar.of(.5)).getClass(), BinomialRandomVariate.class);
    assertEquals(BinomialDistribution.of(1200, RationalScalar.of(1, 2)).getClass(), BinomialDistribution.class);
    assertEquals(BinomialDistribution.of(12000, DoubleScalar.of(.1)).getClass(), BinomialRandomVariate.class);
    assertEquals(BinomialDistribution.of(120000, DoubleScalar.of(.0001)).getClass(), BinomialRandomVariate.class);
  }

  public void testCorner() {
    Distribution distribution1 = BinomialDistribution.of(10, RealScalar.ONE);
    Distribution distribution2 = new BinomialRandomVariate(10, RealScalar.ONE);
    Scalar s1 = RandomVariate.of(distribution1);
    Scalar s2 = RandomVariate.of(distribution2);
    assertEquals(s1, s2);
  }
}
