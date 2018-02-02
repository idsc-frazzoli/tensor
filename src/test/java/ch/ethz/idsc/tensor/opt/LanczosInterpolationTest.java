// code by jph
package ch.ethz.idsc.tensor.opt;

import java.util.Arrays;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.io.ResourceData;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class LanczosInterpolationTest extends TestCase {
  public void testVector() {
    Tensor vector = Tensors.vector(-1, 0, 3, 2, 0, -4, 2);
    Interpolation interpolation = LanczosInterpolation.of(vector, 3);
    for (int c = 0; c < vector.length(); ++c) {
      Scalar scalar = interpolation.Get(Tensors.vector(c));
      assertTrue(Chop._14.close(vector.Get(c), scalar));
    }
  }

  public void testImage() {
    String string = "/io/gray15x9.png";
    Tensor tensor = ResourceData.of(string);
    assertEquals(Dimensions.of(tensor), Arrays.asList(9, 15));
    Interpolation interpolation = LanczosInterpolation.of(tensor, 2);
    Tensor result = interpolation.get(Tensors.vector(6.2));
    assertEquals(Dimensions.of(result), Arrays.asList(15));
    Scalar scalar = interpolation.Get(Tensors.vector(4.4, 7.2));
    assertTrue(Chop._14.close(scalar, RealScalar.of(105.27240539882584)));
  }
}
