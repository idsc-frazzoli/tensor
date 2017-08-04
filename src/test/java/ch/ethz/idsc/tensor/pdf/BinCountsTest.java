// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.red.Total;
import junit.framework.TestCase;

public class BinCountsTest extends TestCase {
  public void testSimple() {
    Tensor hist = BinCounts.of(Tensors.vector(6, 7, 1, 2, 3, 4, 2), RealScalar.of(2));
    assertEquals(hist, Tensors.fromString("{1, 3, 1, 2}"));
  }

  public void testSimple2() {
    Tensor values = Tensors.vector(6, 7, 1, 2, 3, 4, 2);
    Tensor hist = BinCounts.of(values, RationalScalar.of(1, 2));
    assertEquals(hist, Tensors.fromString("{0, 0, 1, 0, 2, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1}"));
    assertEquals(Total.of(hist).Get().number().intValue(), values.length());
  }

  public void testEmpty() {
    assertEquals(BinCounts.of(Tensors.vector(), RealScalar.ONE), Tensors.empty());
  }

  public void testNegative() {
    try {
      assertEquals(BinCounts.of(Tensors.vector(-1e-10), RealScalar.ONE), Tensors.empty());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      assertEquals(BinCounts.of(Tensors.vector(-1e-10, -10), RealScalar.ONE), Tensors.empty());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      assertEquals(BinCounts.of(Tensors.vector(1, 2, 3, 4, 0, -3, 12, 32), RealScalar.ONE), Tensors.empty());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFail() {
    try {
      BinCounts.of(Tensors.vector(-1e-10), RealScalar.of(-.2));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
