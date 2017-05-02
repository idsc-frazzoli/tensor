// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class NearestInterpolationTest extends TestCase {
  public void testSimple() {
    Interpolation interpolation = NearestInterpolation.of(Tensors.vector(10, 20, 30, 40));
    assertEquals(interpolation.get(Tensors.vector(2.8)), RealScalar.of(40));
    assertEquals(interpolation.get(Tensors.vector(1.1)), RealScalar.of(20));
  }
}
