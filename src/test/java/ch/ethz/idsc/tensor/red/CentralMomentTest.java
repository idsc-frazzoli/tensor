// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class CentralMomentTest extends TestCase {
  public void testVarious() {
    Tensor tensor = Tensors.vector(10, 2, 3, 4, 1);
    assertEquals(CentralMoment.of(tensor, 0), RealScalar.of(1));
    assertEquals(CentralMoment.of(tensor, 1), RealScalar.of(0));
    assertEquals(CentralMoment.of(tensor, 2), RealScalar.of(10));
    assertEquals(CentralMoment.of(tensor, 3), RealScalar.of(36));
    assertEquals(CentralMoment.of(tensor, 4), Scalars.fromString("1394/5"));
  }

  public void testComplex() {
    Tensor tensor = Tensors.vector(10, 2, 3, 4, 1);
    Scalar result = CentralMoment.of(tensor, 1.3);
    Scalar gndtru = Scalars.fromString("1.1567572194352718 - 1.2351191805935866* I");
    assertTrue(Chop._12.close(result, gndtru));
  }

  public void testQuantity() {
    Tensor vector = Tensors.of(Quantity.of(2, "kg"), Quantity.of(3, "kg"));
    Scalar result = CentralMoment.of(vector, 2);
    assertEquals(result, Scalars.fromString("1/4[kg^2]"));
  }

  public void testFailEmpty() {
    try {
      CentralMoment.of(Tensors.empty(), 2);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailmatrix() {
    try {
      CentralMoment.of(HilbertMatrix.of(2, 3), RealScalar.of(2));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
