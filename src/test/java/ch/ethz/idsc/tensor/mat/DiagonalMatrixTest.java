// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dot;
import junit.framework.TestCase;

public class DiagonalMatrixTest extends TestCase {
  public void testIdentity() {
    Tensor m = IdentityMatrix.of(10);
    Tensor d = Dot.of(m, m, m, m);
    assertEquals(m, d);
    Tensor e = DiagonalMatrix.of(Tensors.vector(1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
    assertEquals(m, e);
  }
}
