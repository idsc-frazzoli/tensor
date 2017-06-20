// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import junit.framework.TestCase;

public class StandardNormalDistributionTest extends TestCase {
  public void testSimple() {
    Scalar x = StandardNormalDistribution.INSTANCE.at(RealScalar.ZERO);
    assertTrue(x.toString().startsWith("0.398942280"));
  }

  public void testSimple1() {
    Scalar x = StandardNormalDistribution.INSTANCE.at(RealScalar.ONE);
    Scalar xn = StandardNormalDistribution.INSTANCE.at(RealScalar.ONE.negate());
    assertTrue(x.toString().startsWith("0.241970724"));
    assertEquals(x, xn);
  }
}
