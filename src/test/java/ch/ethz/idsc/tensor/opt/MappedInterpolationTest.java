// code by jph
package ch.ethz.idsc.tensor.opt;

import java.io.IOException;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.Serialization;
import ch.ethz.idsc.tensor.sca.Floor;
import junit.framework.TestCase;

public class MappedInterpolationTest extends TestCase {
  public void testFloor() throws ClassNotFoundException, IOException {
    Interpolation interpolation = //
        Serialization.copy(MappedInterpolation.of(Tensors.vector(10, 20, 30, 40), Floor.FUNCTION));
    assertEquals(interpolation.get(Tensors.vector(2.8)), RealScalar.of(30));
    assertEquals(interpolation.get(Tensors.vector(1.1)), RealScalar.of(20));
  }

  public void testFailNull() {
    try {
      MappedInterpolation.of(null, Floor.FUNCTION);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailFunctionNull() {
    try {
      MappedInterpolation.of(Tensors.vector(3, 4, 5), null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
