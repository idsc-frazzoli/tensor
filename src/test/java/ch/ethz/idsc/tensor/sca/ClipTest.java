// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class ClipTest extends TestCase {
  public void testSimple() {
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
      // TODO what to do here?
      clip.isInside(Quantity.of(0, "V"));
      // assertTrue(false);
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

  public void testQuantityZero() {
    assertEquals(Clip.function(0, 0).apply(Quantity.of(-5, "m")), RealScalar.ZERO);
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
