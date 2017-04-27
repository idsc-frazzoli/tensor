// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class FourierMatrixTest extends TestCase {
  public void checkFormat(int n) {
    Tensor zeros = Array.zeros(n, n);
    Tensor matrix = Chop.of(FourierMatrix.of(n));
    Tensor invert = ConjugateTranspose.of(matrix);
    assertEquals(Chop.of(matrix.dot(invert).subtract(IdentityMatrix.of(n))), zeros);
    assertEquals(Chop.of(Inverse.of(matrix).subtract(invert)), zeros);
  }

  public void testSeveral() {
    for (int n = 1; n <= 10; ++n)
      checkFormat(n);
    checkFormat(32);
  }

  public void testNorm4() {
    Tensor m = FourierMatrix.of(4);
    assertEquals(Norm._1.of(m), RealScalar.of(2));
    assertEquals(Norm._1.of(m), Norm.Infinity.of(m));
    assertEquals(Norm._1.of(m), Norm.Frobenius.of(m));
    // TODO Norm._2.of m == 1
  }
}
