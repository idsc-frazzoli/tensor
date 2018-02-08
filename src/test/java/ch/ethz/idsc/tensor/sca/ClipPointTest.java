// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class ClipPointTest extends TestCase {
  public void testZeroWidth() {
    Clip clip = Clip.function(2, 2);
    assertEquals(clip.apply(RealScalar.of(3)), RealScalar.of(2));
    assertTrue(clip.isInside(RealScalar.of(2)));
    assertFalse(clip.isInside(RealScalar.of(-2)));
    assertEquals(clip.requireInside(RealScalar.of(2)), RealScalar.of(2));
  }

  public void testRescaleZeroWidth() {
    Clip clip = Clip.function(2, 2);
    assertEquals(clip.rescale(RealScalar.of(-1)), RealScalar.ZERO);
    assertEquals(clip.rescale(RealScalar.ZERO), RealScalar.ZERO);
    assertEquals(clip.rescale(RealScalar.of(2)), RealScalar.ZERO);
    assertEquals(clip.rescale(RealScalar.of(4)), RealScalar.ZERO);
    assertEquals(clip.min(), RealScalar.of(2));
    assertEquals(clip.max(), RealScalar.of(2));
  }

  public void testQuantity() {
    Scalar value = Quantity.of(3, "s");
    Clip clip = Clip.function(value, value);
    assertEquals(clip.apply(Quantity.of(2, "s")), value);
    assertEquals(clip.width(), Quantity.of(0, "s"));
    assertFalse(clip.width().equals(RealScalar.ZERO));
    assertEquals(clip.requireInside(value), value);
  }

  public void testVector() {
    Scalar value = Quantity.of(2, "m*s^-1");
    Clip clip = Clip.function(value, value);
    Tensor vector = Tensors.fromString("{1[m*s^-1],5[m*s^-1],2[m*s^-1]}");
    Tensor result = clip.of(vector);
    assertEquals(result, Tensors.fromString("{2[m*s^-1], 2[m*s^-1], 2[m*s^-1]}"));
  }

  public void testRescale() {
    Scalar value = Quantity.of(2, "m*s^-1");
    Clip clip = Clip.function(value, value);
    assertEquals(clip.rescale(Quantity.of(4, "m*s^-1")), RealScalar.ZERO);
    try {
      clip.requireInside(Quantity.of(3, "m*s^-1"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testRescaleFail() {
    Scalar value = Quantity.of(2, "m*s^-1");
    Clip clip = Clip.function(value, value);
    assertEquals(clip.requireInside(value), value);
    try {
      clip.rescale(Quantity.of(2, "kg"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
