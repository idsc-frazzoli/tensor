// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;
import java.util.Random;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import junit.framework.TestCase;

public class BasisTransformTest extends TestCase {
  public void testForm() {
    {
      int n = 3;
      Tensor s = BasisTransform.ofForm(Array.zeros(n, n, n, n), Array.zeros(n, n + 2));
      assertEquals(Dimensions.of(s), Arrays.asList(5, 5, 5, 5));
    }
    {
      int rows = 6;
      int cols = 8;
      Random r = new Random();
      Tensor m = IdentityMatrix.of(rows);
      Tensor v = Tensors.matrix((i, j) -> RealScalar.of(r.nextInt(10)), rows, cols);
      Tensor t = BasisTransform.ofForm(m, v);
      Tensor d = t.subtract(Transpose.of(t));
      assertEquals(d, Array.zeros(cols, cols));
    }
  }
}
