// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.io.IOException;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.io.Serialization;
import ch.ethz.idsc.tensor.sca.Clip;
import junit.framework.TestCase;

public class TruncatedDistributionTest extends TestCase {
  public void testSimple() {
    Clip clip = Clip.function(10, 11);
    Distribution distribution = TruncatedDistribution.of(NormalDistribution.of(10, 2), clip);
    Scalar scalar = RandomVariate.of(distribution);
    assertTrue(clip.isInside(scalar));
  }

  public void testSerializable() throws ClassNotFoundException, IOException {
    Clip clip = Clip.function(10, 11);
    Distribution distribution = TruncatedDistribution.of(BinomialDistribution.of(20, DoubleScalar.of(.5)), clip);
    Scalar scalar = RandomVariate.of(distribution);
    assertTrue(ExactScalarQ.of(scalar));
    assertTrue(clip.isInside(scalar));
    Serialization.copy(distribution);
    Serialization.copy(TruncatedDistribution.of(NormalDistribution.of(10, 2), clip));
  }

  public void testFail() {
    Clip clip = Clip.function(10, 11);
    Distribution distribution = TruncatedDistribution.of(NormalDistribution.of(-100, .2), clip);
    try {
      RandomVariate.of(distribution);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
