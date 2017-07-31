// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class GammaDistributionTest extends TestCase {
  public void testSimple() {
    Distribution distribution = GammaDistribution.of(RealScalar.of(1.123), RealScalar.of(2.3));
    PDF pdf = PDF.of(distribution);
    Scalar prob = pdf.at(RealScalar.of(0.78));
    assertTrue(Chop._08.close(prob, DoubleScalar.of(0.28770929331586703)));
  }
}
