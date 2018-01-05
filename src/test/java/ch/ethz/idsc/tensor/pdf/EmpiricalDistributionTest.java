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
    Scalar s = distribution.quantile(RealScalar.of(Math.nextDown(1.0)));
    assertEquals(s, RealScalar.of(4));
  }

  public void testRandomVariateNeedle1() {
    AbstractDiscreteDistribution distribution = (AbstractDiscreteDistribution) //
    EmpiricalDistribution.fromUnscaledPDF(Tensors.vector(0, 2, 1, 0, 3, 0));
    assertEquals(distribution.quantile(RealScalar.of(0)), RealScalar.ONE);
    assertEquals(distribution.quantile(RealScalar.of(.99999999999)), RealScalar.of(4));
  }

  public void testRandomVariateNeedle2() {
    AbstractDiscreteDistribution distribution = (AbstractDiscreteDistribution) //
    EmpiricalDistribution.fromUnscaledPDF(Tensors.vector(0, 0, 1, 0, 1, 0));
    assertEquals(distribution.quantile(RealScalar.of(0)), RealScalar.of(2));
    assertEquals(distribution.quantile(RealScalar.of(Math.nextDown(.5))), RealScalar.of(2));
    assertEquals(distribution.quantile(RationalScalar.of(1, 2)), RealScalar.of(4));
    assertEquals(distribution.quantile(RealScalar.of(Math.nextDown(1.0))), RealScalar.of(4));
  }

  public void testVariance() {
    Distribution distribution = //
        EmpiricalDistribution.fromUnscaledPDF(Tensors.vector(0, 0, 1, 0, 1, 0));
    assertEquals(Expectation.variance(distribution), RealScalar.ONE);
  }

  public void testInverseCDF() {
    InverseCDF inv = InverseCDF.of(EmpiricalDistribution.fromUnscaledPDF(Tensors.vector(0, 3, 1)));
    Scalar x0 = inv.quantile(RealScalar.ZERO);
    Scalar x1 = inv.quantile(RealScalar.of(.5));
    Scalar x2 = inv.quantile(RealScalar.of(.8));
    // Scalar x3 = inv.quantile(RealScalar.of(1)); // at the moment: forbidden
    assertEquals(x0, RealScalar.ONE);
    assertEquals(x0, x1);
    assertEquals(x2, RealScalar.of(2));
  }

  public void testInverseCDFOne() {
    AbstractDiscreteDistribution distribution = (AbstractDiscreteDistribution) //
    EmpiricalDistribution.fromUnscaledPDF(Tensors.vector(0, 0, 1, 0, 1, 0, 0, 0));
    assertEquals(distribution.quantile(RealScalar.of(1)), RealScalar.of(4));
  }

  public void testFailInverseCDF() {
    InverseCDF inv = InverseCDF.of(EmpiricalDistribution.fromUnscaledPDF(Tensors.vector(0, 3, 1)));
    try {
      inv.quantile(RealScalar.of(-0.1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      inv.quantile(RealScalar.of(1.1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testWrongReference() {
    try {
      AbstractDiscreteDistribution distribution = (AbstractDiscreteDistribution) //
      EmpiricalDistribution.fromUnscaledPDF(Tensors.vector(0, 0, 1, 0, 1, 0));
      distribution.quantile(RealScalar.of(Math.nextDown(0.0)));
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
      EmpiricalDistribution.fromUnscaledPDF(Tensors.empty());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFail2() {
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
