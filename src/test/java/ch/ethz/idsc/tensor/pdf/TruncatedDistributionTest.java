// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.io.IOException;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.io.Serialization;
import ch.ethz.idsc.tensor.sca.Clip;
import ch.ethz.idsc.tensor.sca.Clips;
import junit.framework.TestCase;

public class TruncatedDistributionTest extends TestCase {
  public void testSimple() {
    Clip clip = Clips.interval(10, 11);
    Distribution distribution = TruncatedDistribution.of(NormalDistribution.of(10, 2), clip);
    Scalar scalar = RandomVariate.of(distribution);
    assertTrue(clip.isInside(scalar));
  }

  public void testSerializable() throws ClassNotFoundException, IOException {
    Clip clip = Clips.interval(10, 11);
    Distribution distribution = TruncatedDistribution.of(BinomialDistribution.of(20, DoubleScalar.of(0.5)), clip);
    Scalar scalar = RandomVariate.of(distribution);
    assertTrue(ExactScalarQ.of(scalar));
    assertTrue(clip.isInside(scalar));
    Serialization.copy(distribution);
    Serialization.copy(TruncatedDistribution.of(NormalDistribution.of(10, 2), clip));
  }

  public void testFail() {
    Clip clip = Clips.interval(10, 11);
    Distribution distribution = TruncatedDistribution.of(NormalDistribution.of(-100, 0.2), clip);
    try {
      RandomVariate.of(distribution);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNullFail() {
    try {
      TruncatedDistribution.of(NormalDistribution.of(-100, .2), null);
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      TruncatedDistribution.of(null, Clips.interval(10, 11));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
