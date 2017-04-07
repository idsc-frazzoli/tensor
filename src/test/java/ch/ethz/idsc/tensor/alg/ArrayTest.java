// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import junit.framework.TestCase;

public class ArrayTest extends TestCase {
  public void testArray() {
    Tensor table = Array.of(l -> RealScalar.of(l.get(0) * l.get(2)), 3, 2, 4);
    Tensor res = Tensors.fromString("[[[0, 0, 0, 0], [0, 0, 0, 0]], [[0, 1, 2, 3], [0, 1, 2, 3]], [[0, 2, 4, 6], [0, 2, 4, 6]]]");
    assertEquals(table, res);
  }

  public void testCopy() {
    Tensor hilbert = HilbertMatrix.of(3, 5);
    Tensor table = Array.of(l -> hilbert.get(l), Dimensions.of(hilbert));
    assertEquals(hilbert, table);
  }
}
