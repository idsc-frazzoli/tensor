// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.ExactTensorQ;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class GaussianEliminationTest extends TestCase {
  public void testPackageVisibility() {
    int modifiers = GaussianElimination.class.getModifiers();
    assertEquals(modifiers & 0x1, 0x0); // non public but package
  }

  public void testPivots() {
    Tensor matrix = HilbertMatrix.of(3);
    Tensor rhs = Tensors.vector(-1, -2, 3);
    Tensor ge1 = GaussianElimination.of(matrix, PivotArgMaxAbs.INSTANCE, rhs);
    Tensor ge2 = GaussianElimination.of(matrix, PivotFirstNonZero.INSTANCE, rhs);
    assertEquals(ge1, ge2);
    ExactTensorQ.require(ge1);
    ExactTensorQ.require(ge2);
    assertEquals(ge1, Tensors.vector(153, -888, 870));
  }
}
