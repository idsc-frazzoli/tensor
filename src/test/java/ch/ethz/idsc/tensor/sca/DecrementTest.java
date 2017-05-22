// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class DecrementTest extends TestCase {
  public void testIncr() {
    Tensor matrix = Tensors.matrixInt(new int[][] { { -8, 3, -3 }, { 2, -2, 7 } });
    matrix.set(Decrement.ONE, 0, 0);
    Tensor result = matrix.map(Decrement.ONE);
    Tensor check = Tensors.matrixInt(new int[][] { { -10, 2, -4 }, { 1, -3, 6 } });
    assertEquals(result, check);
  }
}
