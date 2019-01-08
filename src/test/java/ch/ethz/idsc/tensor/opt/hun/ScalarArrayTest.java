// code by jph
package ch.ethz.idsc.tensor.opt.hun;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class ScalarArrayTest extends TestCase {
  public void testSimple() {
    Tensor tensor = Tensors.fromString("{{1, 2}, {3, 4, 5}}");
    Scalar[][] array = ScalarArray.ofMatrix(tensor);
    Tensor matrix = Tensors.matrix(array);
    assertEquals(tensor, matrix);
  }
}
