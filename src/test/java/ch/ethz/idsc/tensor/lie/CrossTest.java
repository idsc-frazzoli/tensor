// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.UnitVector;
import ch.ethz.idsc.tensor.pdf.DiscreteUniformDistribution;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import junit.framework.TestCase;

public class CrossTest extends TestCase {
  private static Tensor testOnly(Tensor a, Tensor b) {
    return Cross.of(a).dot(b);
  }

  public void testUnits() {
    Tensor v1 = UnitVector.of(3, 0);
    Tensor v2 = UnitVector.of(3, 1);
    Tensor v3 = UnitVector.of(3, 2);
    assertEquals(Cross.of(v1, v2), v3);
    assertEquals(Cross.of(v2, v3), v1);
    assertEquals(Cross.of(v3, v1), v2);
  }

  public void testSimple() {
    Distribution distribution = NormalDistribution.standard();
    for (int c = 0; c < 100; ++c) {
      Tensor a = RandomVariate.of(distribution, 3);
      Tensor b = RandomVariate.of(distribution, 3);
      assertEquals(Cross.of(a, b), testOnly(a, b));
    }
  }

  public void testSimple2() {
    Distribution distribution = DiscreteUniformDistribution.of(-10, 10);
    for (int c = 0; c < 100; ++c) {
      Tensor a = RandomVariate.of(distribution, 3);
      Tensor b = RandomVariate.of(distribution, 3);
      assertEquals(Cross.of(a, b), testOnly(a, b));
    }
  }
}
