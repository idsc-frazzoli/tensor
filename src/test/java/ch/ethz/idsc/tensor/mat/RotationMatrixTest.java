// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Transpose;
import junit.framework.TestCase;

public class RotationMatrixTest extends TestCase {
  public void testSimple() {
    Tensor mat = RotationMatrix.of(RealScalar.of(.3));
    // System.out.println(Pretty.of(mat));
    Tensor eye = mat.dot(Transpose.of(mat));
    assertEquals(eye, IdentityMatrix.of(2));
    assertTrue(OrthogonalMatrixQ.of(mat));
    assertTrue(mat.Get(0, 1).toString().startsWith("-0.2955"));
    assertTrue(mat.Get(1, 1).toString().startsWith("0.95533"));
  }
}
