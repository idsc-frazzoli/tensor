// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.DiagonalMatrix;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import junit.framework.TestCase;

public class BasisTransformTest extends TestCase {
  public void testDimensions() {
    int n = 3;
    Tensor s = BasisTransform.ofForm(Array.zeros(n, n, n, n), Array.zeros(n, n + 2));
    assertEquals(Dimensions.of(s), Arrays.asList(5, 5, 5, 5));
  }

  public void testForm() {
    int rows = 6;
    int cols = 8;
    Random r = new Random();
    Tensor m = IdentityMatrix.of(rows);
    Tensor v = Tensors.matrix((i, j) -> RealScalar.of(r.nextInt(10)), rows, cols);
    Tensor t = BasisTransform.ofForm(m, v);
    Tensor d = t.subtract(Transpose.of(t));
    assertEquals(d, Array.zeros(cols, cols));
  }

  public void testStream() {
    int n = 5;
    Integer[] asd = new Integer[n];
    IntStream.range(0, n).forEach(i -> asd[i] = (i + 1) % n);
    assertEquals(asd[0].intValue(), 1);
    assertEquals(asd[n - 1].intValue(), 0);
  }

  public void testFormVectorFail() {
    int n = 3;
    try {
      BasisTransform.ofForm(Array.zeros(n, n, n), Array.zeros(n));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testMatrixFail() {
    try {
      BasisTransform.ofMatrix(IdentityMatrix.of(3), DiagonalMatrix.of(1, 1, 0));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
