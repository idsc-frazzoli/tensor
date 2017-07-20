// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.Random;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class MatrixExpTest extends TestCase {
  public void testExp() {
    Random ra = new Random();
    double val = ra.nextGaussian();
    double va2 = ra.nextGaussian();
    double va3 = ra.nextGaussian();
    double[][] mat = new double[][] { { 0, val, va2 }, { -val, 0, va3 }, { -va2, -va3, 0 } };
    Tensor bu = Tensors.matrixDouble(mat);
    Tensor o = MatrixExp.of(bu);
    assertEquals( //
        Chop._12.of(o.dot(Transpose.of(o)).subtract(IdentityMatrix.of(o.length()))), //
        Array.zeros(Dimensions.of(o)));
  }

  public void testExp2() {
    int n = 10;
    Random random = new Random();
    Tensor A = Tensors.matrix((i, j) -> DoubleScalar.of(random.nextGaussian()), n, n);
    Tensor S = A.subtract(Transpose.of(A));
    Tensor o = MatrixExp.of(S);
    assertEquals( //
        o.dot(Transpose.of(o)).subtract(IdentityMatrix.of(o.length())).map(Chop.below(1e-10)), //
        Array.zeros(Dimensions.of(o)));
  }

  public void testFail() {
    try {
      MatrixExp.of(Array.zeros(4, 3));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      MatrixExp.of(Array.zeros(3, 4));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
