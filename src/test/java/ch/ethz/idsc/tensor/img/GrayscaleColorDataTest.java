// code by jph
package ch.ethz.idsc.tensor.img;

import java.awt.Color;
import java.io.IOException;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.ExactTensorQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.io.Serialization;
import ch.ethz.idsc.tensor.red.Norm;
import junit.framework.TestCase;

public class GrayscaleColorDataTest extends TestCase {
  public void testColor() {
    assertEquals(ColorFormat.toColor(GrayscaleColorData.DEFAULT.apply(RealScalar.ZERO)), Color.BLACK);
    assertEquals(ColorFormat.toColor(GrayscaleColorData.DEFAULT.apply(RationalScalar.HALF)), Color.GRAY);
    assertEquals(ColorFormat.toColor(GrayscaleColorData.DEFAULT.apply(RealScalar.ONE)), Color.WHITE);
  }

  public void testApply() {
    Tensor tensor = ColorDataGradients.GRAYSCALE.apply(RealScalar.of(0.3));
    assertFalse(ExactTensorQ.of(tensor));
    tensor.set(RealScalar.ONE, 1);
    assertEquals(ColorDataGradients.GRAYSCALE.apply(RealScalar.of(0.3)), //
        Tensors.vector(new Double[] { 77.0, 77.0, 77.0, 255.0 }));
  }

  public void testTransparent() {
    Tensor vector = GrayscaleColorData.DEFAULT.apply(DoubleScalar.POSITIVE_INFINITY);
    assertTrue(Scalars.isZero(Norm._2.ofVector(vector)));
    assertEquals(vector, Array.zeros(4));
  }

  public void testColorData() {
    ColorDataGradient colorDataGradient = ColorDataGradients.GRAYSCALE.deriveWithOpacity(RealScalar.of(0.5));
    assertEquals(colorDataGradient.apply(RealScalar.ZERO), Tensors.vector(0, 0, 0, 128));
  }

  public void testSerializable() throws ClassNotFoundException, IOException {
    Serialization.copy(GrayscaleColorData.DEFAULT);
  }
}
