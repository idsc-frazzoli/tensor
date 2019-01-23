// code by jph
package ch.ethz.idsc.tensor.qty;

import java.io.IOException;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.Serialization;
import ch.ethz.idsc.tensor.sca.AbsSquared;
import junit.framework.TestCase;

public class QuaternionImplTest extends TestCase {
  public void testImmutable() {
    Quaternion quaternion = Quaternion.of(1, 3, -2, 2);
    try {
      quaternion.xyz().set(RealScalar.ONE, 1);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testUnaffected() {
    Tensor vector = Tensors.vector(1, 2, 3);
    Quaternion quaternion = Quaternion.of(RealScalar.ZERO, vector);
    vector.set(RealScalar.ZERO, 1);
    assertEquals(quaternion.xyz(), Tensors.vector(1, 2, 3));
  }

  public void testPlusFail() {
    Scalar quaternion = Quaternion.of(1, 3, -2, 2);
    Scalar quantity = Quantity.of(1, "m");
    try {
      quaternion.add(quantity);
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      quantity.add(quaternion);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testAbsSquared() {
    Scalar scalar = AbsSquared.FUNCTION.apply(Quaternion.of(1, 2, 3, 4));
    assertTrue(scalar instanceof Quaternion);
    ExactScalarQ.require(scalar);
  }

  public void testNumberFail() {
    Quaternion quaternion = Quaternion.of(1, 3, -2, 2);
    try {
      quaternion.number();
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testHashcode() {
    Tensor tensor = Tensors.of( //
        Quaternion.of(1, 3, -2, 2), //
        Quaternion.of(3, 1, -2, 2), //
        Quaternion.of(3, 2, -2, 1), //
        Quaternion.of(1, 3, 2, -2));
    long count = tensor.stream().mapToInt(Tensor::hashCode).distinct().count();
    assertEquals(count, tensor.length());
  }

  public void testSerializable() throws ClassNotFoundException, IOException {
    Scalar q1 = Quaternion.of(1, 3, -2, 2);
    Scalar q2 = Serialization.copy(q1);
    assertEquals(q1, q2);
  }
}
