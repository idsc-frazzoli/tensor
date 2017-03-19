// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import junit.framework.TestCase;

public class KillingFormTest extends TestCase {
  public void testSo3() {
    Tensor so3 = LieAlgebras.so3();
    assertEquals(JacobiIdentity.of(so3), Array.zeros(3, 3, 3, 3));
    Tensor kil = KillingForm.of(so3);
    assertEquals(kil, IdentityMatrix.of(3).multiply(RealScalar.of(-2)));
  }
}
