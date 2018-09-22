// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.GaussScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.lie.RotationMatrix;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class RotationMatrixTest extends TestCase {
  public void testPointThree() {
    Tensor matrix = RotationMatrix.of(RealScalar.of(.3));
    Tensor eye = matrix.dot(Transpose.of(matrix));
    assertEquals(eye, IdentityMatrix.of(2));
    assertTrue(OrthogonalMatrixQ.of(matrix));
    assertTrue(matrix.Get(0, 1).toString().startsWith("-0.2955"));
    assertTrue(matrix.Get(1, 1).toString().startsWith("0.95533"));
  }

  public void testComplex() {
    Tensor matrix = RotationMatrix.of(ComplexScalar.of(1, 2));
    assertTrue(Chop._12.close(matrix.Get(0, 0), ComplexScalar.of(2.0327230070196655294, -3.0518977991518000575)));
    assertTrue(Chop._12.close(matrix.Get(0, 1), ComplexScalar.of(-3.1657785132161681467, -1.9596010414216058971)));
    assertTrue(OrthogonalMatrixQ.of(matrix));
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
