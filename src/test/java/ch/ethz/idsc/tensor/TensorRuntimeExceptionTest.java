// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.io.Serialization;
import junit.framework.TestCase;

public class TensorRuntimeExceptionTest extends TestCase {
  public void testFull() {
    Exception exception = TensorRuntimeException.of(Tensors.vector(1, 2), Tensors.vector(9, 3));
    assertEquals(exception.getMessage(), "{1, 2}; {9, 3}");
  }

  public void testShort() {
    Exception exception = TensorRuntimeException.of(Array.zeros(20, 10, 5), RealScalar.ONE);
    assertEquals(exception.getMessage(), "T[20, 10, 5]; 1");
  }

  public void testEmpty() {
    Exception ex = TensorRuntimeException.of();
    assertEquals(ex.getMessage(), "");
  }

  public void testNull() {
    try {
      TensorRuntimeException.of(Tensors.vector(2), null);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testSerializable() throws Exception {
    Serialization.of(TensorRuntimeException.of(RealScalar.ONE));
  }

  public void testMessage() {
    Exception exception = TensorRuntimeException.of();
    assertEquals(exception.getMessage(), "");
  }
}
