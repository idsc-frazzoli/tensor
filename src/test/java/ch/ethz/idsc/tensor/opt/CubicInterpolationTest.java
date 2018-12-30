// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Subdivide;
import junit.framework.TestCase;

public class CubicInterpolationTest extends TestCase {
  public void testSimple() {
    Interpolation interpolation = CubicInterpolation.of(Tensors.vector(1, 0, 0, 2, 0));
    Tensor tensor = Subdivide.of(0, 4, 8);
    Tensor values = tensor.map(interpolation::At);
    assertEquals(values, Tensors.fromString("{1, 293/448, 0, -177/448, 0, 583/448, 2, 421/448, 0}"));
  }
}
