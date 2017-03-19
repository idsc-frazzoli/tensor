// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Dot;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import junit.framework.TestCase;

public class LieAlgebrasTest extends TestCase {
  public void testHeisenberg() {
    Tensor he3 = LieAlgebras.heisenberg();
    Tensor eye = IdentityMatrix.of(3);
    assertEquals(Dot.of(he3, eye.get(0), eye.get(1)), eye.get(2));
    assertEquals(Dot.of(he3, eye.get(1), eye.get(0)), eye.get(2).negate());
    assertEquals(JacobiIdentity.of(he3), Array.zeros(3, 3, 3, 3));
  }
}
