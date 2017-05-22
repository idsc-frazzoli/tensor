// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.io.Serialization;
import junit.framework.TestCase;

public class TensorRuntimeExceptionTest extends TestCase {
  public void testSome() {
    Exception ex = TensorRuntimeException.of(Tensors.vector(1, 2), Tensors.vector(9, 3));
    assertFalse(ex.getMessage().isEmpty());
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
}
