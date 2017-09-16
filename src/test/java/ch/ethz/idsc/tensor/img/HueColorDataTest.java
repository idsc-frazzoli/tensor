// code by jph
package ch.ethz.idsc.tensor.img;

import java.awt.Color;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class HueColorDataTest extends TestCase {
  public void testSome() {
    Tensor color = HueColorData.FUNCTION.apply(RealScalar.of(0.1));
    Tensor alter = ColorDataGradients.HUE.apply(RealScalar.of(0.1));
    assertTrue(Chop._05.close(color, alter));
    assertEquals(HueColorData.FUNCTION.apply(RealScalar.ONE), ColorFormat.toVector(Color.RED));
    assertEquals(HueColorData.FUNCTION.apply(DoubleScalar.POSITIVE_INFINITY), Tensors.vector(0, 0, 0, 0));
  }
}
