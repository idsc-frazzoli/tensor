// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Map;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.red.Tally;
import junit.framework.TestCase;

public class EmpiricalDistributionTest extends TestCase {
  public void testPDF() {
    Distribution distribution = EmpiricalDistribution.fromUnscaledPDF(Tensors.vector(0, 9, 1));
    PDF pdf = PDF.of(distribution);
    assertEquals(pdf.at(RealScalar.of(0)), RealScalar.ZERO);
    assertEquals(pdf.at(RealScalar.of(1)), RationalScalar.of(9, 10));
    assertEquals(pdf.at(RealScalar.of(2)), RationalScalar.of(1, 10));
  }

  public void testCDF() {
    Distribution distribution = EmpiricalDistribution.fromUnscaledPDF(Tensors.vector(0, 9, 1));
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
    Distribution distribution = EmpiricalDistribution.fromUnscaledPDF(Tensors.vector(0, 9, 1));
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
    Distribution distribution = EmpiricalDistribution.fromUnscaledPDF(Tensors.vector(0, 2, 1, 0, 3, 0));
    Map<Tensor, Long> map = Tally.of(RandomVariate.of(distribution, 1000));
    assertFalse(map.containsKey(RealScalar.ZERO));
    assertTrue(map.containsKey(RealScalar.of(1)));
    assertTrue(map.containsKey(RealScalar.of(2)));
    assertFalse(map.containsKey(RealScalar.of(3)));
    assertTrue(map.containsKey(RealScalar.of(4)));
    assertFalse(map.containsKey(RealScalar.of(5)));
  }

  public void testNextDown() {
    AbstractDiscreteDistribution distribution = (AbstractDiscreteDistribution) //
    EmpiricalDistribution.fromUnscaledPDF(Tensors.vector(Math.PI, 2., 1., 1.123123, 3., 0, 0, 0));
    Scalar s = distribution.randomVariate(RealScalar.of(Math.nextDown(1)));
    assertEquals(s, RealScalar.of(4));
  }

  public void testRandomVariateNeedle1() {
    AbstractDiscreteDistribution distribution = (AbstractDiscreteDistribution) //
    EmpiricalDistribution.fromUnscaledPDF(Tensors.vector(0, 2, 1, 0, 3, 0));
    assertEquals(distribution.randomVariate(RealScalar.of(0)), RealScalar.ONE);
    assertEquals(distribution.randomVariate(RealScalar.of(.99999999999)), RealScalar.of(4));
  }

  public void testRandomVariateNeedle2() {
    AbstractDiscreteDistribution distribution = (AbstractDiscreteDistribution) //
    EmpiricalDistribution.fromUnscaledPDF(Tensors.vector(0, 0, 1, 0, 1, 0));
    assertEquals(distribution.randomVariate(RealScalar.of(0)), RealScalar.of(2));
    assertEquals(distribution.randomVariate(RealScalar.of(Math.nextDown(.5))), RealScalar.of(2));
    assertEquals(distribution.randomVariate(RationalScalar.of(1, 2)), RealScalar.of(4));
    assertEquals(distribution.randomVariate(RealScalar.of(Math.nextDown(1))), RealScalar.of(4));
  }

  public void testWrongReference() {
    try {
      AbstractDiscreteDistribution distribution = (AbstractDiscreteDistribution) //
      EmpiricalDistribution.fromUnscaledPDF(Tensors.vector(0, 0, 1, 0, 1, 0));
      distribution.randomVariate(RealScalar.of(Math.nextDown(0)));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      AbstractDiscreteDistribution distribution = (AbstractDiscreteDistribution) //
      EmpiricalDistribution.fromUnscaledPDF(Tensors.vector(0, 0, 1, 0, 1, 0));
      distribution.randomVariate(RealScalar.of(1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFail() {
    try {
      EmpiricalDistribution.fromUnscaledPDF(Tensors.vector(0, -9, 1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      EmpiricalDistribution.fromUnscaledPDF(Tensors.vector(0, 0, 0));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      EmpiricalDistribution.fromUnscaledPDF(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      EmpiricalDistribution.fromUnscaledPDF(HilbertMatrix.of(10));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
