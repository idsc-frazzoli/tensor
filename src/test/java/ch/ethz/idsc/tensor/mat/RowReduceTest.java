// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class RowReduceTest extends TestCase {
  // from Mathematica, RowReduce Applications: solving a linear system
  public void testReduce1() {
    Tensor m = Tensors.fromString("{{1, 2, 3, 1}, {5, 6, 7, 1}, {7, 8, 9, 1}}");
    Tensor r = RowReduce.of(m);
    Tensor sol = Tensors.fromString("{{1, 0, -1, -1}, {0, 1, 2, 1}, {0, 0, 0, 0}}");
    assertEquals(r, sol);
  }

  // from Mathematica, RowReduce Applications: a linear system without solution
  public void testReduce2() {
    Tensor m = Tensors.fromString("{{1, 2, 3, 1}, {5, 6, 7, -2}, {7, 8, 9, 1}}");
    Tensor r = RowReduce.of(m);
    Tensor sol = Tensors.fromString("{{1, 0, -1, 0}, {0, 1, 2, 0}, {0, 0, 0, 1}}");
    assertEquals(r, sol);
  }

  // from Mathematica, RowReduce Applications: for a degenerate square matrix
  public void testReduce3() {
    Tensor m = Tensors.fromString("{{1, 2, 3, 4, 1, 0, 0, 0}, {5, 6, 7, 8, 0, 1, 0, 0}, {9, 10, 11, 12, 0, 0, 1, 0}, {13, 14, 15, 16, 0, 0, 0, 1}}");
    Tensor r = RowReduce.of(m);
    Tensor sol = Tensors.fromString("{{1, 0, -1, -2, 0, 0, -7/2, 5/2}, {0, 1, 2, 3, 0, 0, 13/4, -9/4}, {0, 0, 0, 0, 1, 0, -3, 2}, {0, 0, 0, 0, 0, 1, -2, 1}}");
    assertEquals(r, sol);
  }
}
