// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class ReverseTest extends TestCase {
  public void testRev() {
    Tensor tensor = Tensors.vectorInt(3, 2, 6, 5);
    Tensor rev = Reverse.of(tensor);
    Tensor res = Tensors.vectorInt(5, 6, 2, 3);
    assertEquals(rev, res);
  }
}
