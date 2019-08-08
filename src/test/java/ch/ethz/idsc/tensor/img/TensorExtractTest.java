// code by jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class TensorExtractTest extends TestCase {
  public void testEmpty() {
    assertEquals(TensorExtract.of(Tensors.empty(), 0, t -> t), Tensors.empty());
  }

  public void testRadiusFail() {
    try {
      TensorExtract.of(Tensors.empty(), -1, t -> t);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFunctionNullFail() {
    try {
      TensorExtract.of(Tensors.empty(), 2, null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
