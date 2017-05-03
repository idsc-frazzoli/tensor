// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class PositiveDefiniteMatrixQTest extends TestCase {
  public void testMathematica3() {
    boolean status = PositiveDefiniteMatrixQ.ofHermitian(Tensors.fromString("{{4, 3, 2, I}, {3, 4, 3, 2}, {2, 3, 4, 3}, {-I, 2, 3, 4}}"));
    assertTrue(status);
  }

  public void testMathematica4() {
    boolean status = PositiveDefiniteMatrixQ.ofHermitian(Tensors.fromString("{{4, 3, 2, I}, {3, 4, 3, 2}, {2, 3, 4, 3}, {-I, 2, 3, 0}}"));
    assertFalse(status);
  }
}
