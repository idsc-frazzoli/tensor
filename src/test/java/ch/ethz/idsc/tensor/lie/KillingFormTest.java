// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.mat.Det;
import ch.ethz.idsc.tensor.mat.DiagonalMatrix;
import junit.framework.TestCase;

public class KillingFormTest extends TestCase {
  public void testSo3() {
    Tensor so3 = LieAlgebras.so3();
    assertEquals(JacobiIdentity.of(so3), Array.zeros(3, 3, 3, 3));
    Tensor kil = KillingForm.of(so3);
    assertEquals(kil, DiagonalMatrix.of(-2, -2, -2));
  }

  public void testSl3() {
    Tensor sl3 = LieAlgebras.sl2();
    assertEquals(JacobiIdentity.of(sl3), Array.zeros(3, 3, 3, 3));
    Tensor kil = KillingForm.of(sl3);
    // killing form is non-degenerate
    assertTrue(Scalars.nonZero(Det.of(kil)));
  }

  public void testHe3() {
    Tensor he3 = LieAlgebras.heisenberg3();
    assertEquals(JacobiIdentity.of(he3), Array.zeros(3, 3, 3, 3));
    Tensor kil = KillingForm.of(he3);
    assertTrue(Scalars.isZero(Det.of(kil)));
  }
}
