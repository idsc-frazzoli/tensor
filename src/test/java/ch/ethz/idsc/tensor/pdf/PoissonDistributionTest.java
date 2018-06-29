// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.NavigableMap;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.red.Total;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Clip;
import junit.framework.TestCase;

public class PoissonDistributionTest extends TestCase {
  static Tensor values(PDF pdf, int length) {
    return Tensors.vector(i -> pdf.at(RealScalar.of(i)), length);
  }

  public void testSingle() {
    Distribution distribution = PoissonDistribution.of(RealScalar.of(2));
    PDF pdf = PDF.of(distribution);
    assertTrue(pdf.at(RealScalar.ZERO).toString().startsWith("0.13533"));
    assertTrue(pdf.at(RealScalar.ONE).toString().startsWith("0.27067"));
    assertTrue(pdf.at(RealScalar.of(2)).toString().startsWith("0.27067"));
    assertTrue(pdf.at(RealScalar.of(3)).toString().startsWith("0.18044"));
  }

  public void testConvergence() {
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
    pdf.at(RealScalar.of(30));
    Tensor prob = values(pdf, 30);
    // assertEquals(poissonDistribution.values().length(), 30 + 1);
    Scalar sum = Total.of(prob).Get();
    // System.out.println(sum);
    assertEquals(sum, RealScalar.ONE);
  }

  public void testPDF() {
    Distribution distribution = PoissonDistribution.of(RealScalar.of(10.5));
    CDF cdf = CDF.of(distribution);
    Scalar s = cdf.p_lessThan(RealScalar.of(50));
    assertEquals(Chop._12.of(s.subtract(RealScalar.ONE)), RealScalar.ZERO);
  }

  public void testPDF2() {
    Distribution distribution = PoissonDistribution.of(RealScalar.of(1.5));
    CDF cdf = CDF.of(distribution);
    Scalar s = cdf.p_lessThan(RealScalar.of(50));
    assertEquals(Chop._12.of(s.subtract(RealScalar.ONE)), RealScalar.ZERO);
  }

  public void testInverseCDF() {
    InverseCDF inv = InverseCDF.of(PoissonDistribution.of(RealScalar.of(5.5)));
    Scalar x0 = inv.quantile(RealScalar.of(.0));
    Scalar x1 = inv.quantile(RealScalar.of(.1));
    Scalar x2 = inv.quantile(RealScalar.of(.5));
    assertEquals(x0, RealScalar.ZERO);
    assertTrue(Scalars.lessThan(x1, x2));
  }

  public void testInverseCDFOne() {
    Distribution distribution = PoissonDistribution.of(RealScalar.of(5.5));
    EvaluatedDiscreteDistribution edd = (EvaluatedDiscreteDistribution) distribution;
    NavigableMap<Scalar, Scalar> navigableMap = edd.inverse_cdf();
    assertTrue(34 < navigableMap.size());
    assertTrue(navigableMap.size() < 38);
    // navigableMap.forEach((k, v) -> System.out.println(k + " " + v));
    InverseCDF inv = InverseCDF.of(distribution);
    // System.out.println(inv.quantile(RealScalar.of(0.9999999999999985)));
    assertTrue(Clip.function(24, 26).isInside(inv.quantile(RealScalar.of(0.9999999989237532))));
    assertTrue(Clip.function(32, 34).isInside(inv.quantile(RealScalar.of(0.9999999999999985))));
    assertTrue(Clip.function(1900, 2000).isInside(inv.quantile(RealScalar.ONE)));
  }

  public void testToString() {
    Distribution distribution = PoissonDistribution.of(RealScalar.of(5.5));
    String string = distribution.toString();
    assertEquals(string, "PoissonDistribution[5.5]");
  }

  public void testQuantityFail() {
    try {
      PoissonDistribution.of(Quantity.of(3, "m"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
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

  public void testLarge() {
    Distribution distribution = PoissonDistribution.of(RealScalar.of(700));
    PDF pdf = PDF.of(distribution);
    assertTrue(Scalars.isZero(pdf.at(RealScalar.of(140.123))));
    assertTrue(Scalars.nonZero(pdf.at(RealScalar.of(1942))));
    assertTrue(Scalars.isZero(pdf.at(RealScalar.of(1945))));
    assertTrue(Scalars.isZero(pdf.at(RealScalar.of(10000000))));
    assertTrue(Scalars.isZero(pdf.at(RealScalar.of(-1))));
    assertTrue(Scalars.isZero(pdf.at(RealScalar.of(-10000000))));
    assertTrue(Scalars.isZero(pdf.at(RealScalar.of(-1000000.12))));
    // for (Tensor s : Range.of(1900, 2000))
    // System.out.println(s+" "+pdf.at(s.Get()));
  }

  public void testNextDownOne() {
    for (int c = 1; c < 700; c += 3) {
      Scalar lambda = DoubleScalar.of(c * .5 + 300);
      AbstractDiscreteDistribution distribution = //
          (AbstractDiscreteDistribution) PoissonDistribution.of(lambda);
      // Scalar s =
      distribution.quantile(RealScalar.of(Math.nextDown(1.0)));
      // System.out.println(lambda + " -> " + s);
    }
  }
}
