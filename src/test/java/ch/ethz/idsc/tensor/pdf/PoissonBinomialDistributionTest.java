// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.red.Mean;
import ch.ethz.idsc.tensor.red.Variance;
import junit.framework.TestCase;

public class PoissonBinomialDistributionTest extends TestCase {
  public void testEmpty() {
    Distribution distribution = PoissonBinomialDistribution.of(Tensors.empty());
    Tensor samples = RandomVariate.of(distribution, 10);
    assertEquals(samples, Array.zeros(10));
    assertEquals(Mean.of(distribution), RealScalar.ZERO);
    assertEquals(Variance.of(distribution), RealScalar.ZERO);
  }

  public void testZeros() {
    Distribution distribution = PoissonBinomialDistribution.of(Array.zeros(4));
    Tensor samples = RandomVariate.of(distribution, 10);
    assertEquals(samples, Array.zeros(10));
    assertEquals(Mean.of(distribution), RealScalar.ZERO);
    assertEquals(Variance.of(distribution), RealScalar.ZERO);
  }

  public void testOnes() {
    Distribution distribution = PoissonBinomialDistribution.of(Tensors.vector(1, 1, 1, 1));
    Tensor samples = RandomVariate.of(distribution, 10);
    assertEquals(samples, Array.of(l -> RealScalar.of(4), 10));
    assertEquals(Mean.of(distribution), RealScalar.of(4));
    assertEquals(Variance.of(distribution), RealScalar.ZERO);
  }

  public void testFail() {
    try {
      PoissonBinomialDistribution.of(RealScalar.ZERO);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      PoissonBinomialDistribution.of(IdentityMatrix.of(3));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailInvalid() {
    try {
      PoissonBinomialDistribution.of(Tensors.vector(1, 1, 1, 1, 2, 0));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      PoissonBinomialDistribution.of(Tensors.vector(1, 1, 1, 1, -1, 1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
