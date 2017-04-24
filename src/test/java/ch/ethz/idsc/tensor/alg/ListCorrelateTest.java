// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class ListCorrelateTest extends TestCase {
  public void testVector1() {
    Tensor kernel = Tensors.vector(2, 1, 3);
    Tensor tensor = Tensors.vector(0, 0, 1, 0, 0, 0);
    Tensor result = ListCorrelate.of(kernel, tensor);
    Tensor actual = Tensors.vector(3, 1, 2, 0);
    assertEquals(result, actual);
  }

  public void testVector2() {
    Tensor kernel = Tensors.vector(2, 1, 3);
    Tensor tensor = Tensors.vector(0, 0, 1, 0, -2, 1, 2);
    Tensor result = ListCorrelate.of(kernel, tensor);
    Tensor actual = Tensors.vector(3, 1, -4, 1, 3);
    assertEquals(result, actual);
  }

  public void testMatrix() {
    Tensor kernel = Tensors.fromString("{{2, 1, 3}, {0, 1, -1}}");
    Tensor tensor = Tensors.fromString("{{0, 0, 1, 0, -2, 1, 2}, {2, 0, 1, 0, -2, 1, 2}, {3, 2, 3, 3, 45, 3, 2}}");
    Tensor result = ListCorrelate.of(kernel, tensor);
    Tensor actual = Tensors.fromString("{{2, 2, -2, -2, 2}, {6, 1, -46, 43, 4}}");
    assertEquals(result, actual);
  }

  public void testRank3() {
    Tensor kernel = Tensors.fromString("{{{2, 1, 3}, {0, 1, -1}}}");
    Tensor tensor = Tensors.fromString("{{{0, 0, 1, 0, -2, 1, 2}, {2, 0, 1, 0, -2, 1, 2}, {3, 2, 3, 3, 45, 3, 2}}}");
    Tensor result = ListCorrelate.of(kernel, tensor);
    Tensor actual = Tensors.fromString("{{{2, 2, -2, -2, 2}, {6, 1, -46, 43, 4}}}");
    assertEquals(result, actual);
  }
}
