// code by jph
package ch.ethz.idsc.tensor.img;

import java.awt.Color;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.red.Norm;
import junit.framework.TestCase;

public class GrayscaleColorDataTest extends TestCase {
  public void testSimple() {
    Color c0 = ColorFormat.toColor(GrayscaleColorData.FUNCTION.apply(RealScalar.ZERO));
    Color c1 = ColorFormat.toColor(GrayscaleColorData.FUNCTION.apply(RealScalar.ONE));
    assertEquals(c0, Color.BLACK);
    assertEquals(c1, Color.WHITE);
  }

  public void testTransparent() {
    Tensor vector = GrayscaleColorData.FUNCTION.apply(DoubleScalar.POSITIVE_INFINITY);
    assertTrue(Scalars.isZero(Norm._2.ofVector(vector)));
  }
}
