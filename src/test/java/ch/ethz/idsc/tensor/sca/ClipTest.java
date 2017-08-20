// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
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
}
