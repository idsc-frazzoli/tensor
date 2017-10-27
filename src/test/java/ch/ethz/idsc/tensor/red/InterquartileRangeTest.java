// code by gjoel
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.*;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.ExponentialDistribution;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Log;
import junit.framework.TestCase;

public class InterquartileRangeTest extends TestCase {
    public void testSamples() {
        Tensor samples = Tensors.vector(0, 1, 2, 3, 10);
        assertEquals(InterquartileRange.of(samples), RealScalar.of(2)); // == 3 - 1
    }

    public void testDistributions() {
        Scalar lambda = RealScalar.of(5);
        Distribution distribution = ExponentialDistribution.of(lambda);
        assertTrue(Chop._10.close(InterquartileRange.of(distribution), Log.of(RealScalar.of(3)).divide(lambda)));
    }
}
