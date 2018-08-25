// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.MatrixQ;
import ch.ethz.idsc.tensor.alg.Range;
import ch.ethz.idsc.tensor.lie.LieAlgebras;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import junit.framework.TestCase;

public class DiagonalTest extends TestCase {
  public void testVector() {
    Tensor tensor = Diagonal.of(Range.of(10, 20));
    assertTrue(Tensors.isEmpty(tensor));
  }

  public void testSpecial() {
    assertEquals(Diagonal.of(IdentityMatrix.of(5)), Tensors.vector(1, 1, 1, 1, 1));
    assertEquals(Diagonal.of(HilbertMatrix.of(4)), Tensors.vector(1, 3, 5, 7).map(Scalar::reciprocal));
  }

  public void testRectangular() {
    assertEquals(Diagonal.of(HilbertMatrix.of(4, 5)), Tensors.vector(1, 3, 5, 7).map(Scalar::reciprocal));
    assertEquals(Diagonal.of(HilbertMatrix.of(5, 4)), Tensors.vector(1, 3, 5, 7).map(Scalar::reciprocal));
    assertEquals(Diagonal.of(Array.zeros(5, 12)), Array.zeros(5));
  }

  public void testLieAlgebra() {
    assertTrue(MatrixQ.of(Diagonal.of(LieAlgebras.sl2())));
  }

  public void testFailScalar() {
    try {
      Diagonal.of(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      //
    }
  }
}
