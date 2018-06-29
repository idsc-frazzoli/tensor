// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.GaussScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.lie.RotationMatrix;
import junit.framework.TestCase;

public class RotationMatrixTest extends TestCase {
  public void testPointThree() {
    Tensor mat = RotationMatrix.of(RealScalar.of(.3));
    Tensor eye = mat.dot(Transpose.of(mat));
    assertEquals(eye, IdentityMatrix.of(2));
    assertTrue(OrthogonalMatrixQ.of(mat));
    assertTrue(mat.Get(0, 1).toString().startsWith("-0.2955"));
    assertTrue(mat.Get(1, 1).toString().startsWith("0.95533"));
  }

  public void testFail() {
    try {
      RotationMatrix.of(GaussScalar.of(2, 7));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
