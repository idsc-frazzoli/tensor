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
    Tensor ad = LieAlgebras.so3();
    assertEquals(JacobiIdentity.of(ad), Array.zeros(3, 3, 3, 3));
    Tensor kil = KillingForm.of(ad);
    assertEquals(kil, DiagonalMatrix.of(-2, -2, -2));
  }

  public void testSl3() {
    Tensor ad = LieAlgebras.sl2();
    assertEquals(JacobiIdentity.of(ad), Array.zeros(3, 3, 3, 3));
    Tensor kil = KillingForm.of(ad);
    // killing form is non-degenerate
    assertTrue(Scalars.nonZero(Det.of(kil)));
  }

  public void testHe3() {
    Tensor ad = LieAlgebras.he1();
    assertEquals(JacobiIdentity.of(ad), Array.zeros(3, 3, 3, 3));
    Tensor kil = KillingForm.of(ad);
    assertTrue(Scalars.isZero(Det.of(kil)));
  }

  public void testRank4Fail() {
    try {
      KillingForm.of(Array.zeros(3, 3, 3, 3));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
