// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.red.Mean;
import ch.ethz.idsc.tensor.sca.Clip;
import junit.framework.TestCase;

public class TriangularDistributionHiTest extends TestCase {
  public void testPdf() {
    Scalar a = RealScalar.of(1);
    Scalar b = RealScalar.of(2);
    Distribution distribution = TriangularDistribution.of(a, b, b);
    assertEquals(PDF.of(distribution).at(RealScalar.ONE), RealScalar.ZERO);
    assertEquals(PDF.of(distribution).at(RationalScalar.of(3, 2)), RealScalar.ONE);
    assertEquals(PDF.of(distribution).at(RationalScalar.of(5, 2)), RealScalar.ZERO);
    assertEquals(PDF.of(distribution).at(b), RealScalar.of(2));
    assertEquals(PDF.of(distribution).at(RealScalar.of(100)), RealScalar.ZERO);
  }

  public void testCdf() {
    Scalar a = RealScalar.of(1);
    Scalar b = RealScalar.of(2);
    Distribution distribution = TriangularDistribution.of(a, b, b);
    assertEquals(CDF.of(distribution).p_lessThan(RealScalar.ZERO), RealScalar.ZERO);
    assertEquals(CDF.of(distribution).p_lessThan(a), RealScalar.ZERO);
    assertEquals(CDF.of(distribution).p_lessEquals(a), RealScalar.ZERO);
    assertEquals(CDF.of(distribution).p_lessThan(RationalScalar.of(3, 2)), RationalScalar.of(1, 4));
    assertEquals(CDF.of(distribution).p_lessThan(b), RealScalar.ONE);
    assertEquals(CDF.of(distribution).p_lessEquals(b), RealScalar.ONE);
    assertEquals(CDF.of(distribution).p_lessThan(RationalScalar.of(5, 2)), RealScalar.ONE);
  }

  public void testMean() {
    Scalar a = RealScalar.of(1);
    Scalar b = RealScalar.of(2);
    Distribution distribution = TriangularDistribution.of(a, b, b);
    assertEquals(Mean.of(distribution), RationalScalar.of(5, 3));
    Scalar mean = Mean.of(RandomVariate.of(distribution, 100)).Get();
    Clip.function(1.5, 1.8).requireInside(mean);
  }
}
