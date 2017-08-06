// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.Random;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.GaussScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class InverseTest extends TestCase {
  public void testInverse() {
    int n = 25;
    Random random = new Random();
    Tensor A = Tensors.matrix((i, j) -> DoubleScalar.of(random.nextGaussian()), n, n);
    Tensor Ai = Inverse.of(A);
    assertEquals(A.dot(Ai).subtract(IdentityMatrix.of(A.length())).map(Chop._10), //
        Array.zeros(Dimensions.of(A)));
    assertEquals(Ai.dot(A).subtract(IdentityMatrix.of(A.length())).map(Chop._10), //
        Array.zeros(Dimensions.of(A)));
  }

  public void testInverseNoAbs() {
    int n = 12;
    int p = 20357;
    Random random = new Random();
    Tensor A = Tensors.matrix((i, j) -> GaussScalar.of(random.nextInt(p), p), n, n);
    Tensor b = Tensors.vector(i -> GaussScalar.of(random.nextInt(p), p), n);
    Tensor x = LinearSolve.withoutAbs(A, b);
    assertEquals(A.dot(x), b);
    Tensor id = IdentityMatrix.of(n, GaussScalar.of(1, p));
    {
      Tensor Ai = Inverse.withoutAbs(A, id);
      assertEquals(A.dot(Ai), id);
      assertEquals(Ai.dot(A), id);
    }
    {
      Tensor Ai = Inverse.of(A, id);
      assertEquals(A.dot(Ai), id);
      assertEquals(Ai.dot(A), id);
    }
  }
}
