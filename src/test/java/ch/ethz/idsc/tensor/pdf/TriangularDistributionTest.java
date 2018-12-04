// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.red.Mean;
import ch.ethz.idsc.tensor.sca.Clip;
import junit.framework.TestCase;

public class TriangularDistributionTest extends TestCase {
  public void testPdf() {
    Scalar a = RealScalar.of(1);
    Scalar b = RealScalar.of(2);
    Scalar c = RealScalar.of(3);
    Distribution distribution = TriangularDistribution.of(a, b, c);
    assertEquals(PDF.of(distribution).at(RationalScalar.of(3, 2)), RationalScalar.HALF);
    assertEquals(PDF.of(distribution).at(RationalScalar.of(5, 2)), RationalScalar.HALF);
    assertEquals(PDF.of(distribution).at(b), RealScalar.ONE);
    assertEquals(PDF.of(distribution).at(RealScalar.of(100)), RealScalar.ZERO);
  }

  public void testCdf() {
    Scalar a = RealScalar.of(1);
    Scalar b = RealScalar.of(2);
    Scalar c = RealScalar.of(3);
    Distribution distribution = TriangularDistribution.of(a, b, c);
    assertEquals(CDF.of(distribution).p_lessThan(RealScalar.ZERO), RealScalar.ZERO);
    assertEquals(CDF.of(distribution).p_lessThan(a), RealScalar.ZERO);
    assertEquals(CDF.of(distribution).p_lessEquals(a), RealScalar.ZERO);
    assertEquals(CDF.of(distribution).p_lessThan(b), RationalScalar.HALF);
    assertEquals(CDF.of(distribution).p_lessEquals(b), RationalScalar.HALF);
    assertEquals(CDF.of(distribution).p_lessThan(RationalScalar.of(5, 2)), RationalScalar.of(7, 8));
    assertEquals(CDF.of(distribution).p_lessThan(c), RealScalar.ONE);
    assertEquals(CDF.of(distribution).p_lessEquals(c), RealScalar.ONE);
  }

  public void testMean() {
    Scalar a = RealScalar.of(1);
    Scalar b = RealScalar.of(2);
    Scalar c = RealScalar.of(3);
    Distribution distribution = TriangularDistribution.of(a, b, c);
    Scalar mean = Mean.of(RandomVariate.of(distribution, 100)).Get();
    Clip.function(1.5, 2.5).requireInside(mean);
    assertEquals(Mean.of(distribution), RealScalar.of(2));
  }

  public void testExactFail() {
    TriangularDistribution.of(RealScalar.of(3), RealScalar.of(3), RealScalar.of(5));
    TriangularDistribution.of(RealScalar.of(3), RealScalar.of(5), RealScalar.of(5));
    try {
      TriangularDistribution.of(RealScalar.of(3), RealScalar.of(3), RealScalar.of(3));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      TriangularDistribution.of(RealScalar.of(3), RealScalar.of(4), RealScalar.of(3));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNumericFail() {
    TriangularDistribution.of(RealScalar.of(3.), RealScalar.of(3.), RealScalar.of(5.));
    TriangularDistribution.of(RealScalar.of(3.), RealScalar.of(5.), RealScalar.of(5.));
    try {
      TriangularDistribution.of(RealScalar.of(3.), RealScalar.of(3.), RealScalar.of(3.));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      TriangularDistribution.of(RealScalar.of(3.), RealScalar.of(4.), RealScalar.of(3.));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
