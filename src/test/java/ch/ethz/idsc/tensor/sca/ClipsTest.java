// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class ClipsTest extends TestCase {
  public void testSignZero1() {
    Clip clip = Clips.interval(-0.0, +0.0);
    assertTrue(clip instanceof ClipPoint);
    assertEquals(clip.min(), RealScalar.ZERO);
    assertEquals(clip.max(), RealScalar.ZERO);
  }

  public void testSignZero2() {
    Clip clip = Clips.interval(+0.0, -0.0);
    assertTrue(clip instanceof ClipPoint);
    assertEquals(clip.min(), RealScalar.ZERO);
    assertEquals(clip.max(), RealScalar.ZERO);
  }

  public void testInftyHalf() {
    Clip clip = Clips.interval(-0.0, Double.POSITIVE_INFINITY);
    assertTrue(clip instanceof ClipInterval);
    assertEquals(clip.min(), RealScalar.ZERO);
    assertEquals(clip.max(), DoubleScalar.POSITIVE_INFINITY);
    assertEquals(clip.width(), DoubleScalar.POSITIVE_INFINITY);
    clip.requireInside(DoubleScalar.POSITIVE_INFINITY);
    assertFalse(clip.isInside(DoubleScalar.NEGATIVE_INFINITY));
  }

  public void testRealNumbers() {
    Clip clip = Clips.interval(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    assertTrue(clip instanceof ClipInterval);
    assertEquals(clip.min(), DoubleScalar.NEGATIVE_INFINITY);
    assertEquals(clip.max(), DoubleScalar.POSITIVE_INFINITY);
    assertEquals(clip.width(), DoubleScalar.POSITIVE_INFINITY);
    clip.requireInside(DoubleScalar.NEGATIVE_INFINITY);
    clip.requireInside(DoubleScalar.POSITIVE_INFINITY);
  }

  public void testAbsolute() {
    Clip clip = Clips.absolute(Quantity.of(10, "N"));
    clip.requireInside(Quantity.of(-10, "N"));
    clip.requireInside(Quantity.of(+10, "N"));
    assertTrue(clip.isOutside(Quantity.of(-10.1, "N")));
    assertTrue(clip.isOutside(Quantity.of(+10.1, "N")));
  }

  public void testAbsoluteNumber() {
    Clip clip = Clips.absolute(Math.PI);
    assertTrue(clip.isInside(RealScalar.of(-1)));
    assertTrue(clip.isInside(RealScalar.of(+1)));
    assertTrue(clip.isInside(RealScalar.of(-Math.PI)));
    assertTrue(clip.isInside(RealScalar.of(+Math.PI)));
    assertTrue(clip.isOutside(RealScalar.of(Math.nextUp(Math.PI))));
    assertTrue(clip.isOutside(RealScalar.of(Math.nextDown(-Math.PI))));
  }

  public void testAbsoluteOne() {
    Clip clip = Clips.absoluteOne();
    assertTrue(clip.isInside(RealScalar.of(-1)));
    assertTrue(clip.isInside(RealScalar.of(+1)));
    assertTrue(clip.isOutside(RealScalar.of(Math.nextUp(1))));
    assertTrue(clip.isOutside(RealScalar.of(Math.nextDown(-1))));
  }

  public void testAbsoluteZero() {
    Clip clip = Clips.absolute(Quantity.of(0, "N"));
    Scalar scalar = clip.rescale(Quantity.of(5, "N"));
    assertEquals(scalar, RealScalar.ZERO);
    ExactScalarQ.require(scalar);
  }

  public void testAbsoluteFail() {
    try {
      Clips.absolute(Quantity.of(-1, "kg"));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testInsideFail() {
    try {
      Clips.unit().isInside(Quantity.of(0.5, "m"));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testQuantityFail() {
    try {
      Clips.unit().apply(Quantity.of(-5, "m"));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      Clips.absoluteOne().apply(Quantity.of(-5, "m"));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testComplexFail() {
    try {
      Clips.absolute(ComplexScalar.I);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
