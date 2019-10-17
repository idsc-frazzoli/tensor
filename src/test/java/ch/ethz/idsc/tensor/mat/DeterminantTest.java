// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.lie.LieAlgebras;
import junit.framework.TestCase;

public class DeterminantTest extends TestCase {
  public void testFailMatrixQ() {
    Tensor table = Tensors.fromString("{{1, 2, 3}, {4, 5}}");
    try {
      Det.of(table);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailNonArray() {
    Tensor matrix = HilbertMatrix.of(4);
    matrix.set(Tensors.vector(1, 2, 3), 1, 2);
    try {
      Det.of(matrix);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailRank3() {
    try {
      Det.of(LieAlgebras.sl2());
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailRank3b() {
    try {
      Det.of(Array.zeros(2, 2, 3));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
