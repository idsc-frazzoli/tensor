// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.N;
import junit.framework.TestCase;

public class EigensystemTest extends TestCase {
  public void testQuantity() {
    Tensor matrix = Tensors.fromString("{{10[m],-2[m]},{-2[m],4[m]}}");
    assertTrue(SymmetricMatrixQ.of(matrix));
    {
      Eigensystem eig = Eigensystem.ofSymmetric(matrix);
      assertTrue(eig.values().Get(0) instanceof Quantity);
      assertTrue(eig.values().Get(1) instanceof Quantity);
    }
    {
      Eigensystem eig = Eigensystem.ofSymmetric(N.DOUBLE.of(matrix));
      assertTrue(eig.values().Get(0) instanceof Quantity);
      assertTrue(eig.values().Get(1) instanceof Quantity);
    }
  }

  public void testQuantityMixed() {
    Tensor matrix = Tensors.fromString("{{10[m^2],2[m*kg]},{2[m*kg],4[kg^2]}}");
    assertTrue(SymmetricMatrixQ.of(matrix));
    try {
      Eigensystem.ofSymmetric(matrix);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNonSymmetricFail() {
    try {
      Eigensystem.ofSymmetric(Tensors.fromString("{{1,2},{3,4}}"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
