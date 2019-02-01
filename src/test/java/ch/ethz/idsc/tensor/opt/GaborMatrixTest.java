// code by jph
package ch.ethz.idsc.tensor.opt;

import java.util.Arrays;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import junit.framework.TestCase;

public class GaborMatrixTest extends TestCase {
  public void testMatrix() {
    Tensor matrix = GaborMatrix.of(2, Tensors.vector(.2, .1), RealScalar.of(.2));
    assertEquals(Dimensions.of(matrix), Arrays.asList(5, 5));
  }

  public void testVector() {
    Tensor vector = GaborMatrix.of(3, Tensors.vector(1), RealScalar.of(0));
    assertEquals(Dimensions.of(vector), Arrays.asList(7));
  }

  public void testFailVector() {
    try {
      GaborMatrix.of(3, RealScalar.ONE, RealScalar.of(0));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailMatrix() {
    try {
      GaborMatrix.of(3, HilbertMatrix.of(3), RealScalar.of(0));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
