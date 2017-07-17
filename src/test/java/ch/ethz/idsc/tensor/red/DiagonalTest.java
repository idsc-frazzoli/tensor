// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import junit.framework.TestCase;

public class DiagonalTest extends TestCase {
  public void testSimple() {
    assertEquals(Diagonal.of(IdentityMatrix.of(3)), Tensors.vector(1, 1, 1));
  }
}
