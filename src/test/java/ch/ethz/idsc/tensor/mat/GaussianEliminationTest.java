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
    GaussianElimination ge1 = new GaussianElimination(matrix, PivotArgMaxAbs.INSTANCE, rhs);
    GaussianElimination ge2 = new GaussianElimination(matrix, PivotFirstNonZero.INSTANCE, rhs);
    assertEquals(ge1.det(), ge2.det());
    assertEquals(ge1.lhs(), ge2.lhs());
    assertEquals(ge1.solution(), ge2.solution());
    assertEquals(ge1.det(), ge2.det());
    assertEquals(ge1.lhs(), ge2.lhs());
    assertEquals(ge1.solution(), ge2.solution());
    ExactTensorQ.require(ge1.solution());
    ExactTensorQ.require(ge2.solution());
    assertEquals(ge1.solution(), Tensors.vector(153, -888, 870));
  }
}
