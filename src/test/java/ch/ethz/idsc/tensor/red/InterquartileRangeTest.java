// code by gjoel
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.ExponentialDistribution;
import ch.ethz.idsc.tensor.pdf.PoissonDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.pdf.UniformDistribution;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Clip;
import ch.ethz.idsc.tensor.sca.Log;
import junit.framework.TestCase;

public class InterquartileRangeTest extends TestCase {
  public void testSamples() {
    Tensor samples = Tensors.vector(0, 1, 2, 3, 10);
    assertEquals(InterquartileRange.of(samples), RealScalar.of(2)); // == 3 - 1
  }

  public void testMathematica() {
    assertEquals(InterquartileRange.of(Tensors.vector(1, 3, 4, 2, 5, 6)), RealScalar.of(3));
  }

  public void testDistributionExp() { // continuous
    Scalar lambda = RealScalar.of(5);
    Distribution distribution = ExponentialDistribution.of(lambda);
    assertTrue(Chop._12.close(InterquartileRange.of(distribution), Log.of(RealScalar.of(3)).divide(lambda)));
  }

  public void testDistributionUniform() { // continuous
    Distribution distribution = UniformDistribution.of(22, 30);
    assertTrue(Chop._12.close(InterquartileRange.of(distribution), RealScalar.of(4)));
  }

  public void testDistributionPoisson() { // discrete
    // Mathematica: InterquartileRange[PoissonDistribution[10.5]] == 5
    Scalar lambda = RealScalar.of(10.5);
    Distribution distribution = PoissonDistribution.of(lambda);
    Scalar iqr = InterquartileRange.of(distribution);
    assertEquals(iqr, RealScalar.of(5));
    Tensor random = RandomVariate.of(distribution, 1000);
    Scalar test = InterquartileRange.of(random);
    assertTrue(Clip.function(4, 6).isInside(test));
  }

  public void testFail() {
    try {
      InterquartileRange.of(Tensors.empty());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      InterquartileRange.of(HilbertMatrix.of(3, 4));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
