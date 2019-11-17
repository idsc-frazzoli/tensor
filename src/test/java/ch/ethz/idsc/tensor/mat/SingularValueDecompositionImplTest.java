// code by jph
package ch.ethz.idsc.tensor.mat;

import java.lang.reflect.Modifier;
import java.nio.file.Paths;

import ch.ethz.idsc.tensor.NumberQ;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.io.Get;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class SingularValueDecompositionImplTest extends TestCase {
  private static void _check(Tensor matrix) {
    assertTrue(SquareMatrixQ.of(matrix));
    SingularValueDecompositionTest.specialOps(matrix);
    Tensor svd = IdentityMatrix.of(matrix.length()).subtract(Transpose.of(matrix));
    SingularValueDecompositionTest.specialOps(svd);
  }

  public void testResource() throws Exception {
    String string = getClass().getResource("/mat/svd0.mathematica").getPath();
    _check(Get.of(Paths.get(string).toFile()));
  }

  public void testEps() {
    Tensor A = Tensors.fromString("{{1, 0}, {0, 1E-14}}");
    assertTrue(NumberQ.all(A));
    SingularValueDecomposition svd = SingularValueDecomposition.of(A);
    assertEquals(NullSpace.of(svd).length(), 1);
    assertEquals(NullSpace.of(svd, Chop._20), Tensors.empty());
  }

  public void testPackageVisibility() {
    assertTrue(Modifier.isPublic(SingularValueDecomposition.class.getModifiers()));
    assertFalse(Modifier.isPublic(SingularValueDecompositionImpl.class.getModifiers()));
  }
}
