// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import junit.framework.TestCase;

public class DiagonalTest extends TestCase {
  public void testSimple() {
    assertEquals(Diagonal.of(IdentityMatrix.of(5)), Tensors.vector(1, 1, 1, 1, 1));
    assertEquals(Diagonal.of(HilbertMatrix.of(4)), Tensors.vector(1, 3, 5, 7).map(Scalar::invert));
  }
}
