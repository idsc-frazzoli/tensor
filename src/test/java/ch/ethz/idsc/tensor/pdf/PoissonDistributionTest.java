// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.red.Total;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class PoissonDistributionTest extends TestCase {
  static Tensor values(PDF pdf, int length) {
    return Tensors.vector(i -> pdf.p_equals(RealScalar.of(i)), length);
  }

  public void testSingle() {
    Distribution distribution = PoissonDistribution.of(RealScalar.of(2));
    PDF pdf = PDF.of(distribution);
    assertTrue(pdf.p_equals(RealScalar.ZERO).toString().startsWith("0.13533"));
    assertTrue(pdf.p_equals(RealScalar.ONE).toString().startsWith("0.27067"));
    assertTrue(pdf.p_equals(RealScalar.of(2)).toString().startsWith("0.27067"));
    assertTrue(pdf.p_equals(RealScalar.of(3)).toString().startsWith("0.18044"));
  }

  public void testSimple() {
    Distribution distribution = PoissonDistribution.of(RealScalar.of(2));
    PDF pdf = PDF.of(distribution);
    Tensor prob = values(pdf, 16);
    Scalar scalar = Total.of(prob).Get();
    assertTrue(Scalars.lessThan(RealScalar.of(.9999), scalar));
    assertTrue(Scalars.lessThan(scalar, RealScalar.ONE));
  }

  public void testValues() {
    Distribution distribution = PoissonDistribution.of(RealScalar.of(3));
    PDF pdf = PDF.of(distribution);
    pdf.p_equals(RealScalar.of(30));
    Tensor prob = values(pdf, 30);
    // assertEquals(poissonDistribution.values().length(), 30 + 1);
    Scalar sum = Total.of(prob).Get();
    // System.out.println(sum);
    assertEquals(sum, RealScalar.ONE);
  }

  public void testPDF() {
    Distribution distribution = PoissonDistribution.of(RealScalar.of(10.5));
    CDF pdf = CDF.of(distribution);
    Scalar s = pdf.p_lessThan(RealScalar.of(50));
    assertEquals(Chop.of(s.subtract(RealScalar.ONE)), RealScalar.ZERO);
  }

  public void testPDF2() {
    Distribution distribution = PoissonDistribution.of(RealScalar.of(1.5));
    CDF pdf = CDF.of(distribution);
    Scalar s = pdf.p_lessThan(RealScalar.of(50));
    assertEquals(Chop.of(s.subtract(RealScalar.ONE)), RealScalar.ZERO);
  }

  public void testFailLambda() {
    try {
      PoissonDistribution.of(RealScalar.ZERO);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      PoissonDistribution.of(RealScalar.of(-0.1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
