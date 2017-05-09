// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class IncrementTest extends TestCase {
  public void testIncr() {
    Tensor matrix = Tensors.matrixInt(new int[][] { { -8, 3, -3 }, { 2, -2, 7 } });
    matrix.set(Increment.ONE, 0, 0);
    Tensor result = matrix.map(Increment.ONE);
    Tensor check = Tensors.matrixInt(new int[][] { { -6, 4, -2 }, { 3, -1, 8 } });
    assertEquals(result, check);
  }
}
