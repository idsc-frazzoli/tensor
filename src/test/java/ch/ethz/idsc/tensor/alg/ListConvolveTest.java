// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class ListConvolveTest extends TestCase {
  public void testVector1() {
    Tensor kernel = Tensors.vectorInt(0, -1, 3);
    Tensor tensor = Tensors.vectorInt(0, 0, 1, 6, 0, 0, -1, 0, 0);
    Tensor result = ListConvolve.of(kernel, tensor);
    Tensor actual = Tensors.vectorInt(0, -1, -3, 18, 0, 1, -3);
    assertEquals(result, actual);
  }
}
