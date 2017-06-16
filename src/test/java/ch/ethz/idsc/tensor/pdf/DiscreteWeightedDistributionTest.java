// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Map;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.red.Tally;
import junit.framework.TestCase;

public class DiscreteWeightedDistributionTest extends TestCase {
  public void testPDF() {
    Distribution distribution = DiscreteWeightedDistribution.of(Tensors.vector(0, 9, 1));
    PDF pdf = PDF.of(distribution);
    assertEquals(pdf.p_equals(RealScalar.of(0)), RealScalar.ZERO);
    assertEquals(pdf.p_equals(RealScalar.of(1)), RationalScalar.of(9, 10));
    assertEquals(pdf.p_equals(RealScalar.of(2)), RationalScalar.of(1, 10));
  }

  public void testCDF() {
    Distribution distribution = DiscreteWeightedDistribution.of(Tensors.vector(0, 9, 1));
    CDF pdf = CDF.of(distribution);
    assertEquals(pdf.p_lessEquals(RealScalar.of(-.1)), RealScalar.ZERO);
    assertEquals(pdf.p_lessEquals(RealScalar.of(0)), RealScalar.ZERO);
    assertEquals(pdf.p_lessEquals(RealScalar.of(0.1)), RealScalar.ZERO);
    assertEquals(pdf.p_lessEquals(RealScalar.of(1)), RationalScalar.of(9, 10));
    assertEquals(pdf.p_lessEquals(RealScalar.of(1.1)), RationalScalar.of(9, 10));
    assertEquals(pdf.p_lessEquals(RealScalar.of(2)), RationalScalar.of(10, 10));
    assertEquals(pdf.p_lessEquals(RealScalar.of(3)), RationalScalar.of(10, 10));
  }

  public void testCDF2() {
    Distribution distribution = DiscreteWeightedDistribution.of(Tensors.vector(0, 9, 1));
    CDF pdf = CDF.of(distribution);
    assertEquals(pdf.p_lessThan(RealScalar.of(-.1)), RealScalar.ZERO);
    assertEquals(pdf.p_lessThan(RealScalar.of(0)), RealScalar.ZERO);
    assertEquals(pdf.p_lessThan(RealScalar.of(0.1)), RealScalar.ZERO);
    assertEquals(pdf.p_lessThan(RealScalar.of(1)), RationalScalar.of(0, 10));
    assertEquals(pdf.p_lessThan(RealScalar.of(1.1)), RationalScalar.of(9, 10));
    assertEquals(pdf.p_lessThan(RealScalar.of(2)), RationalScalar.of(9, 10));
    assertEquals(pdf.p_lessThan(RealScalar.of(3)), RationalScalar.of(10, 10));
  }

  public void testRandomVariate() {
    Distribution distribution = DiscreteWeightedDistribution.of(Tensors.vector(0, 2, 1, 0, 3, 0));
    Map<Tensor, Long> map = Tally.of(RandomVariate.of(distribution, 1000));
    assertFalse(map.containsKey(RealScalar.ZERO));
    assertTrue(map.containsKey(RealScalar.of(1)));
    assertTrue(map.containsKey(RealScalar.of(2)));
    assertFalse(map.containsKey(RealScalar.of(3)));
    assertTrue(map.containsKey(RealScalar.of(4)));
    assertFalse(map.containsKey(RealScalar.of(5)));
  }

  public void testRandomVariateNeedle() {
    AbstractDiscreteDistribution distribution = (AbstractDiscreteDistribution) //
    DiscreteWeightedDistribution.of(Tensors.vector(0, 2, 1, 0, 3, 0));
    assertEquals(distribution.randomVariate(RealScalar.of(0)), RealScalar.ONE);
    assertEquals(distribution.randomVariate(RealScalar.of(.99999999999)), RealScalar.of(4));
  }

  public void testFail() {
    try {
      DiscreteWeightedDistribution.of(Tensors.vector(0, -9, 1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      DiscreteWeightedDistribution.of(Tensors.vector(0, 0, 0));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      DiscreteWeightedDistribution.of(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      DiscreteWeightedDistribution.of(HilbertMatrix.of(10));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
