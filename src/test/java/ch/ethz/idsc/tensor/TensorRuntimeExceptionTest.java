// code by jph
package ch.ethz.idsc.tensor;

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
}
