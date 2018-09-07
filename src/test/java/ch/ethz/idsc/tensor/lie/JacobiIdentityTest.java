// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Dot;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import junit.framework.TestCase;

public class JacobiIdentityTest extends TestCase {
  public void testSo3() {
    Tensor so3 = LieAlgebras.so3();
    Tensor eye = IdentityMatrix.of(3);
    assertEquals(Dot.of(so3, eye.get(0), eye.get(1)), eye.get(2));
    assertEquals(Dot.of(so3, eye.get(1), eye.get(0)), eye.get(2).negate());
    assertEquals(JacobiIdentity.of(so3), Array.zeros(3, 3, 3, 3));
  }

  public void testSl3() {
    Tensor ad = LieAlgebras.sl2();
    assertEquals(JacobiIdentity.of(ad), Array.zeros(3, 3, 3, 3));
  }

  public void testSe3() {
    Tensor ad = LieAlgebras.se2();
    assertEquals(JacobiIdentity.of(ad), Array.zeros(3, 3, 3, 3));
  }
}
