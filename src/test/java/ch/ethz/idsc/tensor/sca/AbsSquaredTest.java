// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.StringScalar;
import junit.framework.TestCase;

public class AbsSquaredTest extends TestCase {
  public void testQuantity() {
    Scalar qs1 = Scalars.fromString("3+4*I[s^2*m^-1]");
    Scalar qs2 = AbsSquared.FUNCTION.apply(qs1);
    assertEquals(qs2.toString(), "25[m^-2*s^4]");
  }

  public void testTensor() {
    Tensor qs1 = Tensors.fromString("{3+4*I[s^2*m^-1]}");
    Tensor qs2 = AbsSquared.of(qs1);
    assertEquals(qs2.toString(), "{25[m^-2*s^4]}");
  }

  public void testFail() {
    try {
      AbsSquared.FUNCTION.apply(StringScalar.of("idsc"));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
