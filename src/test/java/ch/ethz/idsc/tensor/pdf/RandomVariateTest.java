// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Arrays;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Dimensions;
import junit.framework.TestCase;

public class RandomVariateTest extends TestCase {
  public void testFormat() {
    Distribution distribution = DiscreteUniformDistribution.of(2, 10);
    Tensor array = RandomVariate.of(distribution, 3, 4, 5);
    assertEquals(Dimensions.of(array), Arrays.asList(3, 4, 5));
  }
}
