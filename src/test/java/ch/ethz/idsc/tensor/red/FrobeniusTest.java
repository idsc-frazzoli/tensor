// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import junit.framework.TestCase;

public class FrobeniusTest extends TestCase {
  public void testVector() {
    Scalar norm = Frobenius.NORM.ofVector(Tensors.vector(3, 4));
    assertEquals(norm, RealScalar.of(5));
  }

  public void testBetween() {
    Tensor t1 = Tensors.fromString("{0, {1, 2}, 3}");
    Tensor t2 = Tensors.fromString("{2, {-1, 0}, 8}");
    Scalar d1 = Frobenius.NORM.between(t1, t2);
    Scalar d2 = Norm._2.between(Tensor.of(t1.flatten(-1)), Tensor.of(t2.flatten(-1)));
    assertEquals(d1, d2);
  }

  public void testMatrix() {
    Scalar norm = Frobenius.NORM.ofMatrix(IdentityMatrix.of(4));
    assertEquals(norm, RealScalar.of(2));
  }

  public void testVectorFail() {
    try {
      Frobenius.NORM.ofVector(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testMatrixFail() {
    try {
      Frobenius.NORM.ofMatrix(Tensors.vector(1, 2, 3));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
