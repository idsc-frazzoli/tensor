// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class LowerTriangularizeTest extends TestCase {
  public void testSimple0() {
    Tensor matrix = Tensors.fromString("{{1,2,3},{4,5,6},{7,8,9},{9,5,2}}");
    Tensor actual = Tensors.fromString("{{1, 0, 0}, {4, 5, 0}, {7, 8, 9}, {9, 5, 2}}");
    assertEquals(LowerTriangularize.of(matrix), actual);
  }

  public void testSimpleN1() {
    Tensor matrix = Tensors.fromString("{{1,2,3},{4,5,6},{7,8,9},{9,5,2}}");
    Tensor actual = Tensors.fromString("{{0, 0, 0}, {4, 0, 0}, {7, 8, 0}, {9, 5, 2}}");
    assertEquals(LowerTriangularize.of(matrix, -1), actual);
  }
}
