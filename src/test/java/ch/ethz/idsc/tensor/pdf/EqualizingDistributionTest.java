// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.io.IOException;
import java.util.Collections;

import ch.ethz.idsc.tensor.ExactTensorQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Differences;
import ch.ethz.idsc.tensor.alg.Last;
import ch.ethz.idsc.tensor.alg.Subdivide;
import ch.ethz.idsc.tensor.io.Serialization;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.opt.LinearInterpolation;
import ch.ethz.idsc.tensor.red.Mean;
import ch.ethz.idsc.tensor.red.Tally;
import ch.ethz.idsc.tensor.red.Variance;
import ch.ethz.idsc.tensor.sca.Clips;
import junit.framework.TestCase;

public class EqualizingDistributionTest extends TestCase {
  public void testSimple() throws ClassNotFoundException, IOException {
    Distribution distribution = Serialization.copy(EqualizingDistribution.fromUnscaledPDF(Tensors.vector(3)));
    CDF cdf = CDF.of(distribution);
    Tensor domain = Subdivide.of(0, 1, 10);
    assertEquals(domain, domain.map(cdf::p_lessThan));
    assertEquals(domain, domain.map(cdf::p_lessEquals));
    PDF pdf = PDF.of(distribution);
    assertEquals(domain.map(pdf::at), Tensors.vector(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
    assertEquals(Mean.of(distribution), RationalScalar.HALF);
    assertEquals(Variance.of(distribution), RationalScalar.of(1, 12));
    InverseCDF inverseCDF = InverseCDF.of(distribution);
    assertEquals(domain.map(inverseCDF::quantile), domain);
    RandomVariate.of(distribution, 30).map(Clips.unit()::requireInside);
  }

  public void testResample() {
    Tensor vector = Tensors.vector(-3, 6, 10, 20, 22, 30);
    Distribution distribution = EqualizingDistribution.fromUnscaledPDF(Differences.of(vector));
    Tensor domain = Subdivide.of(0, 1, 10);
    InverseCDF inverseCDF = InverseCDF.of(distribution);
    Tensor tensor = domain.map(inverseCDF::quantile);
    Tensor linear = tensor.map(LinearInterpolation.of(vector)::At);
    assertEquals(linear.Get(0), RealScalar.of(-3));
    assertEquals(Last.of(linear), RealScalar.of(30));
    Tensor uniform = Differences.of(linear);
    ExactTensorQ.require(uniform);
    assertEquals(Tally.of(uniform), Collections.singletonMap(RationalScalar.of(33, 10), 10L));
  }

  public void testNegativeFail() {
    try {
      EqualizingDistribution.fromUnscaledPDF(Tensors.vector(0, -9, 1));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testZeroFail() {
    try {
      EqualizingDistribution.fromUnscaledPDF(Tensors.vector(0, 0, 0));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testEmptyFail() {
    try {
      EqualizingDistribution.fromUnscaledPDF(Tensors.empty());
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testScalarFail() {
    try {
      EqualizingDistribution.fromUnscaledPDF(RealScalar.ONE);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testMatrixFail() {
    try {
      EqualizingDistribution.fromUnscaledPDF(HilbertMatrix.of(10));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
