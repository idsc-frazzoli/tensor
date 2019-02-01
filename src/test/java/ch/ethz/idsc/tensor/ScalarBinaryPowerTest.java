// code by jph
package ch.ethz.idsc.tensor;

import java.io.IOException;

import ch.ethz.idsc.tensor.alg.BinaryPower;
import ch.ethz.idsc.tensor.io.Serialization;
import junit.framework.TestCase;

public class ScalarBinaryPowerTest extends TestCase {
  public void testSimple() {
    try {
      Scalars.binaryPower(null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testSerializable() throws ClassNotFoundException, IOException {
    BinaryPower<Scalar> binaryPower = Scalars.binaryPower(RealScalar.ONE);
    BinaryPower<Scalar> copy = Serialization.copy(binaryPower);
    assertEquals(copy.zeroth(), RealScalar.ONE);
  }
}
