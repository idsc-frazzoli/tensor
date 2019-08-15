// code by jph
package ch.ethz.idsc.tensor;

import java.io.IOException;

import ch.ethz.idsc.tensor.alg.BinaryPower;
import ch.ethz.idsc.tensor.io.Serialization;
import ch.ethz.idsc.tensor.num.GaussScalar;
import junit.framework.TestCase;

public class ScalarBinaryPowerTest extends TestCase {
  public void testGaussScalar() {
    int prime = 677;
    GaussScalar gaussScalar = GaussScalar.of(432, prime);
    GaussScalar power = gaussScalar.power(RealScalar.of(-123));
    Scalar now = GaussScalar.of(1, prime);
    BinaryPower<Scalar> binaryPower = new ScalarBinaryPower<>(now);
    for (int index = 0; index < 123; ++index)
      now = now.divide(gaussScalar);
    assertEquals(power, now);
    assertEquals(power, binaryPower.apply(gaussScalar, -123));
  }

  public void testSerializable() throws ClassNotFoundException, IOException {
    BinaryPower<Scalar> binaryPower = Scalars.binaryPower(RealScalar.ONE);
    BinaryPower<Scalar> copy = Serialization.copy(binaryPower);
    assertEquals(copy.apply(RealScalar.of(2), 3), RealScalar.of(8));
  }

  public void testSimple() {
    try {
      Scalars.binaryPower(null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
