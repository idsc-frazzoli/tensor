// code by jph
package ch.ethz.idsc.tensor.mat;

import java.nio.file.Paths;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.io.Get;
import junit.framework.TestCase;

public class SingularValueDecompositionExtTest extends TestCase {
  private static void _check(Tensor matrix) {
    assertTrue(SquareMatrixQ.of(matrix));
    SingularValueDecomposition.of(matrix);
    Tensor svd = IdentityMatrix.of(matrix.length()).subtract(Transpose.of(matrix));
    try {
      SingularValueDecomposition.of(svd);
      System.out.println("SingularValueDecomposition works!");
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testSimple() throws Exception {
    String string = getClass().getResource("/mat/svd0.mathematica").getPath();
    _check(Get.of(Paths.get(string)));
  }
}
