// code by jph
package ch.ethz.idsc.tensor.lie;

import java.util.Arrays;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import junit.framework.TestCase;

public class KhatriRaoTest extends TestCase {
  public void testSimple() {
    Tensor a = Tensors.fromString("{{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}");
    Tensor b = Tensors.fromString("{{1, 2, 3}, {4, 5, 6}}");
    Tensor tensor = KhatriRao.of(a, b);
    Tensor result = Tensors.fromString("{{7, 14, 21}, {28, 35, 42}, {8, 16, 24}, {32, 40, 48}, {9, 18, 27}, {36, 45, 54}}");
    assertEquals(tensor, result);
  }

  public void testRank3() {
    Tensor tensor = KhatriRao.of(LieAlgebras.so3(), LieAlgebras.so3());
    assertEquals(Dimensions.of(tensor), Arrays.asList(9, 3, 3));
  }
}
