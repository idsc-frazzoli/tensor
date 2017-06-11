// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.pdf.DiscreteDistribution;
import ch.ethz.idsc.tensor.pdf.PDF;
import ch.ethz.idsc.tensor.pdf.PoissonDistribution;
import ch.ethz.idsc.tensor.red.Tally;
import ch.ethz.idsc.tensor.red.Total;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class PoissonDistributionTest extends TestCase {
  /* package */ static Tensor values(PDF pdf, int length) {
    return Tensors.vector(i -> pdf.p_equals(RealScalar.of(i)), length);
  }

  public void testSingle() {
    DiscreteDistribution poissonDistribution = PoissonDistribution.of(RealScalar.of(2));
    PDF pdf = PDF.of(poissonDistribution);
    assertTrue(pdf.p_equals(RealScalar.ZERO).toString().startsWith("0.13533"));
    assertTrue(pdf.p_equals(RealScalar.ONE).toString().startsWith("0.27067"));
    assertTrue(pdf.p_equals(RealScalar.of(2)).toString().startsWith("0.27067"));
    assertTrue(pdf.p_equals(RealScalar.of(3)).toString().startsWith("0.18044"));
  }

  public void testSimple() {
    DiscreteDistribution poissonDistribution = PoissonDistribution.of(RealScalar.of(2));
    PDF pdf = PDF.of(poissonDistribution);
    Tensor prob = values(pdf, 16);
    Scalar scalar = Total.of(prob).Get();
    assertTrue(Scalars.lessThan(RealScalar.of(.9999), scalar));
    assertTrue(Scalars.lessThan(scalar, RealScalar.ONE));
  }

  public void testMemo() {
    int size = PoissonDistribution.MEMO.size();
    {
      PoissonDistribution.of(RealScalar.of(32.));
      PoissonDistribution.of(RealScalar.of(32.));
      PoissonDistribution.of(RealScalar.of(32.));
    }
    assertEquals(size + 1, PoissonDistribution.MEMO.size());
  }

  public void testValues() {
    DiscreteDistribution poissonDistribution = PoissonDistribution.of(RealScalar.of(3));
    PDF pdf = PDF.of(poissonDistribution);
    poissonDistribution.p_equals(30);
    Tensor prob = values(pdf, 30);
    // assertEquals(poissonDistribution.values().length(), 30 + 1);
    Scalar sum = Total.of(prob).Get();
    // System.out.println(sum);
    assertEquals(sum, RealScalar.ONE);
  }

  public void testSample() {
    DiscreteDistribution poissonDistribution = PoissonDistribution.of(RealScalar.of(1.5));
    Random random = new Random();
    Tensor collect = Tensors.empty();
    PDF pdf = PDF.of(poissonDistribution);
    for (int c = 0; c < 100; ++c) {
      Scalar sample = pdf.nextSample(random);
      collect.append(sample);
      // System.out.println(sample);
    }
    // Map<Tensor, Long> map =
    Tally.of(collect);
    // System.out.println(map);
  }

  public void testPDF() {
    DiscreteDistribution poissonDistribution = PoissonDistribution.of(RealScalar.of(10.5));
    PDF pdf = PDF.of(poissonDistribution);
    Scalar s = pdf.p_lessThan(RealScalar.of(50));
    assertEquals(Chop.of(s.subtract(RealScalar.ONE)), RealScalar.ZERO);
  }

  public void testPDF2() {
    DiscreteDistribution poissonDistribution = PoissonDistribution.of(RealScalar.of(1.5));
    PDF pdf = PDF.of(poissonDistribution);
    Scalar s = pdf.p_lessThan(RealScalar.of(50));
    assertEquals(Chop.of(s.subtract(RealScalar.ONE)), RealScalar.ZERO);
  }
}
