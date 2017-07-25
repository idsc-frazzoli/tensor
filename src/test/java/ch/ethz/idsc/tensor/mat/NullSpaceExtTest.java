// code by jph
package ch.ethz.idsc.tensor.mat;

import java.nio.file.Paths;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.io.Get;
import junit.framework.TestCase;

public class NullSpaceExtTest extends TestCase {
  private static void _check(Tensor matrix) {
    SingularValueDecomposition.of(matrix);
    Tensor rep = IdentityMatrix.of(matrix.length()).subtract(Transpose.of(matrix));
    try {
      SingularValueDecomposition.of(rep);
      // NullSpace.of(rep);
      assertTrue(false);
      System.out.println("nullspace works!");
    } catch (Exception exception) {
      // ---
    }
  }

  public void testGet0() throws Exception {
    String string = getClass().getResource("/mat/nullspace0.mathematica").getPath();
    Tensor matrix = Get.of(Paths.get(string));
    _check(matrix);
  }
}
