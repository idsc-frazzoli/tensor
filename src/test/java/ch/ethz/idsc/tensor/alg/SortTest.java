// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.ScalarComparators;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class SortTest extends TestCase {
  public void testSort() {
    assertEquals(Sort.of(Tensors.vector(0, 4, 5, 2, -3)), Tensors.vector(-3, 0, 2, 4, 5));
    assertEquals(Sort.of(Tensors.vector(4, 5, 0, 2, -3)), Tensors.vector(-3, 0, 2, 4, 5));
    final Tensor m = Tensors.vectorDouble(.4, 0, .5, .2, -.3);
    assertEquals(Sort.of(m), Tensors.vectorDouble(-.3, 0, .2, .4, .5));
    assertEquals(Sort.of(m, ScalarComparators.DESCENDING), Tensors.vectorDouble(.5, .4, .2, 0, -.3));
    assertEquals(Sort.of(m), Sort.of(m, ScalarComparators.ASCENDING));
  }
}
