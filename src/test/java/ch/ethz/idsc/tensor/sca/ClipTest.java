// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class ClipTest extends TestCase {
  public void testApply() {
    Scalar min = RealScalar.of(-3);
    Scalar max = RealScalar.of(10);
    Clip clip = Clip.function(min, max);
    assertEquals(clip.apply(RealScalar.of(-10)), min);
    assertEquals(clip.apply(RealScalar.of(-4)), min);
    assertEquals(clip.apply(RealScalar.of(0)), RealScalar.ZERO);
    assertEquals(clip.apply(RealScalar.of(13)), max);
  }

  public void testVector() {
    Scalar min = RealScalar.of(-3);
    Scalar max = RealScalar.of(10);
    Clip clip = Clip.function(min, max);
    Tensor vector = Tensors.vector(-30, 30, 5);
    assertEquals(clip.of(vector), Tensors.vector(-3, 10, 5));
  }

  public void testUnit() {
    assertEquals(Clip.unit().apply(RealScalar.of(-.1)), RealScalar.ZERO);
    assertEquals(Clip.unit().apply(RealScalar.of(0.1)), RealScalar.of(0.1));
    assertEquals(Clip.unit().apply(RealScalar.of(1.1)), RealScalar.ONE);
  }

  public void testFail() {
    Clip.function(5, 5);
    try {
      Clip.function(2, -3);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testQuantity() {
    Scalar min = Quantity.of(-3, "m");
    Scalar max = Quantity.of(2, "m");
    Clip clip = Clip.function(min, max);
    assertEquals(clip.apply(Quantity.of(-5, "m")), min);
    assertEquals(clip.apply(Quantity.of(5, "m")), max);
    Scalar value = Quantity.of(-1, "m");
    assertEquals(clip.apply(value), value);
  }

  public void testQuantityInside() {
    Scalar min = Quantity.of(-3, "m");
    Scalar max = Quantity.of(2, "m");
    Clip clip = Clip.function(min, max);
    assertTrue(clip.isInside(Quantity.of(1, "m")));
    assertTrue(clip.isInside(Quantity.of(2, "m")));
    assertFalse(clip.isInside(Quantity.of(3, "m")));
    try {
      clip.isInside(Quantity.of(0, "V"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      clip.isInside(Quantity.of(3, "V"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testInsideFail() {
    try {
      Clip.unit().isInside(Quantity.of(0.5, "m"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testRescaleQuantity() {
    Scalar min = Quantity.of(-3, "m");
    Scalar max = Quantity.of(2, "m");
    Clip clip = Clip.function(min, max);
    assertEquals(clip.rescale(Quantity.of(-3, "m")), RealScalar.ZERO);
    assertEquals(clip.rescale(Quantity.of(-1, "m")), RationalScalar.of(2, 5));
    assertEquals(clip.rescale(Quantity.of(2, "m")), RealScalar.ONE);
    assertEquals(clip.rescale(Quantity.of(10, "m")), RealScalar.ONE);
    assertEquals(clip.min(), min);
    assertEquals(clip.max(), max);
    assertEquals(clip.width(), Quantity.of(5, "m"));
  }

  public void testRescale() {
    Scalar min = RealScalar.of(5);
    Scalar max = RealScalar.of(25);
    Clip clip = Clip.function(min, max);
    assertEquals(clip.rescale(RealScalar.of(20)), RealScalar.of(3 / 4.0));
    assertEquals(clip.min(), RealScalar.of(5));
    assertEquals(clip.max(), RealScalar.of(25));
    assertEquals(clip.width(), RealScalar.of(20));
  }

  public void testClipMinMax() {
    Clip clip = Clip.function(3, 5);
    assertEquals(clip.min(), RealScalar.of(3));
    assertEquals(clip.max(), RealScalar.of(5));
    assertEquals(clip.width(), RealScalar.of(2));
  }

  public void testClipOutside() {
    Clip clip = Clip.function(3, 5);
    assertEquals(clip.requireInside(RealScalar.of(3.9)), RealScalar.of(3.9));
    try {
      clip.requireInside(RealScalar.of(2.9));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testClipInfty() {
    Clip clip = Clip.function(DoubleScalar.NEGATIVE_INFINITY, DoubleScalar.POSITIVE_INFINITY);
    Tensor tensor = RandomVariate.of(NormalDistribution.standard(), 2, 3, 4);
    assertEquals(tensor.map(clip), tensor);
  }

  public void testClipInftyQuantity() {
    Clip clip = Clip.function(Quantity.of(Double.NEGATIVE_INFINITY, "V"), Quantity.of(Double.POSITIVE_INFINITY, "V"));
    Tensor tensor = RandomVariate.of(NormalDistribution.standard(), 2, 3, 4).map(s -> Quantity.of(s, "V"));
    assertEquals(tensor.map(clip), tensor);
  }

  public void testQuantityOutside() {
    Scalar min = Quantity.of(-3, "m");
    Scalar max = Quantity.of(2, "m");
    Clip clip = Clip.function(min, max);
    assertFalse(clip.isOutside(Quantity.of(1, "m")));
    assertFalse(clip.isOutside(Quantity.of(2, "m")));
    assertTrue(clip.isOutside(Quantity.of(3, "m")));
    try {
      clip.isOutside(Quantity.of(3, "V"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testQuantityFail() {
    try {
      Clip.unit().apply(Quantity.of(-5, "m"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Clip.absoluteOne().apply(Quantity.of(-5, "m"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
