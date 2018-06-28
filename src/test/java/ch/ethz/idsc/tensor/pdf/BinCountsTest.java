// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.qty.QuantityTensor;
import ch.ethz.idsc.tensor.qty.Unit;
import ch.ethz.idsc.tensor.red.Total;
import junit.framework.TestCase;

public class BinCountsTest extends TestCase {
  public void testWidthTwo() {
    Tensor hist = BinCounts.of(Tensors.vector(6, 7, 1, 2, 3, 4, 2), RealScalar.of(2));
    assertEquals(hist, Tensors.fromString("{1, 3, 1, 2}"));
  }

  public void testWidthHalf() {
    Tensor values = Tensors.vector(6, 7, 1, 2, 3, 4, 2);
    Tensor hist = BinCounts.of(values, RationalScalar.of(1, 2));
    assertEquals(hist, Tensors.fromString("{0, 0, 1, 0, 2, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1}"));
    assertEquals(Total.of(hist).Get().number().intValue(), values.length());
  }

  public void testEmpty() {
    assertEquals(BinCounts.of(Tensors.vector(), RealScalar.ONE), Tensors.empty());
  }

  public void testDefault() {
    Distribution distribution = ExponentialDistribution.of(RealScalar.ONE);
    Tensor vector = RandomVariate.of(distribution, 10);
    assertEquals(BinCounts.of(vector), BinCounts.of(vector, RealScalar.ONE));
  }

  public void testQuantity() {
    Tensor vector = QuantityTensor.of(Tensors.vector(1, 2, 3, 4, 1, 2, 1, 0, 3, 2, 1, 2), Unit.of("s"));
    Tensor result = BinCounts.of(vector, Quantity.of(1, "s"));
    assertEquals(result, Tensors.vector(1, 4, 4, 2, 1));
  }

  public void testNegative() {
    try {
      BinCounts.of(Tensors.vector(-1e-10), RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      BinCounts.of(Tensors.vector(-1e-10, -10), RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      BinCounts.of(Tensors.vector(1, 2, 3, 4, 0, -3, 12, 32), RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailDomain() {
    try {
      BinCounts.of(Tensors.vector(-1e-10), RealScalar.of(1.0));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailWidth() {
    try {
      BinCounts.of(Tensors.vector(1, 2), RealScalar.of(0.0)); // zero
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      BinCounts.of(Tensors.vector(1, 2), RealScalar.of(-.2)); // negative
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
