// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.alg.TensorMap;
import junit.framework.TestCase;

public class PmulTest extends TestCase {
  public void testSimple() {
    Tensor mat = Tensors.fromString("{{1,2,3},{4,5,6}}");
    Tensors.vector(-2, 2).pmul(mat);
    Tensor factor = Tensors.vector(-2, 2, 2);
    Tensor rep = TensorMap.of(row -> row.pmul(factor), mat, 1);
    assertEquals(rep, Tensors.fromString("{{-2, 4, 6}, {-8, 10, 12}}"));
  }
}
